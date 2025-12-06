import { cn } from '@/lib/utils/cn';

type BadgeProps = React.HTMLAttributes<HTMLSpanElement> & {
  variant?: 'default' | 'success' | 'warning';
};

export function Badge({
  className,
  variant = 'default',
  ...props
}: BadgeProps) {
  const variantClass =
    variant === 'success'
      ? 'bg-emerald-100 text-emerald-800'
      : variant === 'warning'
      ? 'bg-amber-100 text-amber-800'
      : 'bg-slate-100 text-slate-800';

  return (
    <span
      className={cn(
        'inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium',
        variantClass,
        className
      )}
      {...props}
    />
  );
}
