import { cn } from '@/lib/utils/cn';

type AlertProps = React.HTMLAttributes<HTMLDivElement> & {
  variant?: 'default' | 'destructive';
};

export function Alert({
  className,
  variant = 'default',
  children,
  ...props
}: AlertProps) {
  const variantClass =
    variant === 'destructive'
      ? 'border-rose-300 bg-rose-50 text-rose-900'
      : 'border-slate-200 bg-slate-50 text-slate-900';

  return (
    <div
      className={cn(
        'flex gap-2 rounded-md border px-3 py-2 text-sm',
        variantClass,
        className
      )}
      {...props}
    >
      {children}
    </div>
  );
}
