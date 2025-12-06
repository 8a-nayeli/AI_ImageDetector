# Architektur Overview

Dieses Dokument beschreibt das System auf hoher Ebene

- welche Teile es gibt
- wie sie miteinander sprechen
- welche Contracts zwischen den Schichten existieren

Details zu einzelnen Teilen stehen dann in

- `docs/Backend.md`
- `docs/Frontend.md`

---

## 1. High Level Systembild

Abstrakte Übersicht:

```txt
┌─────────────────────────────────────────────┐
│                  Frontend                  │
│         (Next.js + shadcn UI)              │
│                                             │
│  - Upload Pair UI                          │
│  - Pair Gallery                            │
│  - Pair Detail View                        │
│  - Charts und Visualisierungen             │
└───────────────▲────────────────────────────┘
                │ HTTP JSON API
                │
┌───────────────┴────────────────────────────┐
│                  Backend                   │
│          (Python + FastAPI o. ä.)          │
│                                             │
│  API Layer                                 │
│  Data Access Layer                         │
│  Detection Layer                           │
│  Core Analysis Layer                       │
│  Domain Layer                              │
└───────────────▲────────────────────────────┘
                │
                │ Dateisystem / DB
                │
┌───────────────┴────────────────────────────┐
│                  Storage                   │
│  - Rohbilder (real, fake)                  │
│  - Gradient Visualisierungen               │
│  - Analysis Results                        │
│  - Experiments                             │
└────────────────────────────────────────────┘
```

---

## 2. Bounded Contexts

Wir haben drei logische Kontexte:

1. **Frontend Kontext**
   Darstellung, Interaktion, Storytelling der Ergebnisse
2. **Analysis Kontext (Backend Domain + Core + Detection)**
   Fachlogik rund um Bildpaare, Gradienten, Features, Detektoren
3. **Persistence Kontext (Data Access + Storage)**
   Ablage und Wiederauffinden von Daten, aber ohne Analyse Logik

Jeder Kontext hat klar definierte Contracts und spricht mit dem anderen nur über diese Schnittstellen.

---

## 3. Zentrale Domain Begriffe und Contracts

Diese Begriffe sind das gemeinsame Vokabular zwischen Frontend und Backend. Sie werden als JSON über die API transportiert und in TypeScript und Python gespiegelt.

### 3.1 ImagePair

Repräsentiert ein Paar aus realem und generiertem Bild.

Wichtige Felder:

- `pairId`
- `realImage`

  - `id`
  - `url`
  - optional `source`

- `fakeImage`

  - `id`
  - `url`
  - optional `generatorType`
  - optional `prompt`

Der Frontend Contract:

- Frontend muss nur `pairId` und die URLs kennen, keine Dateipfade
- Backend entscheidet, wie die Dateien wirklich gespeichert werden

### 3.2 AnalysisResult

Repräsentiert die Analyse eines `ImagePair`.

Kernfelder:

- `pairId`
- `gradients`

  - `realGradientUrl`
  - `fakeGradientUrl`
  - optional `diffGradientUrl`

- `features`

  - z. B. `meanGradientReal`
  - `meanGradientFake`
  - `histogramReal`
  - `histogramFake`

- `detectors`

  - Liste oder Map nach Detector Name
  - z. B. `gradient_stats` mit

    - `score`
    - optional `explanation`

Frontend Contract:

- Frontend zeigt Bilder und Visualisierungen auf Basis der URLs
- Features sind rein numerische Daten, für Tabellen und Charts
- Detector Results werden als einfache, flache Objekte angezeigt

Backend Contract:

- Backend garantiert, dass für ein `pairId` entweder kein `AnalysisResult` existiert oder ein konsistentes Objekt, das zu diesem Schema passt

---

## 4. API Contracts zwischen Frontend und Backend

Die API ist die zentrale Boundary zwischen den beiden Welten. Das Frontend spricht ausschließlich über HTTP + JSON mit dem Backend.

Wir haben im Kern drei Gruppen von Endpoints:

1. Upload und Analyse
2. Lesen von Analysen
3. Experimente und Beispiele

### 4.1 Upload und Analyse

**Endpoint**

- `POST /api/analyze/pair`

**Request Contract**

- multipart Form Data mit

  - `real_image`
  - `fake_image`
  - optional `generatorType`
  - optional `prompt`

Backend Responsibility:

- Bilder validieren und speichern
- Domain Objekt `ImagePair` erzeugen
- Analyse Pipeline ausführen
- `AnalysisResult` persistieren
- konsistente `pairId` vergeben

**Response Contract**

- HTTP 201 mit einem `AnalysisResult` Objekt
- minimal enthält

  - `pairId`
  - `images` Block
  - `gradients` Block
  - `features` Block
  - `detectors` Block

Frontend Responsibility:

- Antwort parsen
- auf Detailseite navigieren `/pairs/[pairId]`
- Daten an UI Komponenten durchreichen

### 4.2 Lesen von Analysen

**Endpoint**

