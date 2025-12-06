export type ImageInfo = {
  id: string;
  url: string;
  source?: string | null;
  generatorType?: string | null;
  prompt?: string | null;
};

export type ImagePair = {
  pairId: string;
  realImage: ImageInfo;
  fakeImage: ImageInfo;
};

export type AnalysisImages = {
  realImage: ImageInfo;
  fakeImage: ImageInfo;
};

export type GradientArtifacts = {
  realGradientUrl: string;
  fakeGradientUrl: string;
  diffGradientUrl?: string | null;
};

export type FeatureVector = {
  meanGradientReal: number;
  meanGradientFake: number;
  stdGradientReal: number;
  stdGradientFake: number;
  histogramReal: number[];
  histogramFake: number[];
};

export type DetectorResult = {
  score: number;
  explanation?: string | null;
};

export type AnalysisResult = {
  pairId: string;
  images: AnalysisImages;
  gradients: GradientArtifacts;
  features: FeatureVector;
  detectors: Record<string, DetectorResult>;
};
