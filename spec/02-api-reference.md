# API Reference

Base: `http://localhost:8080` · Auth: `Authorization: Bearer <jwt>` (from `/auth/authenticate`) ·
Errors: `{ timestamp, status, detail, instance }` JSON via `ExceptionResponseHandler`.

## Auth — `/auth`

| Method | Path | Params / Body | Does |
|---|---|---|---|
| POST | `/auth/authenticate` | `{empId, password}` | Verifies credentials → `{access_token, errorMessage}` |
| POST | `/auth/resetPassword` | `{empId, oldPassword, newPassword}` | Self-service password change (public) |

## Users / Masters — `/user`

| Method | Path | Params / Body | Does |
|---|---|---|---|
| POST | `/user/createUser` | `UserDto` | Creates user; requires `designation`+`department` (resolves role `<dept>_<desig>`); `400` if missing |
| GET  | `/user/getUser` | `?empId=` | Single user |
| GET  | `/user/getAllUser` | — | All users |
| PUT  | `/user/updateUser` | `UserDto` | Update user (same validation as create) |
| DELETE | `/user/deleteUser` | `?empId=` | Delete user |
| POST | `/user/userResetPassword` | `{empId, newPassword}` | **Admin-only** forced reset (403 for non-admin) |
| GET  | `/user/getAllDepartments` / `getAllBranches` / `allDesignations` | — | Master dropdown data (plain string lists) |
| POST | `/user/saveDepartment` / `saveBranch` / `saveDesignation` | dto | Create master entries |
| POST | `/user/saveRolesWithAuthorities` / `getRolesWithAuthorities` | dto | Role management |
| POST | `/user/setAdmin/{empId}` / `removeAdmin/{empId}` | path | Toggle admin |
| GET  | `/user/getAllUsersByFilter` | query | Filtered user list |

## Customers — `/lens/customer`

| Method | Path | Params / Body | Does |
|---|---|---|---|
| POST | `/lens/customer/save` | `CustomerDto` (+ `contactDetail` set) | Creates customer; server generates `customerReferenceNumber` (`CUST/..`) and contact refs |
| PUT  | `/lens/customer/Update` | `CustomerDto` | Update by ref no |
| GET  | `/lens/customer/get` | `?customerReferenceNumber=` | Single customer |
| GET  | `/lens/customer/getAll` | `?pageNo&pageSize` (def 0/10) | Paged list — pass `pageSize` for full lists |
| GET  | `/lens/customer/getAllCustomerByFilter` | `?customerReferenceNumber&customerName&branch&pageNo&pageSize` | Search |
| GET  | `/lens/customer/addresses` | `?customerReferenceNumber=` | All saved addresses for a customer |
| GET  | `/lens/customer/keyword` | `?keyword=` | Typeahead search |
| DELETE | `/lens/customer/delete` | `?customerReferenceNumber=` | Delete |

## Sales Inquiry — `/lens/salesInquiry`

| Method | Path | Params / Body | Does |
|---|---|---|---|
| POST | `/lens/salesInquiry/save` | `SalesInquiryDto` (nested pump/agitator/apiPlan/rotary items) | Creates inquiry + items; generates `SAL/…` and per-item `*/PUM|AGT|API|ROT/` refs |
| PUT  | `/lens/salesInquiry/Update` | `SalesInquiryDto` | Update; only creator may delete items |
| GET  | `/lens/salesInquiry/get` | `?itemReferenceNo=` | **Chain-fetch**: finds the item by any item ref → `{salesInquiry, <type>Inquiry}`. **Branch-scoped** — caller must share the item's branch |
| GET  | `/lens/salesInquiry/getAllSalesInquiryByFilter` | `?salesInquiryItemReferenceNo&customerName&industry&branch&pageNo&pageSize` | Paged search |
| DELETE | `/lens/salesInquiry/delete` | `?salesInquiryReferenceNo[&itemReferenceNo]` | Whole inquiry or single item |

## DRFs — `/lens/{pumpSeal|rotaryJoint|apiPlan|agitatorSeal}`

All four share the same contract (`getParam` differs):

