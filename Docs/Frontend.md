## 1. Ziele der Frontend Architektur

Das Frontend soll

1. Bildpaare hochladen und an das Backend schicken
2. Analysen eines Bildpaares übersichtlich visualisieren

   - Originalbilder
   - Gradient Maps
   - Features und Detector Scores

3. Vorhandene Analysen durchsuchen und öffnen
4. Sauberes, wiederverwendbares UI mit shadcn Komponenten haben
5. Klar getrennt sein in

   - Routing
   - Feature Logik
   - UI Komponenten
   - API Zugriff

Ich gehe von Next.js App Router aus.

---

## 2. High Level Struktur

Im Monorepo etwa:

```text
frontend/
  app/
  src/ or (direkt in app)
  components/
  features/
  lib/
  styles/
```

Ich schlage vor, die Logik nach "Features" zu strukturieren, nicht nach "Technik". Also z. B. Feature "AnalyzePair", Feature "PairGallery", etc.

---

## 3. Routing Ebene (Next.js app/)

### Wichtige Routen

- `/`
  Landing page

  - kurze Erklärung, was das Projekt macht
  - Call to action: "Upload pair" oder "Browse examples"

- `/upload`
  Upload Page

  - Formular für reales und generiertes Bild
  - optional: Metadaten (Generator, Prompt)
  - schickt Anfrage an Backend
  - danach Redirect zur Detailseite des erzeugten Pairs

- `/pairs`
  Gallery / Overview Page

  - Liste aller bisherigen Bildpaare mit wichtigsten Infos
  - Filterung und Sortierung

- `/pairs/[pairId]`
  Detail Analyse Page

  - zeigt alle Details zu einem bestimmten Bildpaar
  - Tabs für verschiedene Sichten

    - Overview
    - Gradient Space
    - Features
    - Detectors

- optional `/experiments`
  Übersicht über experimentelle Runs

  - z. B. mehrere Detektoren auf einem Datensatz

### App Verzeichnis Struktur

Beispiel:

```text
app/
  layout.tsx            # Shell, Navbar, Theme Provider
  page.tsx              # Landing
  upload/
    page.tsx
  pairs/
    page.tsx            # Liste
    [pairId]/
      page.tsx          # Detailanalyse
  experiments/
    page.tsx            # optional
```

Die Route Pages selbst sollten möglichst wenig Logik enthalten, nur Zusammensetzen von Feature Komponenten.

---

## 4. Feature Layer

Im Ordner `features/` kapselst du die eigentliche "Use Case Logik". Jede Feature hat

- Container Komponenten (mit Data Fetching, State, Routing)
- reine UI Komponenten, die nur Props anzeigen
- API Hooks oder Service Funktionen

### Beispiel Feature Ordner

```text
features/
  uploadPair/
    components/
      UploadForm.tsx
      UploadResultCard.tsx
    hooks/
      useUploadPair.ts
    index.ts
  pairDetail/
    components/
      PairOverview.tsx
      GradientView.tsx
      FeatureTable.tsx
      DetectorScorePanel.tsx
      PairTabs.tsx
    hooks/
      usePairAnalysis.ts
    index.ts
  pairList/
    components/
      PairListTable.tsx
      PairFilters.tsx
    hooks/
      usePairList.ts
    index.ts
  experiments/ (optional)
    ...
```

Die Idee:

- `hooks/` sprechen mit `lib/api` und halten Loading, Error, Data
- `components/` erwarten Data über Props und kümmern sich nur um Darstellung
- `index.ts` exportiert die Hauptentry-Komponenten für die Routes

---

## 5. API und Datenzugriff im Frontend

Der Frontend Code soll nicht überall `fetch` verstreut haben. Stattdessen:

```text
lib/
  api/
    client.ts          # Basisfunktionen für Requests, z. B. fetchJson
    pairs.ts           # Funktionen: getPairList, getPairDetails, uploadPair
    experiments.ts     # optional
  types/
    analysis.ts        # TS Types, gespiegelt aus Backend Domain / API
    pairs.ts
  utils/
    formatters.ts      # z. B. Anzeige von Scores, Prozenten, etc.
```

Typische Functions in `lib/api/pairs.ts`:

- `uploadPair(formData)`
- `getPairAnalysis(pairId)`
- `getPairList(filters)`

Deine Feature Hooks `usePairAnalysis`, `usePairList` usw. rufen diese Funktionen auf und kapseln auch eventuell React Query oder manuelles State Handling.

---

## 6. UI Layer mit shadcn

Ich würde eine Trennung machen zwischen

1. Low level UI Building Blocks (Buttons, Cards, Tabs)
2. Domain spezifischen Komponenten (GradientViewer, FeatureTable usw.)

### 6.1 Low Level UI