- `GET /api/pairs`

  - liefert eine Liste von bekannten Bildpaaren, optional mit kurzen Summary Infos

**Endpoint**

- `GET /api/pairs/{pairId}`

  - liefert das vollständige `AnalysisResult` zu einem bestimmten Paar

Contracts:

- Backend garantiert, dass `pairId` eindeutig und stabil ist
- Frontend behandelt `pairId` als primären Key, sowohl in der URL als auch intern

### 4.3 Beispiele und Experimente

Optionale, aber sinnvolle Endpoints:

- `GET /api/examples`

  - liefert vordefinierte Beispiel Paare, z. B. vom Kursleiter bereitgestellt

- `GET /api/experiments`

  - aggregierte Ergebnisse mehrerer Analysen

Diese können im Frontend im Bereich "Overview" oder "Experiments" visualisiert werden.

Details zu genauen Feldern stehen in `Backend.md`.

---

## 5. Beziehungen zwischen Backend Schichten

Im Backend gilt eine klare Einbahnstraße der Abhängigkeiten:

```txt
API Layer
  ↓
Data Access Layer   Detection Layer
  ↓                 ↓
      Core Analysis Layer
              ↓
          Domain Layer
```

- Domain Layer ist die Basis, kennt nur fachliche Konzepte
- Core Analysis arbeitet auf Domain Typen, kennt keine HTTP Details
- Detection konsumiert Output des Core Layers und liefert Domain-konforme Scores
- Data Access kümmert sich darum, Domain Objekte zu speichern und wiederzufinden
- API Layer orchestriert nur, ruft Domain orientierte Funktionen und Repositories auf, kennt aber keine Implementierungsdetails von Gradientenberechnung

Konkretere Beschreibung dieser Schichten findest du in `Backend.md`.

---

## 6. Beziehungen zwischen Frontend Schichten

Im Frontend gibt es eine ähnliche klare Trennung:

```txt
Next.js Routes (app/)
  ↓
Feature Layer (features/)
  ↓
Domain UI Components (components/domain/)
  ↓
UI Building Blocks (components/ui/)
  ↓
API Client (lib/api/) + Types (lib/types/)
```

- Routes kümmern sich nur um Routing und grobe Layouts
- Feature Layer kapselt Data Fetching und Use Cases (z. B. "Analyze Pair", "Show Pair List")
- Domain UI Komponenten kennen Domain Datenstrukturen, aber keine Fetch Logik
- UI Building Blocks sind reine Präsentationsbausteine, ohne Domain Wissen
- API Client definiert `getPairAnalysis`, `uploadPair`, `getPairList`
- Types spiegeln die Contracts aus `AnalysisResult`, `ImagePair` usw.

Detaillierte Struktur steht in `Frontend.md`.

---

## 7. End-to-End Flow Contract

Ein kompletter Fluss von Upload bis Visualisierung sieht dann so aus:

1. User öffnet `/upload` im Frontend
2. Frontend Komponenten im Feature `uploadPair` sammeln Dateien und Metadaten
3. Feature Hook ruft `lib/api/pairs.uploadPair` auf
4. API Client sendet `POST /api/analyze/pair`
5. Backend API Layer

   - mappt Request in Domain Objekte (`ImagePair`)
   - triggert Core Analysis (`gradients`, `features`)
   - ruft Detection Layer (`scores`)
   - speichert alles über Data Access Layer
   - baut `AnalysisResult`

6. Backend antwortet mit `AnalysisResult` JSON
7. Frontend erhält das Objekt

   - parst es in `AnalysisResult` Type
   - navigiert nach `/pairs/[pairId]`
   - übergibt Daten an Domain Komponenten wie `PairOverview`, `GradientView`, `FeatureTable`, `DetectorScorePanel`

Wichtiger Contract:

- `AnalysisResult` ist das zentrale Austauschobjekt zwischen Backend und Frontend.
- Es ist stabil versioniert, Änderungen erfolgen bewusst und dokumentiert.

---

## 8. Erweiterbarkeit und State of the Art Integration

Die Architektur ist so gewählt, dass du neue wissenschaftliche Methoden einbauen kannst, ohne alles aufzureißen.

- Neue Detektoren

  - im Backend nur ein neuer Detector im Detection Layer
  - Domain Objekt `AnalysisResult.detectors` enthält einfach einen weiteren Eintrag
  - Frontend zeigt automatisch weitere Scores, wenn es generalisiert auf dieser Struktur arbeitet

- Neue Visualisierungen

  - im Frontend nur neue Domain Komponenten und Charts
  - sie konsumieren weiterhin `AnalysisResult`
  - keine Änderung an API nötig, wenn du zusätzliche Felder optional hinzufügst

- Neue Datenquellen oder Speicherformen

  - Data Access Layer kann von Dateien zu SQLite oder einer DB wechseln
  - Contracts zu Domain Objekten bleiben identisch

Details zur Erweiterung von Detektoren und Visualisierungen stehen in `Backend.md` und `Frontend.md` in eigenen Kapiteln.
