import Link from 'next/link';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import type { ImagePair } from '@/lib/types/pairs';
import { Badge } from '@/components/ui/badge';

type PairListTableProps = {
  pairs: ImagePair[];
};

export function PairListTable({ pairs }: PairListTableProps) {
  if (pairs.length === 0) {
    return <p className='text-sm text-slate-600'>Keine Paare vorhanden.</p>;
  }

  return (
    <div className='overflow-hidden rounded-lg border border-slate-200 bg-white shadow-sm'>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Pair ID</TableHead>
            <TableHead>Real</TableHead>
            <TableHead>Fake</TableHead>
            <TableHead>Generator</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {pairs.map((pair) => (
            <TableRow key={pair.pairId}>
              <TableCell>
                <Link
                  href={`/pairs/${pair.pairId}`}
                  className='font-semibold text-slate-900 hover:text-slate-700'
                >
                  {pair.pairId}
                </Link>
              </TableCell>
              <TableCell>
                <div className='flex flex-col'>
                  <span className='text-sm font-medium text-slate-900'>
                    {pair.realImage.id}
                  </span>
                  {pair.realImage.source ? (
                    <span className='text-xs text-slate-500'>
                      Quelle: {pair.realImage.source}
                    </span>
                  ) : null}
                </div>
              </TableCell>
              <TableCell>
                <div className='flex flex-col'>
                  <span className='text-sm font-medium text-slate-900'>
                    {pair.fakeImage.id}
                  </span>
                  {pair.fakeImage.prompt ? (
                    <span className='text-xs text-slate-500'>
                      Prompt: {pair.fakeImage.prompt}
                    </span>
                  ) : null}
                </div>
              </TableCell>
              <TableCell>
                {pair.fakeImage.generatorType ? (
                  <Badge>{pair.fakeImage.generatorType}</Badge>
                ) : (
                  <span className='text-xs text-slate-500'>n/a</span>
                )}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}
