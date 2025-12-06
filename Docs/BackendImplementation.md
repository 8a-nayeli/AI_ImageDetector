# Backend Implementation Guide

Dieses Dokument beschreibt die konkrete FastAPI-Implementierung basierend auf `Docs/ArchitectureOverview.md` und `Docs/Backend.md`.

## 1. Setup & Start

```bash
cd backend
pip install -e .
cp env.example .env   # Pfade/Log-Level anpassen
uvicorn app:app --reload
```

- `DATA_DIR` (Standard: `./data`) enthält Bilder, Gradienten, Analysen.
- Artefakte sind unter `/static/...` erreichbar (FastAPI StaticFiles).
- OpenAPI UI: `http://127.0.0.1:8000/docs`

## 2. Projektstruktur (Backend)

```
backend/
  pyproject.toml
  src/
    app.py                     # FastAPI App, static mount, Router
    config/settings.py         # Settings + Pfade
    utils/                     # Logging, Errors
    domain/                    # ImagePair, AnalysisResult, DetectorDescriptor
    core/                      # image_io, gradients, features, visualization
    detection/                 # BaseDetector, GradientStatsDetector
    data_access/               # FileStorage, Repositories (analysis, pair)
    api/
      routes/                  # analyze_pair, pairs, examples
      schemas/                 # PairSummary
      dependencies.py          # Singleton Instanzen (Settings, Storage, Detector)
```

## 3. Wichtige Domänen-Modelle

- `ImagePair`: `pairId`, `realImage`, `fakeImage` (URLs, optionale Metadaten).
- `AnalysisResult`: `pairId`, `images`, `gradients`, `features`, `detectors`.
- `DetectorResult`: `score`, optionale `explanation`.

Alle Felder nutzen JSON-Aliasse (z. B. `pairId`, `realGradientUrl`).

## 4. Datenhaltung (Filesystem)

- Bilder: `data/images/real/<pair>_real.png`, `data/images/fake/<pair>_fake.png`
- Gradienten: `data/gradients/<pair>/real.png|fake.png|diff.png`
- Analysen: `data/analyses/<pair>.json`
- Paar-Metadaten: `data/pairs.json`
- Experimente (optional): `data/experiments.json`

## 5. Endpoints (HTTP + JSON)

### POST `/api/analyze/pair`

- Request: `multipart/form-data`
  - `real_image`: UploadFile (required)
  - `fake_image`: UploadFile (required)
  - `generatorType`: optional
  - `prompt`: optional
- Ablauf:
  1. Speichern der Bilder auf FS (FileStorage)
  2. Gradienten: Sobel (`core.gradients.compute_gradient_magnitude`)
  3. Features: Mittelwerte, Std, Histogramme (`core.features.extract_features`)
  4. Baseline-Detector: `gradient_stats`
  5. Artefakte speichern (Gradienten, Diff)
  6. Persistieren von `AnalysisResult` (JSON) und `ImagePair`
- Response `201`: vollständiges `AnalysisResult`

### GET `/api/pairs`

- Liefert Liste von `PairSummary` (pairId, realImage, fakeImage).

### GET `/api/pairs/{pairId}`

- Liefert vollständiges `AnalysisResult` für das Paar.

### GET `/api/examples`

- Liefert bis zu 5 vorhandene Analysen (gleiche Struktur wie `AnalysisResult`).

### GET `/health`

- einfacher Liveness-Check.

## 6. Schichten & Verantwortlichkeiten

- **config**: Settings laden, Pfade erzeugen.
- **core**: pure Image/Feature-Funktionen ohne IO.
- **detection**: Detektoren auf Basis von Features.
- **data_access**: Dateien/JSON lesen & schreiben, URLs für Static Serving.
- **api**: Request Handling, Orchestrierung, Fehler → HTTP.

## 7. Erweiterungsideen

- Weitere Detektoren ergänzen (`detection/*`, selbes Interface).
- Zusätzliche Feature-Extractor in `core/features.py`.
- Persistenz auf SQLite migrieren (Repository-Interfaces beibehalten).
- Async IO oder Hintergrundjobs für lange Analysen.
