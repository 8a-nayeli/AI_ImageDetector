import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import type { FeatureVector } from '@/lib/types/pairs';
import { FeatureSummaryCard } from '@/components/domain/FeatureSummaryCard';

type FeatureTableProps = {
  features: FeatureVector;
};

export function FeatureTable({ features }: FeatureTableProps) {
  const histogramPreview = (values: number[]) =>
    values
      .slice(0, 8)
      .map((v) => v.toFixed(2))
      .join(' · ');

  return (
    <div className='grid grid-cols-1 gap-4 lg:grid-cols-3'>
      <FeatureSummaryCard
        title='Mean Gradient (Real)'
        value={features.meanGradientReal.toFixed(3)}
        hint='Average gradient magnitude of the real image'
      />
      <FeatureSummaryCard
        title='Mean Gradient (Fake)'
        value={features.meanGradientFake.toFixed(3)}
        hint='Average gradient magnitude of the generated image'
      />
      <FeatureSummaryCard
        title='Std Gradient Real/Fake'
        value={`${features.stdGradientReal.toFixed(
          3
        )} / ${features.stdGradientFake.toFixed(3)}`}
        hint='Variance comparison (real / generated)'
      />

      <Card className='lg:col-span-2 border-slate-800 bg-slate-900 text-slate-100'>
        <CardHeader>
          <CardTitle className='text-slate-100'>Histogram (Real)</CardTitle>
        </CardHeader>
        <CardContent>
          <p className='text-sm text-slate-200'>
            {histogramPreview(features.histogramReal)} …
          </p>
        </CardContent>
      </Card>

      <Card className='border-slate-800 bg-slate-900 text-slate-100'>
        <CardHeader>
          <CardTitle className='text-slate-100'>Histogram (Generated)</CardTitle>
        </CardHeader>
        <CardContent>
          <p className='text-sm text-slate-200'>
            {histogramPreview(features.histogramFake)} …
          </p>
        </CardContent>
      </Card>
    </div>
  );
}
