'use client';

import { FormEvent, useState } from 'react';
import { useRouter } from 'next/navigation';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Alert } from '@/components/ui/alert';
import { useUploadPair } from '../hooks/useUploadPair';

export function UploadForm() {
  const router = useRouter();
  const { upload, isLoading, error, setError } = useUploadPair();
  const [realImage, setRealImage] = useState<File | null>(null);
  const [fakeImage, setFakeImage] = useState<File | null>(null);
  const [generatorType, setGeneratorType] = useState('');
  const [prompt, setPrompt] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setSuccessMessage('');
    setError(null);

    if (!realImage || !fakeImage) {
      setError('Please choose both real and generated images.');
      return;
    }

    try {
      const result = await upload({
        realImage,
        fakeImage,
        generatorType: generatorType || undefined,
        prompt: prompt || undefined,
      });
      setSuccessMessage('Upload successful – redirecting to analysis …');
      router.push(`/pairs/${result.pairId}`);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>Upload image pair</CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className='flex flex-col gap-4'>
          <div className='grid grid-cols-1 gap-4 md:grid-cols-2'>
            <div className='flex flex-col gap-2'>
              <Label htmlFor='realImage'>Real image</Label>
              <Input
                id='realImage'
                type='file'
                accept='image/*'
                onChange={(e) =>
                  setRealImage(e.target.files ? e.target.files[0] : null)
                }
                required
              />
            </div>
            <div className='flex flex-col gap-2'>
              <Label htmlFor='fakeImage'>Generated image</Label>
              <Input
                id='fakeImage'
                type='file'
                accept='image/*'
                onChange={(e) =>
                  setFakeImage(e.target.files ? e.target.files[0] : null)
                }
                required
              />
            </div>
          </div>

          <div className='grid grid-cols-1 gap-4 md:grid-cols-2'>
            <div className='flex flex-col gap-2'>
              <Label htmlFor='generatorType'>Generator (optional)</Label>
              <Input
                id='generatorType'
                placeholder='e.g. Stable Diffusion'
                value={generatorType}
                onChange={(e) => setGeneratorType(e.target.value)}
              />
            </div>
            <div className='flex flex-col gap-2'>
              <Label htmlFor='prompt'>Prompt (optional)</Label>
              <Textarea
                id='prompt'
                placeholder='How was the image generated?'
                value={prompt}
                onChange={(e) => setPrompt(e.target.value)}
              />
            </div>
          </div>

          {error ? <Alert variant='destructive'>{error}</Alert> : null}
          {successMessage ? <Alert>{successMessage}</Alert> : null}

          <div className='flex items-center justify-end gap-3'>
            <Button type='submit' disabled={isLoading}>
              {isLoading ? 'Uploading...' : 'Analyze'}
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}
