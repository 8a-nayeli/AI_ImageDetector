"use client";

import { useState } from "react";
import type { AnalysisResult } from "@/lib/types/pairs";
import { uploadPair, type UploadPairPayload } from "@/lib/api/pairs";

export function useUploadPair() {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const mutate = async (payload: UploadPairPayload): Promise<AnalysisResult> => {
    setIsLoading(true);
    setError(null);
    try {
      return await uploadPair(payload);
    } catch (err) {
      const message =
        err instanceof Error ? err.message : "Upload failed unexpectedly.";
      setError(message);
      throw err;
    } finally {
      setIsLoading(false);
    }
  };

  return { upload: mutate, isLoading, error, setError };
}
