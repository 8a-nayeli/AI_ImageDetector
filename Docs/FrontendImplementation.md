# Frontend Implementation Guide (Next.js App Router)

This document captures the concrete implementation of the Next.js frontend that mirrors the contracts from `Docs/Frontend.md` and the FastAPI backend.

## 1) Setup & Start

```bash
cd frontend
cp env.example .env.local   # set NEXT_PUBLIC_API_BASE_URL (default: http://localhost:8000)
npm install
npm run dev
```

### Env

- `NEXT_PUBLIC_API_BASE_URL` points to the FastAPI server that serves `/api/*` and `/static/*`.

## 2) Types (src/lib/types/pairs.ts)

TypeScript mirrors the backend Pydantic models:

- `ImageInfo` `{ id, url, source?, generatorType?, prompt? }`
- `GradientArtifacts` `{ realGradientUrl, fakeGradientUrl, diffGradientUrl? }`
- `FeatureVector` `{ meanGradientReal, meanGradientFake, stdGradientReal, stdGradientFake, histogramReal, histogramFake }`
- `DetectorResult` `{ score, explanation? }`
- `AnalysisResult` `{ pairId, images: { realImage, fakeImage }, gradients, features, detectors: Record<string, DetectorResult> }`
- `ImagePair` (PairSummary) `{ pairId, realImage, fakeImage }`

## 3) API Layer (src/lib/api)

- `client.ts`: base helpers (`getJson`, `postForm`, `ApiError`), reading `NEXT_PUBLIC_API_BASE_URL`.
- `pairs.ts`:
  - `uploadPair({ realImage, fakeImage, generatorType?, prompt? })` → POST `/api/analyze/pair`
  - `getPairList()` → GET `/api/pairs`
  - `getPairAnalysis(pairId)` → GET `/api/pairs/{pairId}`
  - `getExamples()` → GET `/api/examples`

All responses are typed with the models above. Errors bubble as `ApiError`.

## 4) UI Building Blocks

### components/ui/

Lightweight shadcn-like primitives: `button`, `card`, `input`, `textarea`, `label`, `table`, `badge`, `alert`.

### components/domain/

Domain-facing presentation: `LayoutShell` (header + shell), `PageHeader`, `ImagePreview`, `GradientPreview`, `FeatureSummaryCard`, `DetectorScoreBadge`.

## 5) Feature Layer (src/features)

- `uploadPair`
  - Hook: `useUploadPair` handles loading/error around `uploadPair`.
  - UI: `UploadForm` (file inputs + optional generator/prompt) pushes to `/pairs/{pairId}` on success.
- `pairList`
  - Hook: `usePairList` fetches `/api/pairs`.
  - UI: `PairListTable` renders pair overview; `PairListScreen` shows actions + loading/error.
- `pairDetail`
  - Hook: `usePairAnalysis(pairId)` fetches `/api/pairs/{pairId}`.
  - UI: `PairTabs` with tabs for Overview (originals), Gradients, Features (stats + histogram preview strings), Detectors (badges).
- `examples`
  - Hook: `useExamples` fetches `/api/examples`.
  - UI: `ExamplesSection` + `ExampleGrid` for landing page cards.

Hooks expose `{ data, isLoading, error }` patterns; components stay presentation-focused.

## 6) Routing (src/app)

- `/` landing with CTA + `ExamplesSection`.
- `/upload` → `UploadPairScreen`.
- `/pairs` → `PairListScreen`.
- `/pairs/[pairId]` → `PairDetailScreen`.

Routes are thin; layout shell lives inside each page for clarity.

## 7) Data & UX Notes

- Images/gradients are rendered via provided URLs (served from backend static mount).
- Detector scores are color-coded badges (success ≥0.75, warning ≥0.5).
- Histogram data is shown as abbreviated numeric previews; charts can be added under `components/charts` if needed.
- Error and loading states are surfaced via alerts/text in each feature.

## 8) Extensibility

- New detectors automatically appear because the UI iterates the `detectors` map.
- Additional feature fields can be added to the types and rendered with new domain components without touching routes.
- Adding experiments page: create `app/experiments` and reuse list/detail patterns with new API helpers.
