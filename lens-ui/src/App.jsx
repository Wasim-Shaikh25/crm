import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import RequireAuth from '@/components/RequireAuth';
import Layout from '@/components/Layout';
import Login from '@/pages/Login';
import ResetPassword from '@/pages/ResetPassword';
import UpdatePassword from '@/pages/UpdatePassword';
import Dashboard from '@/pages/Dashboard';
import DrfDashboard from '@/pages/DrfDashboard';
import OfmDashboard from '@/pages/OfmDashboard';
import OfmCommunication from '@/pages/OfmCommunication';
import DrfFilter from '@/pages/DrfFilter';
import {
  CustomersList, UsersList, SalesInquiriesList, QuotationsList, OfmsList,
  PumpSealsList, RotaryJointsList, ApiPlansList, AgitatorSealsList,
} from '@/pages/Lists';
import {
  PumpSealForm, AgitatorForm, ApiPlanForm, RotaryForm,
  SalesInquiryForm, CustomerForm, QuotationForm, OfmForm, UserForm,
} from '@/pages/Forms';

const P = (el) => <RequireAuth>{el}</RequireAuth>;

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/reset" element={<ResetPassword />} />
      <Route element={P(<Layout />)}>
        <Route path="/" element={<Navigate to="/dashboard" replace />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/drfDashboard" element={<DrfDashboard />} />
        <Route path="/ofmDashboard" element={<OfmDashboard />} />
        <Route path="/ofmComm" element={<OfmCommunication />} />
        <Route path="/drfFilter" element={<DrfFilter />} />

        {/* lists */}
        <Route path="/editCustomer" element={<CustomersList />} />
        <Route path="/users" element={<UsersList />} />
        <Route path="/editSales" element={<SalesInquiriesList />} />
        <Route path="/EditQuotation" element={<QuotationsList />} />
        <Route path="/editOfm" element={<OfmsList />} />
        <Route path="/editPump" element={<PumpSealsList />} />
        <Route path="/editRotary" element={<RotaryJointsList />} />
        <Route path="/editApi" element={<ApiPlansList />} />
        <Route path="/editAgitator" element={<AgitatorSealsList />} />
        <Route path="/editDrf" element={<DrfFilter />} />

        {/* forms */}
        <Route path="/Customer" element={<CustomerForm />} />
        <Route path="/Customer/:rId" element={<CustomerForm />} />
        <Route path="/CreateUser" element={<UserForm />} />
        <Route path="/CreateUser/:uId" element={<UserForm />} />
        <Route path="/signup" element={<UserForm />} />
        <Route path="/SalesInquiry" element={<SalesInquiryForm />} />
        <Route path="/SalesInquiry/:sId" element={<SalesInquiryForm />} />
        <Route path="/quotation" element={<QuotationForm />} />
        <Route path="/quotation/:qId" element={<QuotationForm />} />
        <Route path="/createOfm" element={<OfmForm />} />
        <Route path="/createOfm/:oId" element={<OfmForm />} />
        <Route path="/createPump" element={<PumpSealForm />} />
        <Route path="/createPump/:pId" element={<PumpSealForm />} />
        <Route path="/createRotary" element={<RotaryForm />} />
        <Route path="/createRotary/:rjId" element={<RotaryForm />} />
        <Route path="/createApi" element={<ApiPlanForm />} />
        <Route path="/createApi/:apId" element={<ApiPlanForm />} />
        <Route path="/createAgitator" element={<AgitatorForm />} />
        <Route path="/createAgitator/:aId" element={<AgitatorForm />} />
        <Route path="/updatePassword" element={<UpdatePassword />} />

        <Route path="*" element={<Navigate to="/dashboard" replace />} />
      </Route>
    </Routes>
  );
}
