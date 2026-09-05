import React from 'react';
import { Label } from '@/components/ui/label';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { Checkbox } from '@/components/ui/checkbox';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { useMasterOptions, useApiOptions } from '@/lib/useMasterOptions';
import { labelize } from '@/lib/format';
import { uploadFile, downloadFile } from '@/lib/upload';
import { toast } from 'sonner';
import { Eye, Paperclip } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { useState } from 'react';

function FileInput({ field, value, onChange }) {
  const [busy, setBusy] = useState(false);
  const pick = async (e) => {
    const file = e.target.files?.[0];
    if (!file) return;
    if (file.size > 20 * 1024 * 1024) return toast.error('File too large (max 20 MB)');
    setBusy(true);
    try {
      const stored = await uploadFile(file, field.filetype || 'misc');
      onChange(stored);
      toast.success('Uploaded');
    } catch {} finally { setBusy(false); }
  };
  return (
    <div className="flex items-center gap-2">
      <Button type="button" variant="outline" size="sm" asChild disabled={busy}>
        <label className="cursor-pointer flex items-center gap-1.5">
          <Paperclip size={14} /> {busy ? 'Uploading…' : value ? 'Replace' : 'Upload'}
          <input type="file" hidden onChange={pick} />
        </label>
      </Button>
      {value && (
        <button type="button" className="flex items-center gap-1 text-xs text-primary hover:underline" onClick={() => downloadFile(value)}>
          <Eye size={12} /> {String(value).slice(0, 40)}
        </button>
      )}
    </div>
  );
}

/**
 * One field driven by config:
 * { name, label?, type: text|number|textarea|select|date|datetime|checkbox|master, options?, column?, placeholder?, required?, readOnly?, colSpan? }
 * `master` type fetches options from MasterTableForDrawing column `column`.
 */
export default function FormField({ field, value, onChange }) {
  const masterOpts = useMasterOptions(field.type === 'master' ? field.column || field.name : '', field.options || []);
  const apiOpts = useApiOptions(field.type === 'api' ? field.url : null, field.pick || ((r) => r), field.options || []);
  const label = field.label || labelize(field.name);
  const set = (v) => onChange(field.name, v);

  if (field.type === 'file') {
    return (
      <div className="space-y-1.5">
        <Label htmlFor={field.name}>{label}</Label>
        <FileInput field={field} value={value} onChange={set} />
      </div>
    );
  }

  if (field.type === 'checkbox') {
    return (
      <div className="flex items-center gap-2 pt-6">
        <Checkbox id={field.name} checked={!!value} onCheckedChange={set} />
        <Label htmlFor={field.name}>{label}</Label>
      </div>
    );
  }

  let input;
  if (field.type === 'textarea') {
    input = <Textarea id={field.name} rows={field.rows || 2} value={value ?? ''} placeholder={field.placeholder} maxLength={field.maxLength} onChange={(e) => set(e.target.value)} />;
  } else if (field.type === 'select' || field.type === 'master' || field.type === 'api') {
    const opts = field.type === 'master' ? masterOpts : field.type === 'api' ? apiOpts : field.options || [];
    input = (
      <Select value={value || ''} onValueChange={set}>
        <SelectTrigger id={field.name}><SelectValue placeholder={field.placeholder || `Select ${label}`} /></SelectTrigger>
        <SelectContent>
          {opts.map((o) => <SelectItem key={o} value={o}>{o}</SelectItem>)}
        </SelectContent>
      </Select>
    );
  } else if (field.type === 'date' || field.type === 'datetime') {
    input = <Input id={field.name} type={field.type === 'date' ? 'date' : 'datetime-local'} value={value ?? ''} onChange={(e) => set(e.target.value)} />;
  } else {
    input = <Input id={field.name} type={field.type === 'number' ? 'number' : 'text'} value={value ?? ''} placeholder={field.placeholder} readOnly={field.readOnly} onChange={(e) => set(field.type === 'number' ? (e.target.value === '' ? '' : Number(e.target.value)) : e.target.value)} />;
  }

  return (
    <div className={field.colSpan ? `space-y-1.5 col-span-${field.colSpan}` : 'space-y-1.5'}>
      <Label htmlFor={field.name}>{label}{field.required ? ' *' : ''}</Label>
      {input}
    </div>
  );
}
