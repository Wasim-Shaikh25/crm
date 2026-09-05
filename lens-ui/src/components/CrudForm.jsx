import React, { useState } from 'react';
import { toast } from 'sonner';
import { Plus, Trash2 } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import FormField from './FormField';
import { labelize } from '@/lib/format';

/**
 * Config-driven form.
 * sections: [{ title, fields: [fieldConfig] }]
 * items:    [{ key, columns: [fieldConfig], addLabel? }] — editable child lists
 * onSubmit(formData) is called with the full state object.
 */
const getPath = (obj, path) => path.split('.').reduce((o, k) => o?.[k], obj);
const setPath = (obj, path, v) => {
  const keys = path.split('.');
  const out = { ...obj };
  let cur = out;
  for (let i = 0; i < keys.length - 1; i++) {
    cur[keys[i]] = { ...(cur[keys[i]] || {}) };
    cur = cur[keys[i]];
  }
  cur[keys[keys.length - 1]] = v;
  return out;
};

export default function CrudForm({ title, sections, items = [], initial, values, onChange, onSubmit, submitLabel = 'Save', extra }) {
  const data = values;
  const set = (name, v) => onChange(setPath(data, name, v));
  const setItem = (key, idx, name, v) => {
    const list = [...(data[key] || [])];
    list[idx] = setPath(list[idx] || {}, name, v);
    onChange({ ...data, [key]: list });
  };
  const addItem = (key, cols) => {
    const row = Object.fromEntries(cols.map((c) => [c.name, '']));
    onChange({ ...data, [key]: [...(data[key] || []), row] });
  };
  const removeItem = (key, idx) => {
    const list = [...(data[key] || [])];
    list.splice(idx, 1);
    onChange({ ...data, [key]: list });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const missing = [];
    for (const s of sections) for (const f of s.fields) if (f.required && !getPath(data, f.name)) missing.push(f.label || labelize(f.name));
    if (missing.length) return toast.warning(`Required: ${missing.join(', ')}`);
    onSubmit(data);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <h1 className="text-2xl font-bold">{title}</h1>
      {sections.map((s) => (
        <Card key={s.title}>
          <CardHeader><CardTitle className="text-base">{s.title}</CardTitle></CardHeader>
          <CardContent className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
            {s.fields.map((f) => (
              <FormField key={f.name} field={f} value={getPath(data, f.name)} onChange={set} />
            ))}
          </CardContent>
        </Card>
      ))}

      {items.map(({ key, title: t, columns, addLabel }) => (
        <Card key={key}>
          <CardHeader className="flex flex-row items-center justify-between">
            <CardTitle className="text-base">{t || labelize(key)}</CardTitle>
            <Button type="button" size="sm" variant="outline" onClick={() => addItem(key, columns)}>
              <Plus size={14} /> {addLabel || 'Add'}
            </Button>
          </CardHeader>
          <CardContent className="overflow-x-auto">
            <Table>
              <TableHeader><TableRow>{columns.map((c) => <TableHead key={c.name}>{c.label || labelize(c.name)}</TableHead>)}<TableHead className="w-10" /></TableRow></TableHeader>
              <TableBody>
                {(data[key] || []).map((row, idx) => (
                  <TableRow key={idx}>
                    {columns.map((c) => (
                      <TableCell key={c.name} className="min-w-[120px]">
                        <FormField field={{ ...c, label: '' }} value={getPath(row, c.name)} onChange={(n, v) => setItem(key, idx, n, v)} />
                      </TableCell>
                    ))}
                    <TableCell><Button type="button" variant="ghost" size="icon" onClick={() => removeItem(key, idx)}><Trash2 size={14} /></Button></TableCell>
                  </TableRow>
                ))}
                {!(data[key] || []).length && <TableRow><TableCell colSpan={columns.length + 1} className="text-center text-muted-foreground">No items</TableCell></TableRow>}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      ))}

      {extra}
      <Button type="submit">{submitLabel}</Button>
    </form>
  );
}
