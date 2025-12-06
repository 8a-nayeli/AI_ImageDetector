import { PageHeader } from '@/components/domain/PageHeader';
import { UploadForm } from './components/UploadForm';

export function UploadPairScreen() {
  return (
    <div className='flex flex-col gap-6'>
      <PageHeader
        title='Upload Pair'
        description='Upload a real and a generated image; we compute gradients, features, and detector scores.'
      />
      <UploadForm />
    </div>
  );
}
