import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { toast } from 'sonner';
import api from '@/lib/axios';
import EntityForm from '@/components/EntityForm';
import { FORM_CONFIGS } from '@/lib/formConfigs';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

// Shared "Fetch from Sales Inquiry Item" panel for all DRF forms.
// GET /lens/salesInquiry/get?itemReferenceNo=<ref> returns the inquiry item;
// its <type>Inquiry payload merges into the form's <type>InquiryItem section.
const DrfFetch = ({ form, setForm, itemKey, respKey }) => {
  const fetchItem = async () => {
    const ref = (form.salesInquiryItemReferenceNo || '').trim();
    if (!ref) return toast.warning('Enter a Sales Inquiry Item Ref No first');
    try {
      const { data } = await api.get('/lens/salesInquiry/get', { params: { itemReferenceNo: ref } });
      const si = data?.salesInquiry || {};
      setForm((f) => ({
        ...f,
        customerName: si.customerName || f.customerName,
        branch: f.branch || si.branch,
        endUser: si.endUser || f.endUser,
        [itemKey]: { ...(data[respKey] || {}) },
      }));
      toast.success('Inquiry item loaded');
    } catch {
      toast.error('Failed to load inquiry item');
    }
  };
  return (
    <Card><CardContent className="flex items-end gap-2 pt-4">
      <div className="flex-1 space-y-1.5">
        <Label>Fetch from Sales Inquiry Item</Label>
        <Input value={form.salesInquiryItemReferenceNo || ''} placeholder="Sales inquiry item ref no"
               onChange={(e) => setForm({ ...form, salesInquiryItemReferenceNo: e.target.value })} />
      </div>
      <Button type="button" variant="outline" onClick={fetchItem}>Fetch</Button>
    </CardContent></Card>
  );
};

export const PumpSealForm = () => (
  <EntityForm cfg={FORM_CONFIGS.pumpSeal} idParam="pId" updatePath="/lens/pumpSeal/Update"
    extra={({ form, setForm }) => <DrfFetch form={form} setForm={setForm} itemKey="pumpInquiryItem" respKey="pumpInquiry" />} />
);
export const AgitatorForm = () => (
  <EntityForm cfg={FORM_CONFIGS.agitatorSeal} idParam="aId"
    extra={({ form, setForm }) => <DrfFetch form={form} setForm={setForm} itemKey="agitatorInquiryItem" respKey="agitatorInquiry" />} />
);
export const ApiPlanForm = () => (
  <EntityForm cfg={FORM_CONFIGS.apiPlan} idParam="apId"
    extra={({ form, setForm }) => <DrfFetch form={form} setForm={setForm} itemKey="apiPlanInquiryItem" respKey="apiPlanInquiry" />} />
);
export const RotaryForm = () => (
  <EntityForm cfg={FORM_CONFIGS.rotaryJoint} idParam="rjId"
    extra={({ form, setForm }) => <DrfFetch form={form} setForm={setForm} itemKey="rotaryJointInquiryItem" respKey="rotaryJointInquiry" />} />
);
export const SalesInquiryForm = () => (
  <EntityForm cfg={FORM_CONFIGS.salesInquiry} idParam="sId" updatePath="/lens/salesInquiry/Update" />
);
export const CustomerForm = () => (
  <EntityForm cfg={FORM_CONFIGS.customer} idParam="rId" updatePath="/lens/customer/Update" />
);

// Quotation with sales-inquiry fetch
export function QuotationForm() {
  const fetchInquiry = async (form, setForm) => {
    const no = form.salesInquiryNumber?.trim();
    if (!no) return toast.warning('Enter a Sales Inquiry Number first');
    try {
      const { data } = await api.get('/lens/salesInquiry/get', { params: { itemReferenceNo: no } });
      if (!data) return;
      setForm((f) => ({
        ...f,
        customer: data.customerName || f.customer,
        customerAddress: data.customerAddress || f.customerAddress,
        branch: data.branch || f.branch,
        enquiryNo: data.salesInquiryReferenceNo || f.enquiryNo,
        kindAttentionTo: data.contactPerson || f.kindAttentionTo,
        customerEnquiryNo: data.salesInquiryReferenceNo || f.customerEnquiryNo,
      }));
      toast.success('Inquiry data loaded');
    } catch {}
  };

  return (
    <EntityForm
      cfg={FORM_CONFIGS.quotation}
      idParam="qId"
      extra={({ form, setForm }) => (
        <Card><CardContent className="flex items-end gap-2 pt-4">
          <div className="flex-1 space-y-1.5">
            <Label>Fetch from Sales Inquiry</Label>
            <Input value={form.salesInquiryNumber || ''} placeholder="Sales inquiry item ref no"
                   onChange={(e) => setForm({ ...form, salesInquiryNumber: e.target.value })} />
          </div>
          <Button type="button" variant="outline" onClick={() => fetchInquiry(form, setForm)}>Fetch</Button>
        </CardContent></Card>
      )}
    />
  );
}

