import { cn } from '@/lib/utils/cn';

type FeatureSummaryCardProps = {
  title: string;
  value: number | string;
  hint?: string;
  className?: string;
};

export function FeatureSummaryCard({
  title,
  value,
  hint,
  className,
}: FeatureSummaryCardProps) {
  return (
    <div
      className={cn(
        'rounded-lg border border-slate-200 bg-white p-4 shadow-sm',
        className
      )}
    >
      <p className='text-xs uppercase tracking-wide text-slate-500'>{title}</p>
      <p className='mt-1 text-2xl font-semibold text-slate-900'>{value}</p>
      {hint ? <p className='text-xs text-slate-500'>{hint}</p> : null}
    </div>
  );
}
