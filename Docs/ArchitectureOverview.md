# Architecture Overview

This document describes the system at a high level:

- which parts exist
- how they communicate
- which contracts exist between layers

Details for individual parts:

- `Docs/Backend.md`
- `Docs/Frontend.md`

---

## 1. High-Level System Diagram

Abstract view:

```txt
┌─────────────────────────────────────────────┐
│                  Frontend                  │
│         (Next.js + shadcn UI)              │
│                                             │
│  - Upload Pair UI                          │
│  - Pair Gallery                            │
│  - Pair Detail View                        │
│  - Charts and Visualizations               │
└───────────────▲────────────────────────────┘
                │ HTTP JSON API
                │
┌───────────────┴────────────────────────────┐
│                  Backend                   │
│           (Python + FastAPI etc.)          │
│                                             │
│  API Layer                                  │
│  Data Access Layer                          │
│  Detection Layer                            │
│  Core Analysis Layer                        │
│  Domain Layer                               │
└───────────────▲────────────────────────────┘
                │
                │ File system / DB
                │
┌───────────────┴────────────────────────────┐
│                  Storage                   │
│  - Raw images (real, fake)                 │
│  - Gradient visualizations                 │
│  - Analysis results                        │
│  - Experiments                             │
└────────────────────────────────────────────┘
```

---

## 2. Bounded Contexts

Three logical contexts:

1. **Frontend context**  
   Presentation, interaction, storytelling of results
2. **Analysis context (Backend Domain + Core + Detection)**  
   Domain logic for image pairs, gradients, features, detectors
3. **Persistence context (Data Access + Storage)**  
   Storing and retrieving data, without analysis logic

Each context has clear contracts and communicates only through these interfaces.

---

## 3. Core Domain Terms and Contracts

These terms are the shared vocabulary between frontend and backend. They travel as JSON over the API and are mirrored in TypeScript and Python.

### 3.1 ImagePair

Represents a pair of real and generated images.

Key fields:

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

Frontend contract:

- Frontend only needs `pairId` and URLs, not file paths
- Backend decides how files are stored

### 3.2 AnalysisResult

Represents the analysis of an `ImagePair`.

Core fields:

- `pairId`
- `gradients`
  - `realGradientUrl`
  - `fakeGradientUrl`
  - optional `diffGradientUrl`
- `features`
  - e.g., `meanGradientReal`
  - `meanGradientFake`
  - `histogramReal`
  - `histogramFake`
- `detectors`
  - list or map keyed by detector name
  - e.g., `gradient_stats` with
    - `score`
    - optional `explanation`

Frontend contract:

- Render images/visualizations via URLs
- Features are numeric data for tables/charts
- Detector results are simple flat objects

Backend contract:

- For a `pairId` either no `AnalysisResult` exists or a consistent object matching this schema

---

## 4. API Contracts Between Frontend and Backend

The API is the central boundary. The frontend talks to the backend only via HTTP + JSON.

Three endpoint groups:

1. Upload and analysis
2. Read analyses
3. Examples and experiments

### 4.1 Upload and Analysis

**Endpoint**

- `POST /api/analyze/pair`

**Request contract**

- multipart form data with
  - `real_image`
  - `fake_image`
  - optional `generatorType`
  - optional `prompt`

Backend responsibilities:

- Validate and store images
- Create `ImagePair` domain object
- Run analysis pipeline
- Persist `AnalysisResult`
- Assign consistent `pairId`

**Response contract**

- HTTP 201 with `AnalysisResult` object
- minimally contains
  - `pairId`
  - `images`
  - `gradients`
  - `features`
  - `detectors`

Frontend responsibilities:

- Parse response
- Navigate to `/pairs/[pairId]`
- Pass data to UI components

### 4.2 Reading Analyses

**Endpoint**

- `GET /api/pairs`
  - returns list of known image pairs, optionally with short summaries

**Endpoint**

- `GET /api/pairs/{pairId}`
  - returns full `AnalysisResult` for that pair

Contracts:

- Backend guarantees `pairId` is unique and stable
- Frontend treats `pairId` as primary key in URL and internally

### 4.3 Examples and Experiments

Optional but useful endpoints:

- `GET /api/examples`

  - returns predefined example pairs

- `GET /api/experiments`
  - aggregated results of multiple analyses

These can be shown in the frontend in “Overview” or “Experiments”. Field details are in `Backend.md`.

---

## 5. Relationships Between Backend Layers

One-way dependency flow:

```txt
API Layer
  ↓
Data Access Layer   Detection Layer
  ↓                 ↓
      Core Analysis Layer
              ↓
          Domain Layer
```

- Domain layer is the base; only domain concepts
- Core analysis works on domain types; no HTTP details
- Detection consumes core output and returns domain-conformant scores
- Data access stores and retrieves domain objects
- API layer orchestrates domain functions/repositories; no gradient internals

See `Backend.md` for more detail.

---

## 6. Relationships Between Frontend Layers

Similar clear separation:

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

- Routes handle routing and layout shells
- Feature layer encapsulates data fetching/use cases (e.g., Analyze Pair, Pair List)
- Domain UI components know domain structures, not fetch logic
- UI building blocks are pure presentation
- API client defines `getPairAnalysis`, `uploadPair`, `getPairList`
- Types mirror `AnalysisResult`, `ImagePair`, etc.

Details in `Frontend.md`.

---

## 7. End-to-End Flow Contract

Flow from upload to visualization:

1. User opens `/upload`
2. Feature `uploadPair` collects files/metadata
3. Feature hook calls `lib/api/pairs.uploadPair`
4. API client sends `POST /api/analyze/pair`
5. Backend API layer:
   - maps request to domain objects (`ImagePair`)
   - triggers core analysis (`gradients`, `features`)
   - calls detection layer (`scores`)
   - stores everything via data access layer
   - builds `AnalysisResult`
6. Backend responds with `AnalysisResult` JSON
7. Frontend receives it:
   - parses into `AnalysisResult` type
   - navigates to `/pairs/[pairId]`
   - passes data to domain components (`PairOverview`, `GradientView`, `FeatureTable`, `DetectorScorePanel`)

Key contract:

- `AnalysisResult` is the central exchange object between backend and frontend
- It is versioned/stable; changes are intentional and documented

---

## 8. Extensibility and State-of-the-Art Integration

Architecture is chosen so you can add new scientific methods without major rewrites.

- New detectors

  - backend: add a detector in the detection layer
  - `AnalysisResult.detectors` just gains another entry
  - frontend shows more scores automatically if it iterates the structure

- New visualizations

  - frontend: add domain components/charts
  - they keep consuming `AnalysisResult`
  - no API change if new fields are optional

- New data sources or storage
  - data access layer can switch from files to SQLite/DB
  - contracts to domain objects stay identical

Details for extending detectors and visualizations are in `Backend.md` and `Frontend.md`.