| Method | Path | Params | Does |
|---|---|---|---|
| POST | `/{type}/save` | `XyzDto` | Create DRF; generates `drfNumber` (`PUM/…` etc.); stores nested `<type>InquiryItem` |
| PUT  | `/{type}/Update` (or `/update`) | `XyzDto` | Update by `drfNumber` |
| GET  | `/{type}/get` | `?{type}ReferenceNo=` (= `drfNumber`) | Single DRF incl. items, attachments |
| GET  | `/{type}/getAll` | `?pageNo&pageSize` | Paged list |
| GET* | `/{type}/getAll{Type}ByFilter` | filter params | Filtered search (present in controller, some commented) |
| DELETE | `/{type}/delete` | `?{type}ReferenceNo=` | Delete |

## Quotation — `/lens/Quotation`

| Method | Path | Params | Does |
|---|---|---|---|
| POST | `/lens/Quotation/save` | `QuotationDTO` (+ `items`) | Create; generates `Q/…` ref |
| PUT  | `/lens/Quotation/update` | `QuotationDTO` | Update |
| GET  | `/lens/Quotation/get` | `?quotationNo=` | Single quotation + items |
| GET  | `/lens/Quotation/getByNo` | `?quotationNo=` | **Chain-fetch for OFM** — returns quotation + items for prefill |
| GET  | `/lens/Quotation/getAll` / `getAllQuotationByFilter` | filter + page | Lists/search |
| DELETE | `/lens/Quotation/delete` | `?quotationNo=` | Delete |

## OFM — `/lens/OrderForwardingMemo`

| Method | Path | Params | Does |
|---|---|---|---|
| POST | `/lens/OrderForwardingMemo/save` | `OrderForwardingMemoDTO` (+ `ofmItems`, `endUserDetails`) | Create; generates `OFM/…` |
| PUT  | `/lens/OrderForwardingMemo/update` | dto | Update |
| GET  | `/lens/OrderForwardingMemo/get` | `?ofmNo=` | Single OFM + items + end-user + communications |
| GET  | `/lens/OrderForwardingMemo/getAll` / `/lens/getAllOrderForwardingMemoByFilter` | page/filter | Lists |
| DELETE | `/lens/OrderForwardingMemo/delete` | `?ofmNo=` | Delete |

## OFM Communication — `/lens/ofmCommunication`

| Method | Path | Params | Does |
|---|---|---|---|
| GET  | `/lens/ofmCommunication/get` | `?ofmNo=` | **Latest** activity for that OFM |
| GET  | `/lens/ofmCommunication/getAll` | `?ofmNo=` | Full activity history (desc) |
| POST | `/lens/ofmCommunication/save` | form fields (`@ModelAttribute`) | Append activity (activity, comments, by) |
| PUT  | `/lens/ofmCommunication/update` | dto | Update an activity row |
| GET  | `/lens/ofmCommunication/downloadFiles/{fileName}` | path | Download an attachment |

## Dashboard, Masters, Files

| Method | Path | Params | Does |
|---|---|---|---|
| GET | `/lens/dashboard/summary` | — | `{customerCount, salesInquiryCount, pumpSealCount, rotaryJointCount, apiPlanCount, agitatorSealCount, quotationCount, ofmCount, ofmCountByStatus, recentOfmActivities[10]}` |
| GET | `/lens/drawingMasterTable` | — | Full master-table |
| GET | `/lens/queryDrawingMasterTablebyColumn` | `?columnName=` | Values for one master category (e.g. `MOC`, `SERIES`) |
| POST | `/lens/fileUpload/file` | multipart `file` + `type` | Upload → stored under `./uploads/<type>/` |
| GET  | `/lens/file/download/` | `?filePath=&fileName=` | Download |
| GET  | `/lens/filter` | `?drfNumber&branch&customerName&pageNo&pageSize` | Cross-DRF search (DrfFilter page) |

## Reference-number generation

`SalesInquiryIdSequence` — prefixes from `*.prefix` props: `SAL/`, `CUST/`, `Q/`, `OFM/`,
`PUM/`, `AGT/`, `API/`, `ROT/`. Inquiries produce a parent ref + per-item refs used by
the chain-fetch endpoints above.
