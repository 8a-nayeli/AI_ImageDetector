## 1. Goals of the Frontend Architecture

The frontend should:

1. Upload image pairs and send them to the backend
2. Visualize analyses clearly:
   - original images
   - gradient maps
   - features and detector scores
3. Browse and open existing analyses
4. Provide clean, reusable UI with shadcn components
5. Be clearly separated into:
   - routing
   - feature logic
   - UI components
   - API access

Assumes Next.js App Router.

---

## 2. High-Level Structure

In a monorepo, e.g.:

```text
frontend/
  app/
  src/ (or directly in app)
  components/
  features/
  lib/
  styles/
```

Organize by “features” rather than “tech”: e.g., AnalyzePair, PairGallery.

---

## 3. Routing Layer (Next.js app/)

### Key routes

- `/`  
  Landing page

  - brief description of the project
  - call to action: “Upload pair” or “Browse examples”

- `/upload`  
  Upload page

  - form for real and generated image
  - optional metadata (generator, prompt)
  - sends request to backend
  - then redirects to detail page of the created pair

- `/pairs`  
  Gallery / overview

  - list of all pairs with key info
  - filtering and sorting

- `/pairs/[pairId]`  
  Detail analysis page

  - shows all details for a pair
  - tabs:
    - Overview
    - Gradient Space
    - Features
    - Detectors

- optional `/experiments`  
  Overview of experiment runs (e.g., multiple detectors on a dataset)

### App directory structure

```text
app/
  layout.tsx            # shell, navbar, theme provider
  page.tsx              # landing
  upload/
    page.tsx
  pairs/
    page.tsx            # list
    [pairId]/
      page.tsx          # detail
  experiments/
    page.tsx            # optional
```

Route pages should contain minimal logic—compose feature components.

---

## 4. Feature Layer

Folder `features/` encapsulates use-case logic. Each feature has:

- container components (data fetching, state, routing)
- pure UI components (props only)
- API hooks or service functions

### Example feature folders

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

Idea:

- `hooks/` call `lib/api` and manage loading/error/data
- `components/` expect data via props, focus on rendering
- `index.ts` exports entry components for routes

---

## 5. API and Data Access in the Frontend

Avoid scattered `fetch`. Instead:

```text
lib/
  api/
    client.ts          # base request helpers, e.g., fetchJson
    pairs.ts           # getPairList, getPairDetails, uploadPair
    experiments.ts     # optional
  types/
    analysis.ts        # TS types mirrored from backend domain/API
    pairs.ts
  utils/
    formatters.ts      # e.g., display scores, percentages
```

Typical functions in `lib/api/pairs.ts`:

- `uploadPair(formData)`
- `getPairAnalysis(pairId)`
- `getPairList(filters)`

Feature hooks (`usePairAnalysis`, `usePairList`, etc.) call these and can wrap React Query or manual state.

---

## 6. UI Layer with shadcn

Separate:

1. Low-level UI building blocks (buttons, cards, tabs)
2. Domain-specific components (GradientViewer, FeatureTable, etc.)

### 6.1 Low-level UI

Folder:

```text
components/ui/
```

Contains generated or slightly adapted shadcn components:

- `button.tsx`
- `card.tsx`
- `tabs.tsx`
- `input.tsx`
- `label.tsx`
- `table.tsx`
- `alert.tsx`
- etc.

These are generic, domain-agnostic.

### 6.2 Domain UI components

Folder:

```text
components/domain/
  ImagePreview.tsx
  GradientPreview.tsx
  FeatureSummaryCard.tsx
  DetectorScoreBadge.tsx
  LayoutShell.tsx
  PageHeader.tsx
```

These combine shadcn UI with domain concepts, still API-agnostic. They receive everything via props.

Examples:

- `ImagePreview` takes `src`, `label`, etc.
- `GradientPreview` takes `src` for a gradient map
- `FeatureSummaryCard` takes a reduced feature object and shows it
- `DetectorScoreBadge` takes `score` and `detectorName`, shows colored badge

---

## 7. State Management

Usually sufficient:

- server components for static parts
- client components + hooks for interactive areas
- page data via hooks using `fetch` or React Query

Recommendation:

- No global Redux—overkill
- Feature hook per use case, e.g., `usePairAnalysis(pairId)`
  - handles request to `/api/pairs/{id}`
  - returns `{ data, isLoading, error }`
- Navigation/selection primarily via URL params
  - e.g., filters as query params on `/pairs`

Keeps state traceable and aligned with routing—great for debugging.

---

## 8. Typical Page Compositions

Example for `PairDetail` page (architecture level):

1. `app/pairs/[pairId]/page.tsx`
   - reads `pairId` from URL
   - renders a feature component, e.g., `PairDetailScreen`
2. `features/pairDetail/index.ts`
   - exports `PairDetailScreen`
3. `PairDetailScreen` (feature container)
   - calls `usePairAnalysis(pairId)`
   - shows loading/error
   - when data is ready, renders domain components, e.g.:
     - `PairTabs` with tabs: Overview, Gradients, Features, Detectors
4. Tabs contain smaller domain components
   - `PairOverview` (uses `ImagePreview`)
   - `GradientView` (uses `GradientPreview` for real/fake, maybe diff)
   - `FeatureTable`
   - `DetectorScorePanel`

Responsibility clarity:

- Page file: routing + shell
- Feature container: knows API + domain
- Domain components: know domain data + UI
- shadcn components: layout/styling only

---

## 9. Integrating Visualizations

If you need plotting (e.g., gradient magnitude histograms), encapsulate it:

```text
components/charts/
  GradientHistogram.tsx
  FeatureRadarChart.tsx
```

These expect prepared inputs, e.g.:

- `bins` and `counts` for histograms
- a feature object mapped to radial axes

Transform raw data to chart inputs in feature hooks or `lib/utils/transformers.ts`, not inside the chart, to keep charts generic and reusable.

---

## 10. Why This Plays Well with the Backend

- Mirror backend domain types in `lib/types` for type safety and consistency.
- Mirror backend API endpoints 1:1 in `lib/api`.
- Every major backend capability (analyze pair, list analyses) has a corresponding frontend feature.

So you can argue clearly:

- This route maps to this use case
- This use case calls these API endpoints
- The endpoints call these backend functions

Result: clean, modular, extensible, and scientifically usable.
