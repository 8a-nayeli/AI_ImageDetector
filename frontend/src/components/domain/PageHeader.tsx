import { cn } from '@/lib/utils/cn';

type PageHeaderProps = {
  title: string;
  description?: string;
  actions?: React.ReactNode;
  className?: string;
};

export function PageHeader({
  title,
  description,
  actions,
  className,
}: PageHeaderProps) {
  return (
    <div
      className={cn(
        'mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between',
        className
      )}
    >
      <div>
        <h1 className='text-2xl font-semibold text-slate-900'>{title}</h1>
        {description ? (
          <p className='mt-1 text-sm text-slate-600'>{description}</p>
        ) : null}
      </div>
      {actions ? (
        <div className='flex items-center gap-3'>{actions}</div>
      ) : null}
    </div>
  );
}
