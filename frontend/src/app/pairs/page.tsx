import { LayoutShell } from '@/components/domain/LayoutShell';
import { PairListScreen } from '@/features/pairList';

export default function PairsPage() {
  return (
    <LayoutShell>
      <PairListScreen />
    </LayoutShell>
  );
}
