import React from 'react';
import { NavLink, Outlet, useNavigate } from 'react-router-dom';
import {
  LayoutDashboard, Users, Building2, PhoneCall, FileText, Briefcase,
  MessageSquare, LogOut, Search, UserPlus, KeyRound,
} from 'lucide-react';
import { useAuth } from '@/context/AuthContext';
import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';
import { Separator } from '@/components/ui/separator';

const NAV = [
  { section: 'Dashboards', items: [
    { to: '/dashboard', label: 'Overview', icon: LayoutDashboard },
    { to: '/drfDashboard', label: 'DRF', icon: FileText },
    { to: '/ofmDashboard', label: 'OFM Status', icon: Briefcase },
    { to: '/drfFilter', label: 'DRF Search', icon: Search },
  ]},
  { section: 'Masters', items: [
    { to: '/users', label: 'Users', icon: Users },
    { to: '/editCustomer', label: 'Customers', icon: Building2 },
  ]},
  { section: 'Sales', items: [
    { to: '/editSales', label: 'Sales Inquiries', icon: PhoneCall },
    { to: '/EditQuotation', label: 'Quotations', icon: FileText },
  ]},
  { section: 'DRF', items: [
    { to: '/editPump', label: 'Pump Seal', icon: FileText },
    { to: '/editRotary', label: 'Rotary Joint', icon: FileText },
    { to: '/editApi', label: 'API Plan', icon: FileText },
    { to: '/editAgitator', label: 'Agitator Seal', icon: FileText },
  ]},
  { section: 'OFM', items: [
    { to: '/editOfm', label: 'OFM List', icon: Briefcase },
    { to: '/ofmComm', label: 'Communication', icon: MessageSquare },
  ]},
  { section: 'Account', items: [
    { to: '/signup', label: 'Register User', icon: UserPlus },
    { to: '/updatePassword', label: 'Change Password', icon: KeyRound },
  ]},
];

export default function Layout() {
  const { logout, authState } = useAuth();
  const navigate = useNavigate();

  return (
    <div className="flex min-h-screen">
      <aside className="w-60 shrink-0 border-r bg-card p-4 flex flex-col">
        <div className="mb-6 px-2">
          <h1 className="text-lg font-bold text-primary">LENS CRM</h1>
          <p className="text-xs text-muted-foreground">Leak-Proof Engineering</p>
        </div>
        <nav className="flex-1 space-y-4">
          {NAV.map((group) => (
            <div key={group.section}>
              <p className="px-2 pb-1 text-[11px] font-semibold uppercase tracking-wide text-muted-foreground">
                {group.section}
              </p>
              {group.items.map(({ to, label, icon: Icon }) => (
                <NavLink
                  key={to}
                  to={to}
                  className={({ isActive }) =>
                    cn(
                      'flex items-center gap-2 rounded-md px-2 py-1.5 text-sm hover:bg-accent',
                      isActive && 'bg-primary text-primary-foreground hover:bg-primary',
                    )
                  }
                >
                  <Icon size={16} /> {label}
                </NavLink>
              ))}
            </div>
          ))}
        </nav>
        <Separator className="my-3" />
        <div className="px-2 pb-2 text-xs text-muted-foreground truncate">
          {authState?.sub || ''}
        </div>
        <Button variant="ghost" className="justify-start gap-2" onClick={() => { logout(); navigate('/login'); }}>
          <LogOut size={16} /> Logout
        </Button>
      </aside>
      <main className="flex-1 overflow-auto p-6">
        <Outlet />
      </main>
    </div>
  );
}
