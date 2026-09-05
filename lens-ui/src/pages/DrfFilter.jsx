import React, { useState } from 'react';
import { Search } from 'lucide-react';
import api from '@/lib/axios';
import DataTable from '@/components/DataTable';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { fmt } from '@/lib/format';
import { useApiOptions } from '@/lib/useMasterOptions';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';

const COLUMNS = [
  { key: 'drfNumber', label: 'DRF No' },
  { key: 'drfType', label: 'Type', render: (r) => fmt(r.drfType ?? r.type) },
  { key: 'customerName', label: 'Customer' },
  { key: 'branch', label: 'Branch' },
];

export default function DrfFilter() {
  const [filters, setFilters] = useState({ branch: '', customerName: '', drfNumber: '' });
  const [rows, setRows] = useState(null);
  const branches = useApiOptions('/user/getAllBranches', (b) => b.branchName || b.name);

  const search = async () => {
    const { data } = await api.get('/lens/filter', {
      params: { ...filters, pageNo: 0, pageSize: 100 },
    });
    setRows(data || []);
  };

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">DRF Search</h1>
      <Card>
        <CardHeader><CardTitle className="text-base">Filters</CardTitle></CardHeader>
        <CardContent className="flex flex-wrap items-end gap-3">
          <div className="space-y-1.5">
            <Label>Branch</Label>
            <Select value={filters.branch} onValueChange={(v) => setFilters({ ...filters, branch: v })}>
              <SelectTrigger className="w-48"><SelectValue placeholder="All branches" /></SelectTrigger>
              <SelectContent>{branches.map((b) => <SelectItem key={b} value={b}>{b}</SelectItem>)}</SelectContent>
            </Select>
          </div>
          <div className="space-y-1.5"><Label>Customer</Label><Input className="w-48" value={filters.customerName} onChange={(e) => setFilters({ ...filters, customerName: e.target.value })} /></div>
          <div className="space-y-1.5"><Label>DRF Number</Label><Input className="w-48" value={filters.drfNumber} onChange={(e) => setFilters({ ...filters, drfNumber: e.target.value })} /></div>
          <Button onClick={search}><Search size={16} /> Search</Button>
        </CardContent>
      </Card>
      {rows !== null && <Card><CardContent className="pt-4"><DataTable columns={COLUMNS} rows={rows} /></CardContent></Card>}
    </div>
  );
}
