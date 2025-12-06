import Link from 'next/link';
import { cn } from '@/lib/utils/cn';

type LayoutShellProps = {
  children: React.ReactNode;
  className?: string;
};

export function LayoutShell({ children, className }: LayoutShellProps) {
  return (
    <div className='min-h-screen bg-slate-50 text-slate-900'>
      <header className='border-b border-slate-200 bg-white'>
        <div className='mx-auto flex max-w-6xl items-center justify-between px-6 py-4'>
          <Link href='/' className='text-lg font-semibold tracking-tight'>
            Gradient Analyzer
          </Link>
          <nav className='flex items-center gap-4 text-sm font-medium'>
            <Link
              className='text-slate-700 hover:text-slate-900'
              href='/upload'
            >
              Upload
            </Link>
            <Link className='text-slate-700 hover:text-slate-900' href='/pairs'>
              Pairs
            </Link>
          </nav>
        </div>
      </header>
      <main className={cn('mx-auto max-w-6xl px-6 py-8', className)}>
        {children}
      </main>
    </div>
  );
}
