## 1. Goals of the Backend Architecture

The backend should:

1. Accept and manage image pairs (real, fake)
2. Compute gradients and additional features
3. Encapsulate various detectors (baseline and SOTA)
4. Deliver results as clean JSON structures to the frontend
5. Make experiments repeatable, independent of the web part
6. Stay modular and extensible for future methods

This implies these layers:

- **Domain layer**: central data types and concepts
- **Core analysis layer**: image processing, gradients, features
- **Detection layer**: models and classifiers
- **Data layer**: storage of images, analyses, experiments
- **API layer**: HTTP interfaces
- **Config and infra**: settings, paths, logging
- **Tests**: unit and integration

---

## 2. Backend Folder Structure

Example:

```text
backend/
  src/
    domain/
    core/
    detection/
    data_access/
    api/
    config/
    utils/
  tests/
    unit/
    integration/
```

We walk through each layer below.

---

## 3. Domain Layer

`src/domain/`

Define **business concepts** without technical details—your vocabulary for the project.

Typical modules:

- `image_pair.py`

  - Concept of an image pair
    - real_image_id
    - fake_image_id
    - optional: generator_type, prompt, source_dataset

- `analysis_result.py`

  - Analysis result for an image pair
    - references an `ImagePair`
    - gradient maps (as references, not raw data)
    - feature vectors
    - detector scores per method

- `detector.py`

  - Abstract notion of a detector
    - name
    - description
    - input format (e.g., gradient maps, features)
    - output score

- `experiment.py`
  - An experiment run
    - which detectors
    - which dataset
    - configuration parameters
    - aggregated metrics

Important: the domain layer knows no frameworks, no file paths, no HTTP—only concepts and data structures.

---

## 4. Core Analysis Layer

`src/core/`

Holds the actual image processing and analysis logic: turns pixels into gradients and gradients into features.

Possible modules:

- `image_io.py`

  - Load/save images from file paths or binaries
  - Normalization (size, channels)
  - Convert to internal representation

- `gradients.py`

  - Functions to compute gradients
    - edge filters (Sobel, Scharr, Laplacian variants)
    - possibly autograd variants for experiments
  - Output: gradient maps per channel or combined

- `features.py`

  - Extract features from gradient maps
    - statistics (means, variance, histograms)
    - possibly frequency analysis in gradient space
  - Output: structured feature vector used by detectors

- `visualization.py`
  - Produce visualizable artifacts for the frontend
    - grayscale gradient images
    - heatmaps
    - diff maps real vs. fake in gradient space
  - Provides files or references for static serving

Core should assume almost nothing about environment—ideally no HTTP or DB knowledge.

---

## 5. Detection Layer

`src/detection/`

Encapsulates classification/detection based on core outputs.

Modules:

- `base.py`

  - Abstract interface for detectors
    - e.g., methods like `fit`, `predict`, `describe`
  - Defines expected inputs
    - features only
    - or direct gradient maps

- `gradient_stats_detector.py`

  - Simple, interpretable baseline detector
    - operates on statistical gradient features
  - Great for explaining differences

- `ml_detector.py`

  - Generic wrapper for ML models
    - loads models from disk
    - handles pre/post-processing

- `sota_gradient_detector.py`
  - Slot for a SOTA detector from a paper, e.g., CNN on gradient maps
  - Implements same interface as `BaseDetector` so API layer stays unchanged

Detection depends on Core, never the reverse.

---

## 6. Data Access Layer

`src/data_access/`

Handles how data is stored/retrieved safely.

Modules:

- `file_storage.py`

  - Responsible for
    - storage of raw images
    - storage of generated gradient visualizations
  - Rules, e.g., folder layout
    - `data/images/real`
    - `data/images/fake`
    - `data/gradients/<pair_id>/`

- `analysis_repository.py`

  - Store/load `AnalysisResult` objects
    - as JSON files
    - or a small DB like SQLite
  - Methods like
    - `save_analysis(result)`
    - `get_analysis(pair_id)`
    - `list_analyses(filters)`

- `pair_repository.py`

  - Manage `ImagePair` metadata
    - id assignment
    - mapping IDs to image paths
    - optional labels for ground truth

- `experiment_repository.py`
  - Store experiment runs and aggregates
  - important for later evaluation in Python or frontend

Data access talks to file system or DB but sticks to domain types.

---

## 7. API Layer

`src/api/`

Connects backend logic to the outside world (Next.js or other clients).

Typical split:

- `routes/` or `controllers/`

  - define endpoints and orchestrate flow
  - examples:
    - `analyze_pair_controller`
      - accepts uploads
      - delegates to core and detection
      - uses repositories to persist
      - builds an `AnalysisResult` domain object
      - serializes to API response
    - `examples_controller`
      - lists existing pairs/analyses
    - `experiments_controller`
      - start or query experiments

- `schemas/`
  - request/response models
  - mapping between domain objects and public API structure
  - clearly separated so domain and API evolve independently

The API layer knows HTTP, auth, validation, content types, but no internal gradient details. It only calls upper layers via their public functions.

---

## 8. Config and Utilities

`src/config/` and `src/utils/`

- `config/settings.py`

  - global settings
    - data paths
    - model paths
    - debug flags

- `config/env.py`

  - load environment variables

- `utils/logging.py`

  - central logging config

- `utils/errors.py`
  - custom errors to map into API responses

Keeps configuration centralized and avoids hardcoding.

---

## 9. Tests

`tests/`

- `tests/unit/`

  - test core functions:
    - gradient computation
    - feature extraction
    - simple detector logic

- `tests/integration/`
  - test full flows:
    - upload an image pair
    - save analyses
    - fetch result via API

Goal: rely less on manual frontend tests; validate core logic automatically.

---

## 10. Typical Flow Inside the Backend

To make the architecture tangible, the internal path for `analyze_pair`:

1. API layer
   - receives request
   - validates input
   - creates `ImagePair` domain object
2. Data access layer
   - stores raw images and metadata
   - returns paths or IDs
3. Core layer
   - loads images
   - computes gradients
   - extracts features
   - creates visualizations
4. Detection layer
   - takes features and optional gradients
   - computes scores with one or more detectors
5. Domain layer
   - composes an `AnalysisResult` object
6. Data access layer
   - stores `AnalysisResult`
7. API layer
   - maps `AnalysisResult` to a response
   - sends to frontend

Responsibility boundaries stay clear, so you can optimize each layer separately.

---

If desired, next step could define **domain types** and **API responses** concretely: exact fields of `AnalysisResult` and the JSON that Next.js will render.
