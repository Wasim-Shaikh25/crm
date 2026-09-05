import React, { useMemo, useState } from 'react';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Input } from '@/components/ui/input';
import { fmt } from '@/lib/format';

/**
 * Generic searchable table.
 * columns: [{ key, label, render?(row), date?:bool }]
 */
export default function DataTable({ columns, rows = [], searchKeys, actions, empty = 'No data found' }) {
  const [q, setQ] = useState('');
  const filtered = useMemo(() => {
    if (!q.trim()) return rows;
    const needle = q.toLowerCase();
    const keys = searchKeys || columns.map((c) => c.key);
    return rows.filter((r) => keys.some((k) => fmt(r[k]).toLowerCase().includes(needle)));
  }, [q, rows, columns, searchKeys]);

  return (
    <div className="space-y-3">
      <Input className="max-w-sm" placeholder="Search…" value={q} onChange={(e) => setQ(e.target.value)} />
      <Table>
        <TableHeader>
          <TableRow>
            {columns.map((c) => <TableHead key={c.key}>{c.label}</TableHead>)}
            {actions && <TableHead className="w-24" />}
          </TableRow>
        </TableHeader>
        <TableBody>
          {filtered.map((row, i) => (
            <TableRow key={row.id ?? row[`${Object.keys(row)[0]}`] ?? i}>
              {columns.map((c) => <TableCell key={c.key}>{c.render ? c.render(row) : fmt(row[c.key])}</TableCell>)}
              {actions && <TableCell className="text-right">{actions(row)}</TableCell>}
            </TableRow>
          ))}
          {!filtered.length && (
            <TableRow><TableCell colSpan={columns.length + (actions ? 1 : 0)} className="py-8 text-center text-muted-foreground">{empty}</TableCell></TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  );
}
