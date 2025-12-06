import { GradientPreview } from '@/components/domain/GradientPreview';
import type { AnalysisResult } from '@/lib/types/pairs';

type GradientViewProps = {
  analysis: AnalysisResult;
};

export function GradientView({ analysis }: GradientViewProps) {
  const { gradients } = analysis;
  return (
    <div className='grid grid-cols-1 gap-4 md:grid-cols-3'>
      <GradientPreview
        src={gradients.realGradientUrl}
        title='Real Gradient'
        description='Sobel Magnitude'
      />
      <GradientPreview
        src={gradients.fakeGradientUrl}
        title='Fake Gradient'
        description='Sobel Magnitude'
      />
      {gradients.diffGradientUrl ? (
        <GradientPreview
          src={gradients.diffGradientUrl}
          title='Gradient Diff'
          description='Differenz Real vs Fake'
        />
      ) : null}
    </div>
  );
}
