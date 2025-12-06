'use client';

import { Alert } from '@/components/ui/alert';
import { Button } from '@/components/ui/button';
import { PageHeader } from '@/components/domain/PageHeader';
import { usePairList } from './hooks/usePairList';
import { PairListTable } from './components/PairListTable';

export function PairListScreen() {
  const { pairs, isLoading, error } = usePairList();

  return (
    <div className='flex flex-col gap-4'>
      <PageHeader
        title='Pairs'
        description='Alle bekannten Bildpaare, sortiert nach Pair ID. Klick zum Öffnen der Detailanalyse.'
        actions={
          <Button variant='secondary' href='/upload'>
            Neues Paar hochladen
          </Button>
        }
      />

      {error ? <Alert variant='destructive'>{error}</Alert> : null}

      {isLoading ? (
        <p className='text-sm text-slate-600'>Lade Paare …</p>
      ) : (
        <PairListTable pairs={pairs} />
      )}
    </div>
  );
}
