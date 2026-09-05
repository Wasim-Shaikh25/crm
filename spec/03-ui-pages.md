# UI Pages

React Router routes in `lens-ui/src/App.jsx`. All routes except `/login` and `/reset`
are wrapped in `RequireAuth` (redirect → `/login` when no token).

## Auth

| Route | Component | Does | APIs |
|---|---|---|---|
| `/login` | `Login` | EmpId + password → store JWT → `/dashboard` | `POST /auth/authenticate` |
| `/reset` | `ResetPassword` | EmpId + current + new password (public) | `POST /auth/resetPassword` |
| `/updatePassword` | `UpdatePassword` | In-app password update (authed) | `POST /auth/resetPassword` |

## Dashboards

| Route | Component | Does | APIs |
|---|---|---|---|
| `/dashboard` | `Dashboard` | KPI cards (customers/inquiries/quotations/OFMs/users/DRFs), "DRFs by Type" + "OFM by Status" bar charts, "Recent OFM Activity" table | `GET /lens/dashboard/summary` |
| `/drfDashboard` | `DrfDashboard` | DRF counts per type | `GET /lens/dashboard/summary` |
| `/ofmDashboard` | `OfmDashboard` | OFM status distribution + recent activity | `GET /lens/dashboard/summary` |
| `/drfFilter`, `/editDrf` | `DrfFilter` | Cross-DRF search by drf no / branch / customer | `GET /lens/filter` |

## Masters — lists

All list pages share `ListPage` + `DataTable` (client search box, `searchKeys`,
`pageSize=500` params, New / edit / delete actions).

| Route | List | Endpoint | New/Edit path |
|---|---|---|---|
| `/users` | Users | `GET /user/getAllUser` | `/CreateUser` |
| `/editCustomer` | Customers | `GET /lens/customer/getAll` | `/Customer/:rId?` |
| `/editSales` | Sales Inquiries | `GET /lens/salesInquiry/getAllSalesInquiryByFilter` | `/SalesInquiry/:sId?` |
| `/EditQuotation` | Quotations | `GET /lens/Quotation/getAll` | `/quotation/:qId?` |
| `/editOfm` | OFMs | `GET /lens/OrderForwardingMemo/getAll` | `/createOfm/:oId?` |
| `/editPump` | Pump Seals | `GET /lens/pumpSeal/getAll` | `/createPump/:pId?` |
| `/editRotary` | Rotary Joints | `GET /lens/rotaryJoint/getAll` | `/createRotary/:rjId?` |
| `/editApi` | API Plans | `GET /lens/apiPlan/getAll` | `/createApi/:apId?` |
| `/editAgitator` | Agitator Seals | `GET /lens/agitatorSeal/getAll` | `/createAgitator/:aId?` |

## Forms (config-driven via `EntityForm` + `FORM_CONFIGS`)

`EntityForm` renders sections/fields from `formConfigs.js`, handles:
- create (`POST <base>/save`) and edit (`GET <base>/get?…ReferenceNo=` → `PUT <base>/Update`)
- field types: text, number, date, textarea, select (master data), yes/no, file upload
- master selects call `useMasterOptions` → `GET /lens/queryDrawingMasterTablebyColumn?columnName=`
- file fields → `POST /lens/fileUpload/file` + preview/download links

| Route | Form | Save/Get | Extras |
|---|---|---|---|
| `/Customer[/:rId]` | `CustomerForm` | `/lens/customer/*` | contactDetail rows |
| `/CreateUser[/:uId]`, `/signup` | `UserForm` | `/user/createUser`,`updateUser` | department/designation/branch selects (string + object shapes) |
| `/SalesInquiry[/:sId]` | `SalesInquiryForm` | `/lens/salesInquiry/*` | nested item arrays (pump/agitator/api/rotary) |
| `/quotation[/:qId]` | `QuotationForm` | `/lens/Quotation/*` | **Fetch from Sales Inquiry** panel (`salesInquiry/get?itemReferenceNo=`) + PDF download |
| `/createOfm[/:oId]` | `OfmForm` | `/lens/OrderForwardingMemo/*` | **Fetch from Quotation** (`Quotation/getByNo`) + customer-address picker (`customer/addresses`) + items + PDF |
| `/createPump[/:pId]` | `PumpSealForm` | `/lens/pumpSeal/*` | **Fetch from Sales Inquiry Item** → `pumpInquiryItem` |
| `/createRotary[/:rjId]` | `RotaryForm` | `/lens/rotaryJoint/*` | same → `rotaryJointInquiryItem` |
| `/createApi[/:apId]` | `ApiPlanForm` | `/lens/apiPlan/*` | same → `apiPlanInquiryItem` |
| `/createAgitator[/:aId]` | `AgitatorForm` | `/lens/agitatorSeal/*` | same → `agitatorInquiryItem` |

## OFM Communication — `/ofmComm`

`OfmCommunication` page: OFM-no search → loads latest + history table → post new
activity (activity select from `OFM_CATEGORY` masters, comments, optional file upload
via `fileUpload/file`; attachments downloadable via `ofmCommunication/downloadFiles`).

APIs: `GET /ofmCommunication/get`, `GET /ofmCommunication/getAll`,
`POST /ofmCommunication/save`, `GET /queryDrawingMasterTablebyColumn?columnName=OFM_CATEGORY`.

## Navigation shell

`Layout` sidebar groups: Dashboards (Overview, DRF, OFM Status, DRF Search),
Masters (Users, Customers), Sales (Sales Inquiries, Quotations),
DRF (Pump Seal, Rotary Joint, API Plan, Agitator Seal), OFM (OFM List, Communication).
Logout clears the token → `/login`.
