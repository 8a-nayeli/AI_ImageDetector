import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { DetectorScoreBadge } from '@/components/domain/DetectorScoreBadge';
import type { AnalysisResult } from '@/lib/types/pairs';

type DetectorScorePanelProps = {
  analysis: AnalysisResult;
};

export function DetectorScorePanel({ analysis }: DetectorScorePanelProps) {
  const entries = Object.entries(analysis.detectors);

  if (entries.length === 0) {
    return (
      <p className='text-sm text-slate-600'>Keine Detektor-Scores verfügbar.</p>
    );
  }

  return (
    <Card className='border-slate-800 bg-slate-900 text-slate-100'>
      <CardHeader>
        <CardTitle className='text-slate-100'>Detector Scores</CardTitle>
      </CardHeader>
      <CardContent className='flex flex-col gap-3'>
        {entries.map(([name, detector]) => (
          <DetectorScoreBadge
            key={name}
            name={name}
            score={detector.score}
            explanation={detector.explanation}
            onDark
            showName={false}
          />
        ))}
      </CardContent>
    </Card>
  );
}