Ordner:

```text
components/ui/
```

Dort liegen die generierten oder leicht angepassten shadcn Komponenten:

- `button.tsx`
- `card.tsx`
- `tabs.tsx`
- `input.tsx`
- `label.tsx`
- `table.tsx`
- `alert.tsx`
- usw.

Diese Komponenten kennen keine Domain, sind generische UI Bausteine.

### 6.2 Domain UI Komponenten

Im Ordner:

```text
components/domain/
  ImagePreview.tsx
  GradientPreview.tsx
  FeatureSummaryCard.tsx
  DetectorScoreBadge.tsx
  LayoutShell.tsx
  PageHeader.tsx
```

Diese Komponenten kombinieren shadcn UI mit Domain Konzepten, sind aber noch unabhängig von API Calls. Sie bekommen alles per Props.

Beispiele:

- `ImagePreview` nimmt `src`, `label` etc.
- `GradientPreview` nimmt `src` für eine Gradient Map
- `FeatureSummaryCard` nimmt ein verkleinertes Feature Objekt und zeigt es grafisch
- `DetectorScoreBadge` nimmt `score` und `detectorName` und zeigt ein Badge mit Farbcodierung

---

## 7. State Management

Für das Projekt reicht in der Regel:

- Server Components für statische Dinge
- Client Components plus Hooks für interaktive Bereiche
- Daten pro Seite über Hooks mit `fetch` oder React Query

Klarer Vorschlag:

- Kein globales Redux, das wäre Overkill
- Pro Feature Hook wie `usePairAnalysis(pairId)`

  - kümmert sich um Anfrage an `/api/pairs/{id}`
  - liefert `{ data, isLoading, error }`

- Navigation und Auswahl primär über URL Parameter

  - z. B. Filters als Query Params auf `/pairs`

So bleibt der Zustand nachvollziehbar und nah am Routing, was super für Debugging ist.

---

## 8. Typische Page Kompositionen

Damit du siehst, wie die Teile zusammenspielen, einmal grob, wie z. B. die `PairDetail` Page aufgebaut ist, nur auf Architektur Ebene:

1. `app/pairs/[pairId]/page.tsx`

   - liest `pairId` aus der URL
   - rendert eine Feature Komponente, zum Beispiel `PairDetailScreen`

2. `features/pairDetail/index.ts`

   - exportiert `PairDetailScreen`

3. `PairDetailScreen` (Feature Container)

   - ruft `usePairAnalysis(pairId)`
   - zeigt Loading oder Error Zustände
   - wenn Daten da sind, rendert Domain Komponenten, z. B.

     - `PairTabs` mit Tabs: Overview, Gradients, Features, Detectors

4. Tabs enthalten kleinere Domain Komponenten

   - `PairOverview`

     - nutzt `ImagePreview` Komponenten

   - `GradientView`

     - nutzt `GradientPreview` für real und fake, vielleicht eine Differenzkarte

   - `FeatureTable`
   - `DetectorScorePanel`

Diese Komposition macht klar, wo die Verantwortung liegt:

- Der Page File ist nur Routing und Shell
- Der Feature Container kennt API und Domain
- Domain Komponenten kennen nur Domain Daten und UI
- shadcn Komponenten kennen nur Layout und Styling

---

## 9. Visualisierungen integrieren

Wenn du zusätzlich Plotting brauchst, etwa Histogramme der Gradientenstärken, würde ich das auch sauber kapseln:

```text
components/charts/
  GradientHistogram.tsx
  FeatureRadarChart.tsx
```

Diese Komponenten erwarten bereits vorbereitete Datenstrukturen, z. B.

- `bins` und `counts` für Histogramme
- oder ein Feature Objekt, das in radiale Achsen gemappt wird

Die Transformation von Rohdaten in Chart Inputs sollte in den Feature Hooks oder in `lib/utils/transformers.ts` passieren, nicht im Chart selbst. So bleibt der Chart generisch und wiederverwendbar.

---

## 10. Warum diese Architektur gut mit dem Backend zusammenspielt

- Die Backend Domain Typen spiegelst du in `lib/types` im Frontend, so bleibt es typsicher und konsistent.
- Die Endpoints aus dem API Layer des Backends spiegelst du 1:1 in `lib/api`.
- Jede größere Funktionalität im Backend (Bildpaar analysieren, Analysen auflisten) hat ein entsprechendes Feature im Frontend.

So kannst du sehr klar argumentieren:

- Diese Route entspricht diesem Use Case
- Dieser Use Case nutzt diese API Calls
- Die API Calls sprechen mit diesen Backend Funktionen

Und du bekommst genau das, was du wolltest: clean, modular, gut erweiterbar und wissenschaftlich nutzbar.
