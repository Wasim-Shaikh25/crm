import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'sonner';
import { Pencil, Plus, Trash2 } from 'lucide-react';
import api from '@/lib/axios';
import DataTable from '@/components/DataTable';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Skeleton } from '@/components/ui/skeleton';

/**
 * Generic list screen: fetch `endpoint`, show `columns`, optional
 * create/edit navigation and delete support.
 * cfg: { title, endpoint, columns, rowKey, newPath, editPath?(row)->path,
 *        deletePath?(row)->string (DELETE), searchKeys, params? }
 */
export default function ListPage({ cfg }) {
  const [rows, setRows] = useState(null);
  const navigate = useNavigate();

  const load = () => {
    api.get(cfg.endpoint, { params: cfg.params }).then((r) => setRows(r.data?.content ?? r.data ?? [])).catch(() => setRows([]));
  };
  useEffect(load, [cfg.endpoint]);

  const del = async (row) => {
    if (!window.confirm('Delete this record?')) return;
    try {
      await api.delete(cfg.deletePath(row));
      toast.success('Deleted');
      load();
    } catch {}
  };

  return (
    <Card>
      <CardHeader className="flex flex-row items-center justify-between">
        <CardTitle>{cfg.title}</CardTitle>
        {cfg.newPath && <Button size="sm" onClick={() => navigate(cfg.newPath)}><Plus size={14} /> New</Button>}
      </CardHeader>
      <CardContent>
        {rows === null ? <Skeleton className="h-48 w-full" /> : (
          <DataTable
            columns={cfg.columns}
            rows={rows}
            searchKeys={cfg.searchKeys}
            actions={(cfg.editPath || cfg.deletePath) ? (row) => (
              <div className="flex justify-end gap-1">
                {cfg.editPath && <Button variant="ghost" size="icon" onClick={() => navigate(cfg.editPath(row))}><Pencil size={14} /></Button>}
                {cfg.deletePath && <Button variant="ghost" size="icon" onClick={() => del(row)}><Trash2 size={14} className="text-destructive" /></Button>}
              </div>
            ) : undefined}
          />
        )}
      </CardContent>
    </Card>
  );
}
