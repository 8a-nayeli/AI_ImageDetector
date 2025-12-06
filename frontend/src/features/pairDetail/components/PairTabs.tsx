'use client';

import { useState } from 'react';
import { Button } from '@/components/ui/button';
import type { AnalysisResult } from '@/lib/types/pairs';
import { PairOverview } from './PairOverview';
import { GradientView } from './GradientView';
import { FeatureTable } from './FeatureTable';
import { DetectorScorePanel } from './DetectorScorePanel';

type PairTabsProps = {
  analysis: AnalysisResult;
};

const tabs = [
  { value: 'overview', label: 'Overview' },
  { value: 'gradients', label: 'Gradients' },
  { value: 'features', label: 'Features' },
  { value: 'detectors', label: 'Detectors' },
];

export function PairTabs({ analysis }: PairTabsProps) {
  const [activeTab, setActiveTab] = useState(tabs[0].value);

  return (
    <div className='flex flex-col gap-4'>
      <div className='flex flex-wrap gap-2'>
        {tabs.map((tab) => {
          const isActive = tab.value === activeTab;
          return (
            <Button
              key={tab.value}
              variant={isActive ? 'primary' : 'secondary'}
              size='sm'
              onClick={() => setActiveTab(tab.value)}
            >
              {tab.label}
            </Button>
          );
        })}
      </div>

      <div>
        {activeTab === 'overview' && <PairOverview analysis={analysis} />}
        {activeTab === 'gradients' && <GradientView analysis={analysis} />}
        {activeTab === 'features' && (
          <FeatureTable features={analysis.features} />
        )}
        {activeTab === 'detectors' && (
          <DetectorScorePanel analysis={analysis} />
        )}
      </div>
    </div>
  );
}
