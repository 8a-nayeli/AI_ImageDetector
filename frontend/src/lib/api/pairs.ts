import { getJson, postForm } from './client';
import type { AnalysisResult, ImagePair } from '../types/pairs';

export type UploadPairPayload = {
  realImage: File;
  fakeImage: File;
  generatorType?: string;
  prompt?: string;
};

export async function uploadPair(payload: UploadPairPayload) {
  const form = new FormData();
  form.append('real_image', payload.realImage);
  form.append('fake_image', payload.fakeImage);

  if (payload.generatorType) {
    form.append('generatorType', payload.generatorType);
  }

  if (payload.prompt) {
    form.append('prompt', payload.prompt);
  }

  return postForm<AnalysisResult>('/api/analyze/pair', form);
}

export function getPairList() {
  return getJson<ImagePair[]>('/api/pairs');
}

export function getPairAnalysis(pairId: string) {
  return getJson<AnalysisResult>(`/api/pairs/${pairId}`);
}

export function getExamples() {
  return getJson<AnalysisResult[]>('/api/examples');
}
