# Image Analysis Backend

FastAPI-based backend for analyzing image pairs (real vs. generated) by computing gradient maps, extracting simple features, and running baseline detectors. It follows the layered architecture described in `Docs/ArchitectureOverview.md` and `Docs/Backend.md`.

## Quickstart

1. Install dependencies (editable mode recommended):
   ```bash
   cd backend
   pip install -e .
   ```
2. Create a `.env` based on `.env.example` to set data directories.
3. Run the server:
   ```bash
   uvicorn app:app --reload
   ```
4. Open docs: http://127.0.0.1:8000/docs

## Layout

- `src/`
  - `domain/`: core data models (`ImagePair`, `AnalysisResult`, detectors)
  - `core/`: image I/O, gradients, features, visualization
  - `detection/`: detector interfaces and baseline implementations
  - `data_access/`: filesystem storage and repositories
  - `api/`: FastAPI routes and schemas
  - `config/`: settings and paths
  - `utils/`: logging and error helpers

Data is stored under `data/` (configurable). Images and gradient artifacts are saved to disk; analyses are stored as JSON.
