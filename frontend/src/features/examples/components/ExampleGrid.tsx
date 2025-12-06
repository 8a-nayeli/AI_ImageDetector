import Link from 'next/link';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { DetectorScoreBadge } from '@/components/domain/DetectorScoreBadge';
import type { AnalysisResult } from '@/lib/types/pairs';

type ExampleGridProps = {
  examples: AnalysisResult[];
};

export function ExampleGrid({ examples }: ExampleGridProps) {
  if (examples.length === 0) {
    return <p className='text-sm text-slate-600'>Keine Beispiele vorhanden.</p>;
  }

  return (
    <div className='grid grid-cols-1 gap-4 md:grid-cols-2'>
      {examples.map((example) => (
        <Link key={example.pairId} href={`/pairs/${example.pairId}`}>
          <Card className='border-slate-800 bg-slate-900 text-slate-100 transition hover:-translate-y-0.5 hover:shadow-lg'>
            <CardHeader className='flex flex-row items-center justify-between'>
              <div>
                <CardTitle className='text-slate-100'>
                  Pair {example.pairId}
                </CardTitle>
                <p className='text-sm text-slate-300'>Click for details</p>
              </div>
            </CardHeader>
            <CardContent className='flex flex-col gap-3'>
              <div className='grid grid-cols-2 gap-2'>
                <div className='space-y-1'>
                  <p className='text-xs uppercase tracking-wide text-slate-400'>
                    Real
                  </p>
                  <div className='rounded-md bg-slate-800 p-2'>
                    {/* eslint-disable-next-line @next/next/no-img-element */}
                    <img
                      src={example.images.realImage.url}
                      alt='Real image'
                      className='h-20 w-full rounded object-cover'
                    />
                  </div>
                </div>
                <div className='space-y-1'>
                  <p className='text-xs uppercase tracking-wide text-slate-400'>
                    Fake
                  </p>
                  <div className='rounded-md bg-slate-800 p-2'>
                    {/* eslint-disable-next-line @next/next/no-img-element */}
                    <img
                      src={example.images.fakeImage.url}
                      alt='Fake image'
                      className='h-20 w-full rounded object-cover'
                    />
                  </div>
                </div>
              </div>

              <div className='flex flex-col gap-2'>
                <span className='text-xs uppercase tracking-wide text-slate-400'>
                  Detector scores
                </span>
                {Object.entries(example.detectors).map(([name, detector]) => (
                  <div
                    key={name}
                    className='flex flex-col gap-1 rounded-md bg-slate-800/80 px-3 py-2'
                  >
                    <DetectorScoreBadge
                      name={name}
                      score={detector.score}
                      explanation={detector.explanation}
                      onDark
                      showName
                    />
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </Link>
      ))}
    </div>
  );
}
