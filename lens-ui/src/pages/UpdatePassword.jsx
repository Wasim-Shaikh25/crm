import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'sonner';
import api from '@/lib/axios';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

// Authenticated self password change: POST /user/userResetPassword {empId, password}
export default function UpdatePassword() {
  const [form, setForm] = useState({ empId: '', password: '', confirm: '' });
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    if (!form.empId || !form.password) return toast.warning('All fields required');
    if (form.password !== form.confirm) return toast.warning('Passwords do not match');
    try {
      await api.post('/user/userResetPassword', { empId: form.empId, password: form.password });
      toast.success('Password updated');
      navigate('/dashboard');
    } catch {}
  };

  return (
    <Card className="max-w-md">
      <CardHeader><CardTitle>Update Password</CardTitle></CardHeader>
      <CardContent>
        <form onSubmit={submit} className="space-y-4">
          <div className="space-y-1.5"><Label htmlFor="upd-empid">Employee ID</Label><Input id="upd-empid" value={form.empId} onChange={(e) => setForm({ ...form, empId: e.target.value })} /></div>
          <div className="space-y-1.5"><Label>New Password</Label><Input type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} /></div>
          <div className="space-y-1.5"><Label>Confirm Password</Label><Input type="password" value={form.confirm} onChange={(e) => setForm({ ...form, confirm: e.target.value })} /></div>
          <Button type="submit">Update</Button>
        </form>
      </CardContent>
    </Card>
  );
}
