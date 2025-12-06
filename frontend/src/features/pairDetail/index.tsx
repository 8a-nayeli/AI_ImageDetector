'use client';

import { Alert } from '@/components/ui/alert';
import { PageHeader } from '@/components/domain/PageHeader';
import { usePairAnalysis } from './hooks/usePairAnalysis';
import { PairTabs } from './components/PairTabs';

type PairDetailScreenProps = {
  pairId: string;
};

export function PairDetailScreen({ pairId }: PairDetailScreenProps) {
  const { analysis, isLoading, error } = usePairAnalysis(pairId);

  return (
    <div className='flex flex-col gap-4'>
      <PageHeader
        title={`Pair ${pairId}`}
        description='Detail view with gradients, features, and detector scores.'
      />

      {error ? <Alert variant='destructive'>{error}</Alert> : null}
      {isLoading ? (
        <p className='text-sm text-slate-600'>Loading analysis …</p>
      ) : null}
      {analysis ? <PairTabs analysis={analysis} /> : null}
    </div>
  );
}
