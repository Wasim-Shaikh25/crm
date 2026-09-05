import React, { useEffect, useState } from 'react';
import api from '@/lib/axios';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Skeleton } from '@/components/ui/skeleton';
import { Cell, Pie, PieChart, ResponsiveContainer, Tooltip } from 'recharts';

export default function OfmDashboard() {
  const [s, setS] = useState(null);
  useEffect(() => { api.get('/lens/dashboard/summary').then((r) => setS(r.data)).catch(() => {}); }, []);
  if (!s) return <Skeleton className="h-64 w-full" />;
  const data = Object.entries(s.ofmCountByStatus || {}).map(([name, value]) => ({ name, value }));
  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">OFM Dashboard</h1>
      <div className="flex flex-wrap gap-4">
        <Card className="min-w-48"><CardHeader className="pb-1"><CardTitle className="text-sm text-muted-foreground">Total OFMs</CardTitle></CardHeader><CardContent><p className="text-3xl font-bold">{s.ofmCount}</p></CardContent></Card>
        {data.map((d) => <Card key={d.name} className="min-w-36"><CardContent className="pt-4"><Badge>{d.name}</Badge><p className="mt-2 text-2xl font-bold">{d.value}</p></CardContent></Card>)}
      </div>
      <Card><CardHeader><CardTitle className="text-base">OFM by Status</CardTitle></CardHeader><CardContent>
        <ResponsiveContainer width="100%" height={300}><PieChart><Pie data={data} dataKey="value" nameKey="name" outerRadius={100} label>{data.map((_, i) => <Cell key={i} fill={`hsl(${190 + i * 30} 70% 45%)`} />)}</Pie><Tooltip /></PieChart></ResponsiveContainer>
      </CardContent></Card>
    </div>
  );
}
