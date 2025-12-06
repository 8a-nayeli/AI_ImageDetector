import Link from 'next/link';
import { forwardRef } from 'react';
import { cn } from '@/lib/utils/cn';

type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> &
  React.AnchorHTMLAttributes<HTMLAnchorElement> & {
    variant?: 'primary' | 'secondary' | 'ghost' | 'outline';
    size?: 'sm' | 'md' | 'lg';
    href?: string;
  };

const sizeStyles: Record<NonNullable<ButtonProps['size']>, string> = {
  sm: 'h-8 px-3 text-sm',
  md: 'h-10 px-4 text-sm',
  lg: 'h-12 px-6 text-base',
};

const variantStyles: Record<NonNullable<ButtonProps['variant']>, string> = {
  primary:
    'bg-slate-900 text-white hover:bg-slate-800 focus-visible:outline-slate-900',
  secondary:
    'bg-slate-100 text-slate-900 hover:bg-slate-200 border border-slate-200 focus-visible:outline-slate-300',
  ghost:
    'bg-transparent text-slate-900 hover:bg-slate-100 focus-visible:outline-slate-200',
  outline:
    'border border-slate-300 text-slate-900 hover:bg-slate-100 focus-visible:outline-slate-300',
};

export const Button = forwardRef<
  HTMLButtonElement | HTMLAnchorElement,
  ButtonProps
>(({ className, variant = 'primary', size = 'md', href, ...props }, ref) => {
  const classes = cn(
    'inline-flex items-center justify-center gap-2 rounded-md font-medium transition-colors focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 disabled:cursor-not-allowed disabled:opacity-70',
    sizeStyles[size],
    variantStyles[variant],
    className
  );

  if (href) {
    return (
      <Link
        ref={ref as React.Ref<HTMLAnchorElement>}
        href={href}
        className={classes}
        {...props}
      >
        {props.children}
      </Link>
    );
  }

  return (
    <button
      ref={ref as React.Ref<HTMLButtonElement>}
      className={classes}
      {...props}
    />
  );
});

Button.displayName = 'Button';
