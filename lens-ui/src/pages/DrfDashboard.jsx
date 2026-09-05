import React, { useEffect, useState } from 'react';
import api from '@/lib/axios';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Skeleton } from '@/components/ui/skeleton';
import { Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';

export default function DrfDashboard() {
  const [s, setS] = useState(null);
  useEffect(() => { api.get('/lens/dashboard/summary').then((r) => setS(r.data)).catch(() => {}); }, []);
  if (!s) return <Skeleton className="h-64 w-full" />;
  const data = Object.entries(s.drfCountByType || {}).map(([name, count]) => ({ name, count }));
  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">DRF Dashboard</h1>
      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
        {data.map((d) => (
          <Card key={d.name}><CardHeader className="pb-1"><CardTitle className="text-sm text-muted-foreground">{d.name}</CardTitle></CardHeader><CardContent><p className="text-3xl font-bold">{d.count}</p></CardContent></Card>
        ))}
      </div>
      <Card><CardHeader><CardTitle className="text-base">Counts</CardTitle></CardHeader><CardContent>
        <ResponsiveContainer width="100%" height={300}><BarChart data={data}><CartesianGrid strokeDasharray="3 3" /><XAxis dataKey="name" /><YAxis allowDecimals={false} /><Tooltip /><Bar dataKey="count" fill="var(--primary)" radius={[4, 4, 0, 0]} /></BarChart></ResponsiveContainer>
      </CardContent></Card>
    </div>
  );
}
