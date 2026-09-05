import React, { useEffect, useState } from 'react';
import { toast } from 'sonner';
import { Eye, Paperclip, Pencil, Search } from 'lucide-react';
import api from '@/lib/axios';
import { UPLOADS_ENABLED } from '@/lib/upload';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';

const DEFAULT_ACTIVITIES = ['API plan Design', 'R&D', 'Sales', 'Branch', 'Proceed', 'OA'];
const EMPTY = { id: null, ofmNo: '', currentActivity: '', comments: '', file: null };

export default function OfmCommunication() {
  const [rows, setRows] = useState([]);
  const [ofmOptions, setOfmOptions] = useState([]);
  const [activities, setActivities] = useState(DEFAULT_ACTIVITIES);
  const [form, setForm] = useState(EMPTY);
  const latest = rows[0];

  useEffect(() => {
    api.get('/lens/queryDrawingMasterTablebyColumn', { params: { columnName: 'OFM_ACTIVITY' } })
      .then((r) => r.data?.length && setActivities(r.data)).catch(() => {});
    api.get('/lens/OrderForwardingMemo/getAll')
      .then((r) => setOfmOptions((r.data || []).map((o) => o.ofmNo).filter(Boolean))).catch(() => {});
  }, []);

  const search = async () => {
    if (!form.ofmNo?.trim()) return toast.warning('Please enter an OFM No');
    try {
      const { data } = await api.get('/lens/ofmCommunication/getAll', { params: { ofmNo: form.ofmNo.trim() } });
      setRows(data || []);
      if (!data?.length) toast.info('No activity found for this OFM');
    } catch { setRows([]); }
  };

  const submit = async (e) => {
    e.preventDefault();
    if (!form.ofmNo?.trim()) return toast.warning('OFM No is required');
    if (!form.currentActivity) return toast.warning('Select a Current Activity');
    const fd = new FormData();
    fd.append('ofmNo', form.ofmNo.trim());
    fd.append('currentActivity', form.currentActivity);
    fd.append('comments', form.comments || '');
    if (form.id) fd.append('id', form.id);
    if (form.file) fd.append('file', form.file);
    try {
      const { data } = form.id
        ? await api.put('/lens/ofmCommunication/update', fd)
        : await api.post('/lens/ofmCommunication/save', fd);
      toast.success(typeof data === 'string' ? data : 'Saved');
      setForm({ ...EMPTY, ofmNo: form.ofmNo });
      await search();
    } catch {}
  };

  const download = async (fileName) => {
    try {
      const res = await api.get(`/lens/ofmCommunication/downloadFiles/${fileName}`, { responseType: 'blob' });
      const url = URL.createObjectURL(res.data);
      Object.assign(document.createElement('a'), { href: url, download: fileName }).click();
      URL.revokeObjectURL(url);
    } catch {}
  };

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">OFM Communication</h1>

      <div className="flex items-end gap-2 max-w-xl">
        <div className="flex-1 space-y-1.5">
          <Label>OFM No</Label>
          <Input list="ofm-list" placeholder="Search by OFM number" value={form.ofmNo}
                 onChange={(e) => setForm({ ...form, ofmNo: e.target.value })} />
          <datalist id="ofm-list">{ofmOptions.map((o) => <option key={o} value={o} />)}</datalist>
        </div>
        <Button onClick={search} disabled={!form.ofmNo?.trim()}><Search size={16} /> Search</Button>
      </div>

      <Card>
        <CardHeader><CardTitle className="text-base">Activity History</CardTitle></CardHeader>
        <CardContent>
          <Table>
            <TableHeader><TableRow>
              <TableHead>OFM</TableHead><TableHead>Activity On</TableHead><TableHead>By</TableHead>
              <TableHead>Previous</TableHead><TableHead>Current</TableHead><TableHead>Comments</TableHead>
              <TableHead>File</TableHead><TableHead></TableHead>
            </TableRow></TableHeader>
            <TableBody>
              {rows.map((r) => (
                <TableRow key={r.id}>
                  <TableCell>{r.ofmNo}</TableCell><TableCell>{r.activityOn}</TableCell>
                  <TableCell>{r.activityBy}</TableCell><TableCell>{r.previousActivity}</TableCell>
                  <TableCell>{r.currentActivity}</TableCell>
                  <TableCell className="max-w-[200px] truncate">{r.comments}</TableCell>
                  <TableCell>{r.fileName && <Button variant="ghost" size="icon" onClick={() => download(r.fileName)}><Eye size={16} /></Button>}</TableCell>
                  <TableCell><Button variant="ghost" size="icon" onClick={() => setForm({ id: r.id, ofmNo: r.ofmNo, currentActivity: r.currentActivity || '', comments: r.comments || '', file: null })}><Pencil size={16} /></Button></TableCell>
                </TableRow>
              ))}
              {!rows.length && <TableRow><TableCell colSpan={8} className="text-center text-muted-foreground">No data found</TableCell></TableRow>}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      <Card>
        <CardHeader><CardTitle className="text-base">{form.id ? 'Update Activity' : 'New Activity'}</CardTitle></CardHeader>
        <CardContent>
          <form onSubmit={submit} className="grid gap-4 max-w-2xl">
            {latest?.currentActivity && (
              <div className="space-y-1.5"><Label>Previous Activity</Label><Input value={latest.currentActivity} readOnly disabled /></div>
            )}
            <div className="space-y-1.5">
              <Label>Current Activity *</Label>
              <Select value={form.currentActivity} onValueChange={(v) => setForm({ ...form, currentActivity: v })}>
                <SelectTrigger><SelectValue placeholder="Select activity" /></SelectTrigger>
                <SelectContent>{activities.map((a) => <SelectItem key={a} value={a}>{a}</SelectItem>)}</SelectContent>
              </Select>
            </div>
            <div className="space-y-1.5">
              <Label>Comments</Label>
              <Textarea rows={2} maxLength={2000} value={form.comments} onChange={(e) => setForm({ ...form, comments: e.target.value })} />
            </div>
            {UPLOADS_ENABLED && (
              <div className="flex items-center gap-3">
                <Button type="button" variant="outline" asChild>
                  <label className="cursor-pointer flex items-center gap-2"><Paperclip size={16} /> Attach file (optional)
                    <input type="file" hidden onChange={(e) => setForm({ ...form, file: e.target.files[0] || null })} /></label>
                </Button>
                {form.file && <span className="text-sm text-muted-foreground">{form.file.name}</span>}
              </div>
            )}
            <div className="flex gap-2">
              <Button type="submit">{form.id ? 'Update' : 'Submit'}</Button>
              {form.id && <Button type="button" variant="outline" onClick={() => setForm({ ...EMPTY, ofmNo: form.ofmNo })}>Cancel</Button>}
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
