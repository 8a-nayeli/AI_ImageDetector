'use client';

import { useEffect, useState } from 'react';
import { getPairAnalysis } from '@/lib/api/pairs';
import type { AnalysisResult } from '@/lib/types/pairs';

export function usePairAnalysis(pairId: string) {
  const [analysis, setAnalysis] = useState<AnalysisResult | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!pairId) return;
    let active = true;
    // eslint-disable-next-line react-hooks/set-state-in-effect
    setIsLoading(true);
    getPairAnalysis(pairId)
      .then((data) => {
        if (active) setAnalysis(data);
      })
      .catch((err) => {
        if (!active) return;
        const message =
          err instanceof Error
            ? err.message
            : 'Analyse konnte nicht geladen werden.';
        setError(message);
      })
      .finally(() => {
        if (active) setIsLoading(false);
      });

    return () => {
      active = false;
    };
  }, [pairId]);

  return { analysis, isLoading, error };
}
