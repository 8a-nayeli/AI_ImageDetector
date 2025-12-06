import { ImagePreview } from '@/components/domain/ImagePreview';
import type { AnalysisResult } from '@/lib/types/pairs';

type PairOverviewProps = {
  analysis: AnalysisResult;
};

export function PairOverview({ analysis }: PairOverviewProps) {
  return (
    <div className='grid grid-cols-1 gap-4 md:grid-cols-2'>
      <ImagePreview
        src={analysis.images.realImage.url}
        label='Real'
        meta={analysis.images.realImage.source ?? undefined}
      />
      <ImagePreview
        src={analysis.images.fakeImage.url}
        label='Fake'
        meta={analysis.images.fakeImage.generatorType ?? undefined}
      />
    </div>
  );
}
