import { LayoutShell } from '@/components/domain/LayoutShell';
import { PairDetailScreen } from '@/features/pairDetail';

type PairDetailPageProps = {
  params: Promise<{ pairId: string }>;
};

export default async function PairDetailPage({ params }: PairDetailPageProps) {
  const { pairId } = await params;

  return (
    <LayoutShell>
      <PairDetailScreen pairId={pairId} />
    </LayoutShell>
  );
}