// OFM with quotation fetch + customer address lookup
export function OfmForm() {
  const { oId } = useParams();
  const [addresses, setAddresses] = React.useState([]);
  const fetchQuotation = async (form, setForm) => {
    const no = form.qutationNumber?.trim();
    if (!no) return toast.warning('Enter a quotation number first');
    try {
      const { data } = await api.get('/lens/Quotation/getByNo', { params: { quotationNo: no } });
      if (!data) return;
      setForm((f) => ({
        ...f,
        quotationNo: data.quotationNo || f.quotationNo,
        customer: data.customer || f.customer,
        customerAddress: data.customerAddress || f.customerAddress,
        branch: data.branch || f.branch,
        engineer: data.engineer || f.engineer,
        kindAttentionTo: data.kindAttentionTo || f.kindAttentionTo,
        category: data.category || f.category,
        paymentTerms: data.paymentTerms || f.paymentTerms,
        ofmItems: (data.items?.length ? data.items : []).map((item, i) => ({
          srNo: i + 1, header: item.itemName || '', factor: '', face: '', type: '', size: '',
          description: item.itemDescription || '', ciCode: '', lpItemCode: item.itemCode || '',
          drfNo: item.drfNo || '', drawingNo: '', quantity: item.quantity || '', bookedQuantity: '',
          unit: item.uom || '', unitPrice: item.unitPrice || '', unitLPrice: '',
          discount: item.discount || '', naDrgNo: true,
          totalValue: item.totalPrice || '', totalListValue: '',
        })),
      }));
      toast.success('Quotation data loaded');
    } catch {}
  };

  // Customer address lookup: find the customer's ref by name, fetch its
  // addresses, and offer a dropdown when more than one exists.
  const loadAddresses = async (form, setForm) => {
    const name = form.customer?.trim();
    if (!name) return toast.warning('Enter a customer name first');
    try {
      const { data: customers } = await api.get('/lens/customer/getAll', { params: { pageNo: 0, pageSize: 500 } });
      const list = customers?.content ?? customers ?? [];
      const match = list.find((c) => c.customerName?.trim().toLowerCase() === name.toLowerCase());
      if (!match) return toast.warning('No customer record matches that name');
      const { data: addrs } = await api.get('/lens/customer/addresses', {
        params: { customerRefrenceNumber: match.customerReferenceNumber },
      });
      const options = (addrs || []).filter(Boolean);
      setAddresses(options);
      if (options.length === 1) setForm((f) => ({ ...f, customerAddress: options[0] }));
      if (!options.length) toast.warning('No addresses on record for this customer');
      else toast.success(`${options.length} address(es) found`);
    } catch {}
  };

  return (
    <EntityForm
      cfg={FORM_CONFIGS.ofm}
      idParam="oId"
      extra={({ form, setForm }) => (
        <Card><CardContent className="flex flex-wrap items-end gap-4 pt-4">
          <div className="flex flex-1 items-end gap-2 min-w-64">
            <div className="flex-1 space-y-1.5">
              <Label>Fetch from Quotation</Label>
              <Input value={form.qutationNumber || ''} placeholder="Quotation number"
                     onChange={(e) => setForm({ ...form, qutationNumber: e.target.value })} />
            </div>
            <Button type="button" variant="outline" onClick={() => fetchQuotation(form, setForm)}>Fetch</Button>
          </div>
          <div className="flex flex-1 items-end gap-2 min-w-64">
            <div className="flex-1 space-y-1.5">
              <Label>Customer Addresses</Label>
              {addresses.length > 1 ? (
                <Select value={form.customerAddress || ''} onValueChange={(v) => setForm({ ...form, customerAddress: v })}>
                  <SelectTrigger><SelectValue placeholder="Select address" /></SelectTrigger>
                  <SelectContent>{addresses.map((a, i) => <SelectItem key={i} value={a}>{a}</SelectItem>)}</SelectContent>
                </Select>
              ) : (
                <Input value={form.customerAddress || ''} placeholder="Load addresses for the customer above"
                       onChange={(e) => setForm({ ...form, customerAddress: e.target.value })} />
              )}
            </div>
            <Button type="button" variant="outline" onClick={() => loadAddresses(form, setForm)}>Load</Button>
          </div>
        </CardContent></Card>
      )}
    />
  );
}

