import React from 'react';
import ListPage from '@/components/ListPage';
import { fmt, fmtDate } from '@/lib/format';

const C = (key, label, opts = {}) => ({ key, label, ...opts });

export const CustomersList = () => (
  <ListPage cfg={{
    title: 'Customers',
    endpoint: '/lens/customer/getAll', params: { pageNo: 0, pageSize: 500 },
    columns: [
      C('customerReferenceNumber', 'Ref No'),
      C('customerName', 'Customer'),
      C('branch', 'Branch'),
      C('vendorCode', 'Vendor Code'),
      C('createdOn', 'Created', { render: (r) => fmtDate(r.createdOn) }),
    ],
    searchKeys: ['customerName', 'customerReferenceNumber', 'branch'],
    newPath: '/Customer',
    editPath: (r) => `/Customer/${r.customerReferenceNumber}`,
  }} />
);

export const UsersList = () => (
  <ListPage cfg={{
    title: 'Users',
    endpoint: '/user/getAllUser', params: { pageNo: 0, pageSize: 500 },
    columns: [
      C('empId', 'Emp ID'),
      { key: 'name', label: 'Name', render: (r) => [r.firstName, r.middleName, r.lastName].filter(Boolean).join(' ') },
      { key: 'designation', label: 'Designation', render: (r) => fmt(r.designation?.designationName ?? r.designation) },
      { key: 'roles', label: 'Roles', render: (r) => (r.roles || []).map((x) => x.roleName || x).join(', ') || '-' },
      C('createdOn', 'Created', { render: (r) => fmtDate(r.createdOn) }),
    ],
    searchKeys: ['empId', 'firstName', 'lastName'],
    newPath: '/CreateUser',
    editPath: (r) => `/CreateUser/${r.empId}`,
    deletePath: (r) => `/user/deleteUser?empId=${encodeURIComponent(r.empId)}`,
  }} />
);

export const SalesInquiriesList = () => (
  <ListPage cfg={{
    title: 'Sales Inquiries',
    endpoint: '/lens/salesInquiry/getAllSalesInquiryByFilter',
    params: { pageNo: 0, pageSize: 500 },
    columns: [
      C('salesInquiryReferenceNo', 'Inquiry No'),
      C('customerName', 'Customer'),
      C('industry', 'Industry'),
      C('branch', 'Branch'),
      C('createdOn', 'Created', { render: (r) => fmtDate(r.createdOn) }),
    ],
    searchKeys: ['salesInquiryReferenceNo', 'customerName'],
    newPath: '/SalesInquiry',
    editPath: (r) => `/SalesInquiry/${r.salesInquiryReferenceNo || r.salesInquiryItemReferenceNo}`,
    deletePath: (r) => `/lens/salesInquiry/delete?salesInquiryReferenceNo=${encodeURIComponent(r.salesInquiryReferenceNo)}`,
  }} />
);

export const QuotationsList = () => (
  <ListPage cfg={{
    title: 'Quotations',
    endpoint: '/lens/Quotation/getAll', params: { pageNo: 0, pageSize: 500 },
    columns: [
      C('quotationNo', 'Quotation No'),
      C('customer', 'Customer'),
      C('branch', 'Branch'),
      C('category', 'Category'),
      C('grandTotal', 'Total'),
      C('quotationDate', 'Date', { render: (r) => fmtDate(r.quotationDate) }),
    ],
    searchKeys: ['quotationNo', 'customer'],
    newPath: '/quotation',
    editPath: (r) => `/quotation/${r.quotationId}`,
    deletePath: (r) => `/lens/Quotation/delete?id=${r.quotationId}`,
  }} />
);

export const OfmsList = () => (
  <ListPage cfg={{
    title: 'Order Forwarding Memos',
    endpoint: '/lens/OrderForwardingMemo/getAll', params: { pageNo: 0, pageSize: 500 },
    columns: [
      C('ofmNo', 'OFM No'),
      C('customer', 'Customer'),
      C('quotationNo', 'Quotation'),
      C('branch', 'Branch'),
      C('ofmStatus', 'Status'),
      C('ofmDate', 'Date', { render: (r) => fmtDate(r.ofmDate) }),
    ],
    searchKeys: ['ofmNo', 'customer', 'quotationNo'],
    newPath: '/createOfm',
    editPath: (r) => `/createOfm/${r.ofmNo}`,
    deletePath: (r) => `/lens/OrderForwardingMemo/delete?ofmNo=${encodeURIComponent(r.ofmNo)}`,
  }} />
);

export const PumpSealsList = () => (
  <ListPage cfg={{
    title: 'Pump Seal DRFs',
    endpoint: '/lens/pumpSeal/getAll', params: { pageNo: 0, pageSize: 500 },
    columns: [
      C('drfNumber', 'DRF No'),
      C('customerName', 'Customer'),
      C('branch', 'Branch'),
      C('salesInquiryItemReferenceNo', 'Inquiry Ref'),
      C('createdOn', 'Created', { render: (r) => fmtDate(r.createdOn) }),
    ],
    searchKeys: ['drfNumber', 'customerName'],
    newPath: '/createPump',
    editPath: (r) => `/createPump/${r.drfNumber}`,
    deletePath: (r) => `/lens/pumpSeal/delete?pumpSealDrfNo=${encodeURIComponent(r.drfNumber)}`,
  }} />
);

export const RotaryJointsList = () => (
  <ListPage cfg={{
    title: 'Rotary Joint DRFs',
    endpoint: '/lens/rotaryJoint/getAll', params: { pageNo: 0, pageSize: 500 },
    columns: [
      C('drfNumber', 'DRF No'),
      C('customerName', 'Customer'),
      C('branch', 'Branch'),
      C('createdOn', 'Created', { render: (r) => fmtDate(r.createdOn) }),
    ],
    searchKeys: ['drfNumber', 'customerName'],
    newPath: '/createRotary',
    editPath: (r) => `/createRotary/${r.drfNumber}`,
    deletePath: (r) => `/lens/rotaryJoint/delete?rotaryJointDrfNo=${encodeURIComponent(r.drfNumber)}`,
  }} />
);

export const ApiPlansList = () => (
  <ListPage cfg={{
    title: 'API Plan DRFs',
    endpoint: '/lens/apiPlan/getAll', params: { pageNo: 0, pageSize: 500 },
    columns: [
      C('drfNumber', 'DRF No'),
      C('customerName', 'Customer'),
      C('branch', 'Branch'),
      C('createdOn', 'Created', { render: (r) => fmtDate(r.createdOn) }),
    ],
    searchKeys: ['drfNumber', 'customerName'],
    newPath: '/createApi',
    editPath: (r) => `/createApi/${r.drfNumber}`,
    deletePath: (r) => `/lens/apiPlan/delete?apiPlanId=${encodeURIComponent(r.drfNumber)}`,
  }} />
);

export const AgitatorSealsList = () => (
  <ListPage cfg={{
    title: 'Agitator Seal DRFs',
    endpoint: '/lens/agitatorSeal/getAll', params: { pageNo: 0, pageSize: 500 },
    columns: [
      C('drfNumber', 'DRF No'),
      C('customerName', 'Customer'),
      C('branch', 'Branch'),
      C('createdOn', 'Created', { render: (r) => fmtDate(r.createdOn) }),
    ],
    searchKeys: ['drfNumber', 'customerName'],
    newPath: '/createAgitator',
    editPath: (r) => `/createAgitator/${r.drfNumber}`,
    deletePath: (r) => `/lens/agitatorSeal/delete?agitatorSealDrfNumber=${encodeURIComponent(r.drfNumber)}`,
  }} />
);
