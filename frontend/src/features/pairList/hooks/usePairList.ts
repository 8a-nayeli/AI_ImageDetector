'use client';

import { useEffect, useState } from 'react';
import { getPairList } from '@/lib/api/pairs';
import type { ImagePair } from '@/lib/types/pairs';

export function usePairList() {
  const [pairs, setPairs] = useState<ImagePair[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let active = true;
    // eslint-disable-next-line react-hooks/set-state-in-effect
    setIsLoading(true);
    getPairList()
      .then((data) => {
        if (active) {
          setPairs(data);
        }
      })
      .catch((err) => {
        if (active) {
          const message =
            err instanceof Error ? err.message : 'Konnte Paare nicht laden.';
          setError(message);
        }
      })
      .finally(() => {
        if (active) setIsLoading(false);
      });

    return () => {
      active = false;
    };
  }, []);

  return { pairs, isLoading, error };
}
