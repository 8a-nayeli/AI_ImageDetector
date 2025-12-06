# Backend Implementation Guide

This document captures the concrete FastAPI implementation and explains how each endpoint and layer works, what artifacts are produced, and how to use them end-to-end. It mirrors the contracts from `Docs/ArchitectureOverview.md` and `Docs/Backend.md`.

---

## 1) Setup & Start

```bash
cd backend
pip install -e .
cp env.example .env   # set DATA_DIR, LOG_LEVEL
uvicorn app:app --reload
```

- `DATA_DIR` (default `./data`) is the root for images, gradients, JSON.
- Static serving: everything under `DATA_DIR` is reachable at `/static/...`.
- Docs: `http://127.0.0.1:8000/docs`
- Health: `GET /health` → `{"status": "ok"}`.

---

## 2) Project Layout (what lives where)

```
backend/
  pyproject.toml           # deps: fastapi, uvicorn, pydantic, opencv, numpy
  src/
    app.py                 # FastAPI app, router include, static mount
    config/settings.py     # env + paths (DATA_DIR, LOG_LEVEL)
    utils/                 # logging config, error classes
    domain/                # Pydantic models: ImagePair, AnalysisResult, DetectorDescriptor
    core/                  # image I/O, Sobel gradients, features, visualization helpers
    detection/             # BaseDetector + gradient_stats baseline
    data_access/           # FileStorage + repositories (pairs, analyses, experiments)
    api/
      routes/              # analyze_pair, pairs, examples
      schemas/             # PairSummary
      dependencies.py      # singleton instances for DI
```

---

## 3) Data Contracts (Pydantic models)

- `ImagePair`: `{ pairId, realImage, fakeImage }` with public URLs (no file paths leak to clients).
- `AnalysisResult`: `{ pairId, images, gradients, features, detectors }`.
- `DetectorResult`: `{ score, explanation? }`.
- URL fields (`images.*.url`, `gradients.*Url`) all point to `/static/...`.
- Histograms are normalized float arrays.

---

## 4) Storage Layout (filesystem)

- Images: `data/images/real/<pair>_real.png`, `data/images/fake/<pair>_fake.png`.
- Gradients: `data/gradients/<pair>/real.png|fake.png|diff.png`.
- Analyses: `data/analyses/<pair>.json`.
- Pair metadata: `data/pairs.json`.
- Experiments (optional): `data/experiments.json`.
- Static URLs are derived from the relative path inside `DATA_DIR`.

---

## 5) Endpoint Behavior (what each call does)

### POST `/api/analyze/pair`

- Request: `multipart/form-data`
  - `real_image` (required)
  - `fake_image` (required)
  - `generatorType` (optional)
  - `prompt` (optional)
- Processing pipeline:
  1. Save uploads to disk (real/fake) and build public URLs.
  2. Re-load saved images from disk (defensive read).
  3. Compute Sobel gradient magnitudes (`core.gradients.compute_gradient_magnitude`).
  4. Extract features: mean, std, normalized histograms (`core.features.extract_features`).
  5. Run baseline detector `gradient_stats` (score from mean-diff vs. std; capped 0..1).
  6. Save gradient artifacts (real, fake, diff) as PNGs.
  7. Persist `ImagePair` metadata and `AnalysisResult` JSON.
- Response: `201` with full `AnalysisResult`.
- Errors: `400` for invalid/empty images; `500` catch-all for unexpected failures.

### GET `/api/pairs`

- Returns `PairSummary[]` (pairId + real/fake image URLs). Use for gallery/overview.

### GET `/api/pairs/{pairId}`

- Returns full `AnalysisResult` for detail views/tabs.

### GET `/api/examples`

- Returns up to 5 analyses (same shape as `AnalysisResult`) for seeding examples.

### GET `/health`

- Liveness check.

---

## 6) How the layers interact (request → response)

1. API layer (FastAPI route) validates input and orchestrates.
2. Data access stores uploads; returns paths/URLs.
3. Core layer computes gradients/features (pure numpy/OpenCV, no HTTP).
4. Detection layer consumes features, returns scores/explanations.
5. Data access persists JSON + images/gradients, exposes URLs.
6. API layer serializes domain models (Pydantic) to JSON for the client.

Static serving is handled once in `app.py` via `StaticFiles(directory=settings.data_dir, ...)`, so any file under `DATA_DIR` is reachable at `/static/...`.

---

## 7) Detector details (current baseline)

- `gradient_stats`:
  - Input: `FeatureVector` (means/std + histograms).
  - Logic: absolute difference of mean gradients normalized by summed std → squashed to 0..1.
  - Output: `score` (0 = similar, 1 = very different), `explanation` string.
- Adding detectors: subclass `BaseDetector`, return `DetectorResult`, register in `api/dependencies.py`, and include in the detectors map when building `AnalysisResult`.

---

## 8) Error handling & validation

- Empty or undecodable uploads → `ValidationError` → HTTP 400.
- Missing pairId lookup → HTTP 404 in `GET /api/pairs/{pairId}`.
- Everything else → HTTP 500 with a generic message; logs contain details (set `LOG_LEVEL`).

---

## 9) Extensibility roadmap

- Detectors: add modules under `detection/`; keep the same interface.
- Features: expand `core/features.py` (e.g., frequency stats) and extend `FeatureVector` with optional fields.
- Storage: swap JSON/files for SQLite or another DB behind repository classes.
- Long-running jobs: move heavy detectors to background tasks/workers; keep the API contract identical.

---

## 10) Quick manual test checklist

- Upload: POST `/api/analyze/pair` with two PNG/JPEGs → expect 201 and URLs under `/static/`.
- Detail: GET `/api/pairs/{pairId}` → detectors map + features populated.
- List: GET `/api/pairs` → includes the new pairId.
- Static assets: open `gradients.realGradientUrl` in a browser → PNG served.
- Health: GET `/health` → `{"status": "ok"}`.
