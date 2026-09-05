import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'sonner';
import api from '@/lib/axios';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

// First-login / token-less reset: POST /auth/resetPassword {empId, password}
export default function ResetPassword() {
  const [form, setForm] = useState({ empId: '', oldPassword: '', newPassword: '' });
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    if (!form.empId || !form.oldPassword || !form.newPassword) return toast.warning('All fields required');
    try {
      await api.post('/auth/resetPassword', form);
      toast.success('Password reset. Please sign in.');
      navigate('/login');
    } catch {}
  };

  return (
    <div className="flex min-h-screen items-center justify-center">
      <Card className="w-full max-w-sm">
        <CardHeader><CardTitle>Reset Password</CardTitle></CardHeader>
        <CardContent>
          <form onSubmit={submit} className="space-y-4">
            <div className="space-y-1.5"><Label>Employee ID</Label><Input value={form.empId} onChange={(e) => setForm({ ...form, empId: e.target.value })} /></div>
            <div className="space-y-1.5"><Label>Current Password</Label><Input type="password" value={form.oldPassword} onChange={(e) => setForm({ ...form, oldPassword: e.target.value })} /></div>
            <div className="space-y-1.5"><Label>New Password</Label><Input type="password" value={form.newPassword} onChange={(e) => setForm({ ...form, newPassword: e.target.value })} /></div>
            <Button type="submit" className="w-full">Reset</Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