// User create/update — different endpoint style
export function UserForm() {
  const { uId } = useParams();
  const navigate = useNavigate();
  const [form, setForm] = React.useState({ resetPasswordRequired: true });
  const [deps, setDeps] = React.useState({ departments: [], branches: [], designations: [] });

  React.useEffect(() => {
    Promise.allSettled([
      api.get('/user/getAllDepartments'), api.get('/user/getAllBranches'), api.get('/user/allDesignations'),
    ]).then(([d, b, g]) => setDeps({
      departments: d.status === 'fulfilled' ? d.value.data : [],
      branches: b.status === 'fulfilled' ? b.value.data : [],
      designations: g.status === 'fulfilled' ? g.value.data : [],
    }));
    if (uId) api.get('/user/getUser', { params: { empId: uId } }).then((r) => setForm(r.data || {})).catch(() => {});
  }, [uId]);

  const submit = async (e) => {
    e.preventDefault();
    try {
      const body = {
        ...form,
        departments: form.departmentName ? [{ departmentName: form.departmentName }] : [],
        branches: form.branchName ? [{ branchName: form.branchName }] : [],
        designation: form.designation ? { designationName: form.designation } : null,
      };
      if (uId) await api.put('/user/updateUser', body); else await api.post('/user/createUser', body);
      toast.success(`User ${uId ? 'updated' : 'created'}`);
      navigate('/users');
    } catch {}
  };

  return (
    <Card className="max-w-2xl">
      <CardHeader><CardTitle>{uId ? 'Edit' : 'Create'} User</CardTitle></CardHeader>
      <CardContent>
        <form onSubmit={submit} className="grid gap-4 sm:grid-cols-2">
          <Field label="First Name *" v={form.firstName} set={(v) => setForm({ ...form, firstName: v })} />
          <Field label="Middle Name" v={form.middleName} set={(v) => setForm({ ...form, middleName: v })} />
          <Field label="Last Name" v={form.lastName} set={(v) => setForm({ ...form, lastName: v })} />
          <Field label="Employee ID *" v={form.empId} set={(v) => setForm({ ...form, empId: v })} />
          {!uId && <Field label="Password *" type="password" v={form.password} set={(v) => setForm({ ...form, password: v })} />}
          <SelectField label="Designation" v={form.designation} set={(v) => setForm({ ...form, designation: v })}
                       options={deps.designations.map((d) => d.designationName || d.name || d).filter(Boolean)} />
          <SelectField label="Department" v={form.departmentName} set={(v) => setForm({ ...form, departmentName: v })}
                       options={deps.departments.map((d) => d.departmentName || d.name || d).filter(Boolean)} />
          <SelectField label="Branch" v={form.branchName} set={(v) => setForm({ ...form, branchName: v })}
                       options={deps.branches.map((b) => b.branchName || b.name || b).filter(Boolean)} />
          <div className="sm:col-span-2"><Button type="submit">{uId ? 'Update' : 'Create'}</Button></div>
        </form>
      </CardContent>
    </Card>
  );
}

const fieldId = (label) => 'f-' + label.replace(/[^a-zA-Z]/g, '').toLowerCase();
const Field = ({ label, v, set, type = 'text' }) => (
  <div className="space-y-1.5"><Label htmlFor={fieldId(label)}>{label}</Label><Input id={fieldId(label)} type={type} value={v || ''} onChange={(e) => set(e.target.value)} /></div>
);
const SelectField = ({ label, v, set, options }) => (
  <div className="space-y-1.5"><Label>{label}</Label>
    <Select value={v || ''} onValueChange={set}>
      <SelectTrigger><SelectValue placeholder={`Select ${label}`} /></SelectTrigger>
      <SelectContent>{options.map((o) => <SelectItem key={o} value={o}>{o}</SelectItem>)}</SelectContent>
    </Select>
  </div>
);
