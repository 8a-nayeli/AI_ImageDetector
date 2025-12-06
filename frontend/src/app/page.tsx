import { LayoutShell } from '@/components/domain/LayoutShell';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { ExamplesSection } from '@/features/examples';

export default function Home() {
  return (
    <LayoutShell>
      <div className='grid grid-cols-1 gap-8 lg:grid-cols-[1.2fr_0.8fr]'>
        <div className='flex flex-col gap-4'>
          <p className='text-sm font-semibold uppercase tracking-wide text-slate-500'>
            Gradient Analyzer
          </p>
          <h1 className='text-3xl font-semibold text-slate-900'>
            Detect generated images via gradient analysis.
          </h1>
          <p className='text-lg text-slate-700'>
            Upload an image pair; we compute Sobel gradients, features, and
            detector scores. Browse existing analyses or start with our
            examples.
          </p>
          <div className='flex flex-wrap gap-3'>
            <Button href='/upload'>Analyze image pair</Button>
            <Button variant='secondary' href='/pairs'>
              Browse analyses
            </Button>
          </div>
        </div>

        <Card className='self-start'>
          <CardContent className='flex flex-col gap-3 p-6'>
            <h2 className='text-lg font-semibold text-slate-900'>
              Pipeline overview
            </h2>
            <ul className='list-disc space-y-2 pl-5 text-sm text-slate-700'>
              <li>Upload real and generated image</li>
              <li>Gradient maps (Sobel) and differences</li>
              <li>Statistical features and histograms</li>
              <li>Detectors provide scores + explanations</li>
            </ul>
          </CardContent>
        </Card>
      </div>

      <ExamplesSection />
    </LayoutShell>
  );
}
