# End-to-End Flows

## 1. Authentication

```
Login page ──POST /auth/authenticate {empId,password}──▶ Backend
                                                        │ verify hash
   ┌────────────────────────────────────────────────────┘
   ▼ {access_token}
localStorage.token ──▶ axios interceptor adds `Authorization: Bearer`
   every /lens, /user call ──▶ JwtAuthFilter validates → SecurityContext
   401 ──▶ interceptor clears token → redirect /login
```

- Roles = `<Department>_<Designation>`; authorities from role→authority table.
- When `security.endpoint.config.enabled=true`, `auth-config.json` enforces
  per-endpoint authorities; otherwise permissive mode.

## 2. Business chain — the core flow

```
┌──────────┐   ┌───────────────┐   ┌────────────┐   ┌──────────────────┐
│ Customer │──▶│ Sales Inquiry │──▶│ Quotation  │──▶│ OFM (order fwd)  │
│ CUST/n   │   │ SAL/n + items │   │ Q/n        │   │ OFM/n            │
└──────────┘   └───────┬───────┘   └────────────┘   └────────┬─────────┘
                       │ itemRef (…/PUM|AGT|API|ROT)          │ activity
                       ▼                                     ▼
                ┌─────────────┐                      ┌──────────────────┐
                │  DRF forms  │                      │ OFM Communication │
                │ 4 types     │                      │ history + files   │
                └─────────────┘                      └──────────────────┘
```

**Chain-fetch (the "auto-fill" links):**

| From → To | Endpoint | Fills |
|---|---|---|
| Sales Inquiry item → **Quotation** | `GET /lens/salesInquiry/get?itemReferenceNo=` | customer, address, branch, enquiry nos |
| Sales Inquiry item → **DRF** (all 4) | same | `<type>InquiryItem` section + customer/branch |
| Quotation → **OFM** | `GET /lens/Quotation/getByNo?quotationNo=` | customer, address, branch, engineer, category, payment terms, `ofmItems` from quotation items |
| Customer → **OFM** | `GET /lens/customer/addresses?customerReferenceNumber=` | address dropdown |

> ⚠️ `salesInquiry/get` is **branch-scoped**: the caller's `UserBranch` entries must
> include the item's branch, else `404 "No matching inquiry found"`.

## 3. Create Customer

```
/Customer form → POST /lens/customer/save
   server: new customerId + customerReferenceNumber (CUST/n)
           + contactReferenceNumber per contactDetail row
→ toast → /editCustomer list (GET getAll?pageSize=500) shows the row
```

## 4. Create User

```
/CreateUser → designation + department selects (string lists)
→ POST /user/createUser  → role resolved as <department>_<designation>
→ missing designation → 400 "Designation is required"
→ new user can log in immediately
```

## 5. Password flows

| Flow | Route / caller | Endpoint |
|---|---|---|
| Self-service | `/reset` or `/updatePassword` | `POST /auth/resetPassword {empId, oldPassword, newPassword}` |
| Admin reset | admin only | `POST /user/userResetPassword {empId, newPassword}` (403 otherwise) |

## 6. OFM Communication

```
/ofmComm: type OFM no → GET /ofmCommunication/get (latest)
                      → GET /ofmCommunication/getAll (history table)
post form → POST /ofmCommunication/save (@ModelAttribute fields)
optional attachment → POST /lens/fileUpload/file → link stored
download → GET /ofmCommunication/downloadFiles/{fileName}
→ row appears in history + "Recent OFM Activity" on /dashboard
```

## 7. File upload / download

- Upload: `POST /lens/fileUpload/file` (multipart, `type` selects the
  `./uploads/<type>/` subdir; dirs auto-created; type/size validated; ≤50 MB).
- Download: `GET /lens/file/download/?filePath=&fileName=`.

## 8. PDF generation

Client-side via `@react-pdf/renderer` (`src/pdf/DataSheetPdf.jsx`):
quotation + OFM pages offer a Download button → builds the document from the
loaded form state → streams a real PDF blob (verified byte-size in tests).

## 9. Dashboard data

`GET /lens/dashboard/summary` aggregates repository counts + `findTop10` OFM
activities → KPI cards, two bar charts (Recharts), recent-activity table.
