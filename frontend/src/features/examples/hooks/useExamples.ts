'use client';

import { useEffect, useState } from 'react';
import { getExamples } from '@/lib/api/pairs';
import type { AnalysisResult } from '@/lib/types/pairs';

export function useExamples() {
  const [examples, setExamples] = useState<AnalysisResult[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let active = true;
    // eslint-disable-next-line react-hooks/set-state-in-effect
    setIsLoading(true);
    getExamples()
      .then((data) => {
        if (active) setExamples(data);
      })
      .catch((err) => {
        if (!active) return;
        const message =
          err instanceof Error
            ? err.message
            : 'Beispiele konnten nicht geladen werden.';
        setError(message);
      })
      .finally(() => {
        if (active) setIsLoading(false);
      });

    return () => {
      active = false;
    };
  }, []);

  return { examples, isLoading, error };
}
