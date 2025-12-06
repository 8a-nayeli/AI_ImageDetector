## 1. Ziele der Backend Architektur

Das Backend soll

1. Bildpaare (real, fake) entgegennehmen und verwalten
2. Gradienten und weitere Features berechnen
3. verschiedene Detektoren (Baseline und SOTA) kapseln
4. Ergebnisse als saubere JSON Strukturen an das Frontend liefern
5. Experimente wiederholbar machen, unabhängig vom Web Teil
6. modular und erweiterbar bleiben, damit du später neue Methoden einbauen kannst

Daraus ergibt sich eine Schichtung:

- **Domain Layer**, zentrale Datentypen und Begriffe
- **Core Analysis Layer**, Bildverarbeitung, Gradienten, Features
- **Detection Layer**, Modelle und Klassifikatoren
- **Data Layer**, Ablage von Bildern, Analysen, Experimenten
- **API Layer**, HTTP Schnittstellen
- **Config und Infra**, Settings, Pfade, Logging
- **Tests**, Unit und Integration

---

## 2. Ordnerstruktur des Backends

Zum Beispiel so:

```text
backend/
  src/
    domain/
    core/
    detection/
    data_access/
    api/
    config/
    utils/
  tests/
    unit/
    integration/
```

Ich gehe die Ebenen jetzt einzeln durch.

---

## 3. Domain Layer

`src/domain/`

Hier definierst du die **fachlichen Konzepte**, ohne technische Details. Das ist quasi dein Vokabular für das gesamte Projekt.

Typische Module:

- `image_pair.py`

  - Konzept eines Bildpaares

    - real_image_id
    - fake_image_id
    - optional: generator_type, prompt, source_dataset

- `analysis_result.py`

  - Ergebnis einer Analyse für ein Bildpaar

    - referenziert ein `ImagePair`
    - Gradient Maps (als Referenzen, nicht als Rohdaten)
    - Feature Vektoren
    - Detector Scores pro Methode

- `detector.py`

  - abstrakter Begriff eines Detektors

    - Name
    - Beschreibung
    - Eingabeformat (zum Beispiel Gradient Maps, Features)
    - Ausgabescore

- `experiment.py`

  - ein Experiment Run

    - welche Detektoren
    - welcher Datensatz
    - Konfigurationsparameter
    - Aggregierte Metriken

Wichtig:
Der Domain Layer kennt noch keine Frameworks, keine Pfade auf der Platte, keine HTTP Requests. Nur Konzepte und Datenstrukturen.

---

## 4. Core Analysis Layer

`src/core/`

Hier liegt die eigentliche Bildverarbeitung und Analyse Logik. Dieser Layer macht aus Pixeln Gradienten und aus Gradienten Features.

Mögliche Module:

- `image_io.py`

  - Laden und Speichern von Bildern aus Dateipfaden oder Binaries
  - Normalisierung (Größe, Farbkanäle)
  - Konvertierung in interne Repräsentation

- `gradients.py`

  - Funktionen zur Berechnung von Gradienten

    - Kantenfilter (Sobel, Scharr, Laplacian als Varianten)
    - eventuell Autograd Varianten für spätere Experimente

  - Ausgabe: Gradient Maps pro Kanal oder kombiniert

- `features.py`

  - Extraktion von Merkmalen aus Gradient Maps

    - statistische Kennzahlen (Mittelwerte, Varianz, Histogramme)
    - eventuell Frequenzanalysen im Gradient Space

  - Ausgabe: strukturierter Feature Vektor, den die Detektoren verwenden

- `visualization.py`

  - Erzeugung von visualisierbaren Artefakten für das Frontend

    - Graustufen Gradient Bilder
    - Heatmaps
    - Differenzkarten real vs fake im Gradient Space

  - Liefert Dateien oder Referenzen, die später über Static Serving verfügbar sind

Core kennt nur minimale Annahmen über die Umgebung, idealerweise nichts über HTTP oder Datenbank.

---

## 5. Detection Layer

`src/detection/`

Dieser Layer kapselt alles, was mit Klassifikation oder Erkennung zu tun hat, auf Basis der Core Outputs.

Module:

- `base.py`

  - abstraktes Interface für Detektoren

    - zum Beispiel Methoden wie `fit`, `predict`, `describe`

  - definiert, welche Eingaben ein Detektor erwartet

    - nur Features
    - oder direkt Gradient Maps

- `gradient_stats_detector.py`

  - einfacher, interpretabler Baseline Detektor

    - arbeitet nur auf statistischen Features der Gradienten

  - eignet sich super, um Unterschiede zu erklären

- `ml_detector.py`

  - generischer Wrapper für Machine Learning Modelle

    - lädt Modelle von Disk
    - kümmert sich um Vorverarbeitung und Postprocessing

- `sota_gradient_detector.py`

  - Platz für einen SOTA Detektor nach Paper, zum Beispiel ein CNN, das auf Gradient Maps trainiert ist
  - implementiert dasselbe Interface wie `BaseDetector`, damit die API Schicht nicht angepasst werden muss

