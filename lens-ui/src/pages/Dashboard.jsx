import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '@/lib/axios';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Skeleton } from '@/components/ui/skeleton';
import { Bar, BarChart, CartesianGrid, Cell, Pie, PieChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';

const Stat = ({ title, value, onClick }) => (
  <Card className={onClick ? 'cursor-pointer hover:shadow-md transition-shadow' : ''} onClick={onClick}>
    <CardHeader className="pb-1"><CardTitle className="text-sm text-muted-foreground">{title}</CardTitle></CardHeader>
    <CardContent><p className="text-3xl font-bold">{value}</p></CardContent>
  </Card>
);

export default function Dashboard() {
  const [s, setS] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    api.get('/lens/dashboard/summary').then((r) => setS(r.data)).catch(() => {});
  }, []);

  if (!s) return <div className="grid gap-4 md:grid-cols-3">{[...Array(6)].map((_, i) => <Skeleton key={i} className="h-28" />)}</div>;

  const drf = Object.entries(s.drfCountByType || {}).map(([name, count]) => ({ name, count }));
  const ofm = Object.entries(s.ofmCountByStatus || {}).map(([name, value]) => ({ name, value }));

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Dashboard</h1>
      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-6">
        <Stat title="Customers" value={s.customerCount} onClick={() => navigate('/editCustomer')} />
        <Stat title="Sales Inquiries" value={s.salesInquiryCount} onClick={() => navigate('/editSales')} />
        <Stat title="Quotations" value={s.quotationCount} onClick={() => navigate('/EditQuotation')} />
        <Stat title="OFMs" value={s.ofmCount} onClick={() => navigate('/ofmDashboard')} />
        <Stat title="Users" value={s.userCount} onClick={() => navigate('/users')} />
        <Stat title="Total DRFs" value={s.pumpSealCount + s.rotaryJointCount + s.apiPlanCount + s.agitatorSealCount} onClick={() => navigate('/drfDashboard')} />
      </div>

      <div className="grid gap-4 lg:grid-cols-2">
        <Card>
          <CardHeader><CardTitle className="text-base">DRFs by Type</CardTitle></CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={260}>
              <BarChart data={drf}><CartesianGrid strokeDasharray="3 3" /><XAxis dataKey="name" fontSize={12} /><YAxis allowDecimals={false} /><Tooltip /><Bar dataKey="count" fill="var(--primary)" radius={[4, 4, 0, 0]} /></BarChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
        <Card>
          <CardHeader><CardTitle className="text-base">OFM by Status</CardTitle></CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={260}>
              <PieChart><Pie data={ofm} dataKey="value" nameKey="name" outerRadius={90} label>{ofm.map((_, i) => <Cell key={i} fill={`hsl(${190 + i * 30} 70% 45%)`} />)}</Pie><Tooltip /></PieChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader><CardTitle className="text-base">Recent OFM Activity</CardTitle></CardHeader>
        <CardContent>
          <Table>
            <TableHeader><TableRow><TableHead>OFM No</TableHead><TableHead>Activity On</TableHead><TableHead>By</TableHead><TableHead>Activity</TableHead><TableHead>Comments</TableHead></TableRow></TableHeader>
            <TableBody>
              {(s.recentActivities || []).map((a) => (
                <TableRow key={a.id}><TableCell>{a.ofmNo}</TableCell><TableCell>{a.activityOn}</TableCell><TableCell>{a.activityBy}</TableCell><TableCell>{a.currentActivity}</TableCell><TableCell className="max-w-[240px] truncate">{a.comments}</TableCell></TableRow>
              ))}
              {!s.recentActivities?.length && <TableRow><TableCell colSpan={5} className="text-center text-muted-foreground">No recent activity</TableCell></TableRow>}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  );
}
