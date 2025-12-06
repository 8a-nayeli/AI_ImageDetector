import { cn } from '@/lib/utils/cn';

type GradientPreviewProps = {
  src: string;
  title: string;
  description?: string;
  className?: string;
};

export function GradientPreview({
  src,
  title,
  description,
  className,
}: GradientPreviewProps) {
  return (
    <div
      className={cn(
        'flex flex-col gap-2 rounded-lg border border-slate-200 bg-white p-3 shadow-sm',
        className
      )}
    >
      <div className='relative h-56 w-full overflow-hidden rounded-md bg-slate-100'>
        {/* eslint-disable-next-line @next/next/no-img-element */}
        <img
          src={src}
          alt={title}
          className='h-full w-full object-contain'
          loading='lazy'
        />
      </div>
      <div>
        <p className='text-sm font-medium text-slate-900'>{title}</p>
        {description ? (
          <p className='text-xs text-slate-600'>{description}</p>
        ) : null}
      </div>
    </div>
  );
}