Detection Layer hängt von Core ab, aber nicht umgekehrt.

---

## 6. Data Access Layer

`src/data_access/`

Dieser Layer kümmert sich darum, wie du auf deine Daten zugreifst und sie sicher ablegst.

Module:

- `file_storage.py`

  - Verantwortlich für

    - Speicherorte von Rohbildern
    - Speicherorte von generierten Gradient Visualisierungen

  - Regeln, z. B. Ordnerstruktur

    - `data/images/real`
    - `data/images/fake`
    - `data/gradients/<pair_id>/`

- `analysis_repository.py`

  - Speichern und Laden von `AnalysisResult` Objekten

    - als JSON Dateien
    - oder in einer kleinen Datenbank, etwa SQLite

  - Bietet Methoden wie

    - `save_analysis(result)`
    - `get_analysis(pair_id)`
    - `list_analyses(filters)`

- `pair_repository.py`

  - Verwaltung von `ImagePair` Metadaten

    - id Vergabe
    - Mapping von IDs auf Pfade der Bilder
    - Optionale Labels, falls du Ground Truth verwalten willst

- `experiment_repository.py`

  - Speichern von Experiment Runs und ihren Aggregaten
  - wichtig für spätere Auswertungen in Python oder im Frontend

Data Access Layer spricht mit Dateisystem oder Datenbank, aber hält sich an Domain Typen.

---

## 7. API Layer

`src/api/`

Diese Schicht verbindet Backend Logik mit der Außenwelt, also Next.js oder andere Client Anwendungen.

Typische Teilung:

- `routes/` oder `controllers/`

  - definieren Endpoints und orchestrieren den Flow
  - Beispiele

    - `analyze_pair_controller`

      - nimmt Upload entgegen
      - delegiert an Core und Detection
      - ruft Repositories zum Speichern
      - baut ein `AnalysisResult` Domain Objekt
      - serialisiert es zu einer API Response Struktur

    - `examples_controller`

      - listet vorhandene Bildpaare und Analysen

    - `experiments_controller`

      - Endpoints zum Starten oder Abfragen von Experimenten

- `schemas/`

  - request und response Modelle
  - Mapping zwischen Domain Objekten und der öffentlichen API Struktur
  - klar getrennt, damit du Domain und API unabhängig entwickeln kannst

Die API Schicht kennt HTTP, Auth, Validation und Content Types, aber keine interne Implementierungsdetails von Gradientenberechnung. Sie ruft die höheren Layer nur über deren öffentliche Funktionen an.

---

## 8. Config und Utilities

`src/config/` und `src/utils/`

- `config/settings.py`

  - globale Einstellungen

    - Pfade für Daten
    - Modellpfade
    - Debug Flags

- `config/env.py`

  - Laden von Umgebungsvariablen

- `utils/logging.py`

  - zentrale Logging Konfiguration

- `utils/errors.py`

  - eigene Fehlertypen, die du in API Responses mappen kannst

Damit bleibt vieles zentral konfigurierbar und du vermeidest Hardcoding.

---

## 9. Tests

`tests/`

- `tests/unit/`

  - Core Funktionen testen

    - Gradientenberechnung
    - Feature Extraktion
    - einfache Detector Logik

- `tests/integration/`

  - komplette Flows testen

    - Upload eines Bildpaares
    - Analysen speichern
    - Ergebnis über API abrufen

Ziel: du bist nicht von Handtests im Frontend abhängig, sondern kannst Kernlogik automatisiert prüfen.

---

## 10. Typischer Flow innerhalb des Backends

Um die Architektur greifbar zu machen, einmal der interne Weg für `analyze_pair`:

1. API Layer

   - nimmt Request entgegen
   - validiert Input
   - erzeugt Domain Objekt `ImagePair`

2. Data Access Layer

   - speichert Rohbilder und Metadaten
   - gibt Pfade oder IDs zurück

3. Core Layer

   - lädt Bilder
   - berechnet Gradienten
   - extrahiert Features
   - erzeugt Visualisierungen

4. Detection Layer

   - bekommt Features und optional Gradients
   - berechnet Scores mit einem oder mehreren Detektoren

5. Domain Layer

   - daraus wird ein `AnalysisResult` Objekt zusammengesetzt

6. Data Access Layer

   - speichert `AnalysisResult`

7. API Layer

   - mappt `AnalysisResult` auf eine Response Struktur
   - schickt sie ans Frontend

Damit ist klar getrennt, wer wofür verantwortlich ist, und du kannst an jedem Layer separat weiter denken oder optimieren.

---

Wenn du möchtest, können wir im nächsten Schritt konkret die **Domain Typen** und die **API Responses** als Struktur definieren, also welche Felder ein `AnalysisResult` genau haben soll und wie das JSON aussieht, das Next.js dann visualisiert.
