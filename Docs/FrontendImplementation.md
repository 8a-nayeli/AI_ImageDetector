# Frontend Implementation Guide (Next.js App Router)

Dieses Dokument beschreibt, wie das Frontend die Backend-API konsumiert und die Architektur aus `Docs/Frontend.md` umsetzt. Fokus: Client-Implementierung, keine UI-Bibliotheksdetails.

## 1. Domain Types (lib/types)

Spiegele die Backend-Contracts:

- `ImageInfo` `{ id, url, source?, generatorType?, prompt? }`
- `GradientArtifacts` `{ realGradientUrl, fakeGradientUrl, diffGradientUrl? }`
- `FeatureVector` `{ meanGradientReal, meanGradientFake, stdGradientReal, stdGradientFake, histogramReal, histogramFake }`
- `DetectorResult` `{ score, explanation? }`
- `AnalysisResult` `{ pairId, images: { realImage, fakeImage }, gradients, features, detectors: Record<string, DetectorResult> }`
- `PairSummary` `{ pairId, realImage, fakeImage }`

## 2. API Client (lib/api)

Implement thin wrappers über `fetch`:

- `uploadPair(formData: FormData): Promise<AnalysisResult>`
  - POST `/api/analyze/pair`
  - `formData` Felder: `real_image`, `fake_image`, optional `generatorType`, `prompt`
- `getPairList(): Promise<PairSummary[]>`
  - GET `/api/pairs`
- `getPairAnalysis(pairId: string): Promise<AnalysisResult>`
  - GET `/api/pairs/{pairId}`
- `getExamples(): Promise<AnalysisResult[]>`
  - GET `/api/examples`

Basisfunktionen:

- `fetchJson<T>(input, init?)` mit Error-Handling (Status prüfen, `response.json()`).
- Static Assets: nutze `analysis.gradients.*` oder `images.*` URLs direkt (werden über `/static/...` serviert).

## 3. Feature Layer (features/)

### uploadPair

- `useUploadPair` Hook:
  - lokal: selected files, generatorType, prompt
  - submit → `uploadPair(formData)`
  - nach Erfolg: router.push(`/pairs/${pairId}`)
- UI-Komponenten:
  - `UploadForm` (Datei Inputs, optional Prompt/Generator)
  - `UploadResultCard` (zeigt Gradienten/Features kurz an)

### pairList

- `usePairList`: GET `/api/pairs`
- UI:
  - `PairListTable` (Spalten: pairId, Preview real/fake, Actions)
  - `PairFilters` (optional: Suchfeld über pairId)
  - Klick → `/pairs/[pairId]`

### pairDetail

- `usePairAnalysis(pairId)`: GET `/api/pairs/{pairId}`
- Tabs/Ansichten:
  - Overview: Originalbilder (`ImagePreview`)
  - Gradients: real/fake/diff (`GradientPreview`)
  - Features: Tabellen (Mittelwerte, Std) + Charts (Histogramme)
  - Detectors: Badge/Panel pro Detector (`DetectorScoreBadge`)

### examples (optional)

- `useExamples`: GET `/api/examples`
- Re-use `PairDetail` Komponenten oder Gallery.

## 4. Routing (app/)

- `/` Landing: kurze Erklärung + CTA Upload / Browse
- `/upload`: rendert `UploadScreen` (Feature uploadPair)
- `/pairs`: rendert `PairListScreen`
- `/pairs/[pairId]`: rendert `PairDetailScreen`
- `/experiments` (optional): aggregierte Ergebnisse

Pages sollten minimal Logik enthalten und Feature-Komponenten rendern.

## 5. UI Schichten

- `components/ui`: shadcn primitives (Button, Card, Tabs, Table, Input, Alert, Skeleton)
- `components/domain`: Domain-spezifische Darstellungen
  - `ImagePreview`, `GradientPreview`, `FeatureSummaryCard`, `DetectorScoreBadge`, `PageHeader`, `LayoutShell`
- `components/charts` (optional): `GradientHistogram`, `FeatureRadarChart`
  - Input: bereits aufbereitete Daten (z. B. bins/counts)

## 6. Datenaufbereitung

- Histogramme: nutze `features.histogramReal|Fake`
- Scores: Anzeige z. B. mit Farbe (low/medium/high) anhand `score` (0..1)
- URLs: direkt in `<Image />` (Next Image) oder `<img>` laden; sie zeigen auf `/static/...`
- Error/Loading: Hooks liefern `{ data, isLoading, error }`

## 7. Testing

- API Stubs/Mocks für Storybook oder Tests können `AnalysisResult` Sample-Objekte aus JSON nutzen.
- E2E: Upload-Flow (POST), Detail-Ansicht (GET), Pair-List (GET).

## 8. Erweiterbarkeit

- Neue Detectoren erscheinen automatisch in `detectors` Map → UI iteriert über Keys.
- Zusätzliche Features können optional Felder hinzufügen; UI sollte tolerant sein (optionale chaining).
- Falls Backend Histogram-Bins ändert: Typ anpassen und Chart-Komponente parametrisieren.
