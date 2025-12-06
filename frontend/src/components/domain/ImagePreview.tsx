import { cn } from '@/lib/utils/cn';

type ImagePreviewProps = {
  src: string;
  label: string;
  meta?: string;
  className?: string;
};

export function ImagePreview({
  src,
  label,
  meta,
  className,
}: ImagePreviewProps) {
  return (
    <figure
      className={cn(
        'flex flex-col gap-2 rounded-lg border border-slate-200 bg-white p-3 shadow-sm',
        className
      )}
    >
      <div className='relative h-56 w-full overflow-hidden rounded-md bg-slate-100'>
        {/* eslint-disable-next-line @next/next/no-img-element */}
        <img
          src={src}
          alt={label}
          className='h-full w-full object-contain'
          loading='lazy'
        />
      </div>
      <figcaption className='flex items-center justify-between text-sm text-slate-700'>
        <span className='font-medium'>{label}</span>
        {meta ? <span className='text-xs text-slate-500'>{meta}</span> : null}
      </figcaption>
    </figure>
  );
}
