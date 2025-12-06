# Gradient Analyzer

Analyze real vs. generated images by computing Sobel gradients, features, and detector scores. Built with FastAPI (backend) and Next.js (frontend).

## Features

- Upload a real and a generated image; get an analysis result with gradient maps, features, and detector scores.
- Browse saved analyses and open detail views (images, gradients, features, detectors).
- Example gallery with thumbnails and scores.

## Tech Stack

- Backend: Python, FastAPI, Pydantic, OpenCV, NumPy
- Frontend: Next.js (App Router), React, TypeScript

## Prerequisites

- Python 3.11+
- Node.js 18+

## Backend Setup

```bash
cd backend
cp env.example .env             # adjust DATA_DIR, PUBLIC_BASE_URL if needed
pip install -e .
uvicorn app:app --reload
```

Backend runs on `http://localhost:8000` and serves static files under `/static`.

## Frontend Setup

```bash
cd frontend
cp env.example .env.local       # set NEXT_PUBLIC_API_BASE_URL, e.g. http://localhost:8000
npm install
npm run dev
```

Frontend runs on `http://localhost:3000`.

## API (backend)

- `POST /api/analyze/pair` — multipart: `real_image`, `fake_image`, optional `generatorType`, `prompt`. Returns `AnalysisResult`.
- `GET /api/pairs` — list analyses.
- `GET /api/pairs/{pairId}` — analysis detail.
- `GET /api/examples` — example analyses (if present).
- `GET /health` — health check.

## Data Storage

- Images: `data/images/real|fake/<pair>_*.png`
- Gradients: `data/gradients/<pair>/{real,fake,diff}.png`
- Analyses JSON: `data/analyses/<pair>.json`

## Notes

- Make sure `PUBLIC_BASE_URL` points to the backend origin so image URLs resolve in the frontend.
- Re-upload pairs after changing the URL config to regenerate stored URLs.
