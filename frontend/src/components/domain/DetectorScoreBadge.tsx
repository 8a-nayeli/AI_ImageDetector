import { Badge } from '@/components/ui/badge';

type DetectorScoreBadgeProps = {
  name: string;
  score: number;
  explanation?: string | null;
  onDark?: boolean;
  showName?: boolean;
};

export function DetectorScoreBadge({
  name,
  score,
  explanation,
  onDark = false,
  showName = true,
}: DetectorScoreBadgeProps) {
  const variant =
    score >= 0.75 ? 'success' : score >= 0.5 ? 'warning' : 'default';

  const scoreClass = onDark
    ? 'text-slate-100'
    : 'text-slate-900 dark:text-slate-100';
  const explanationClass = onDark
    ? 'text-slate-300'
    : 'text-slate-600 dark:text-slate-300';

  return (
    <div className='flex items-center gap-2'>
      {showName ? <Badge variant={variant}>{name}</Badge> : null}
      <span className={`text-sm font-semibold ${scoreClass}`}>
        {score.toFixed(3)}
      </span>
      {explanation ? (
        <span className={`text-xs ${explanationClass}`}>{explanation}</span>
      ) : null}
    </div>
  );
}
