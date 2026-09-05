# Data Model

33 tables under the `local` profile (`schema-local.sql`; Hibernate-managed in prod/uat).

## Core entities

| Entity (table) | Business key | Notes |
|---|---|---|
| `Customer` | `customerReferenceNumber` `CUST/n` | has many `ContactDetail` (address sets) |
| `ContactDetail` | `contactReferenceNumber` `CONT/n` | per-customer address/contact |
| `Users` | `empId` | links `Role`, `Designation`, `Department`; `UserBranch` join table |
| `Role` / `Authority` / `RoleAuthority` | name `Dept_Desig` | RBAC |
| `Branch`, `Department`, `Designation` | name | org masters |
| `MasterTable` | (category, value) | all dropdown values |
| `DrawingMasterTable` | — | drawing lookups |

## Business entities

| Entity | Business key | Parent/children |
|---|---|---|
| `SalesInquiry` | `salesInquiryReferenceNo` `SAL/n` | 1-n items: `PumpInquiry`, `AgitatorInquiry`, `ApiPlanInquiry`, `RotaryJointInquiry` (each with own `*/n` ref) |
| `PumpSeal` | `drfNumber` `PUM/n` | nested `PumpInquiry` (item snapshot), attachments |
| `RotaryJoint` | `drfNumber` `ROT/n` | nested `RotaryJointInquiry` |
| `ApiPlan` | `drfNumber` `API/n` | nested `ApiPlanInquiry` |
| `AgitatorSeal` | `drfNumber` `AGT/n` | nested `AgitatorInquiry` |
| `Quotation` | `quotationNo` `Q/n` | 1-n `QuotationItem` |
| `OrderForwardingMemo` | `ofmNo` `OFM/n` | 1-n `OfmItem` + `EndUserDetail`, linked `quotationNo` |
| `OfmCommunication` | — | rows keyed by `ofmNo` (activity log) |

Each entity also has `createdByUser` / `createdOn` audit columns; primitives must be
non-null (SQLite seeds set 0/false defaults).

## Seeded data (local profile, idempotent)

- `seed-local.sql` — 211 master values (NATURE, ORDER_TYPE, MOC, SERIES, MAKE,
  API_PLAN, SEAL_TYPE, PUMP_TYPE, INDUSTRY, OFM_CATEGORY, PRIORITY, …),
  designations/departments/branches, authorities, roles, 5 users + `QA001`
  (ADMIN, `Test@123`), role-authority links.
- `seed-local-data.sql` — business fixtures: 2 customers + addresses, 1 sales
  inquiry (`SAL/TEST/0001` + pump item `SAL/TEST/0001/P1`), pump-seal DRF,
  quotation `Q/TEST/0001`, OFM `OFM/TEST/0001` + item + end-user, 2 OFM
  activities, QA001→Baroda branch link.
