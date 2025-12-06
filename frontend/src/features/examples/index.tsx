'use client';

import { Alert } from '@/components/ui/alert';
import { ExampleGrid } from './components/ExampleGrid';
import { useExamples } from './hooks/useExamples';

export function ExamplesSection() {
  const { examples, isLoading, error } = useExamples();

  return (
    <div className='flex flex-col gap-3'>
      <div className='flex items-center justify-between'>
        <h2 className='text-lg font-semibold text-slate-900'>Examples</h2>
        {isLoading ? (
          <span className='text-xs text-slate-500'>Loading examples …</span>
        ) : null}
      </div>
      {error ? <Alert variant='destructive'>{error}</Alert> : null}
      <ExampleGrid examples={examples} />
    </div>
  );
}
