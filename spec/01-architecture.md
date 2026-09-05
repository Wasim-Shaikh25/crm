# Architecture

## Stack

| Layer | Backend `lens-svc` | Frontend `lens-ui` |
|---|---|---|
| Framework | Spring Boot 3, Java 17 | React 19 + Vite 8 |
| UI lib | — | Tailwind CSS v4, shadcn/ui, lucide icons |
| State | — | React hooks + `AuthContext` |
| Data | Spring Data JPA / Hibernate | Axios (JWT interceptor) |
| DB (local) | SQLite via `local` profile | — |
| DB (prod) | SQL Server (`prod`/`uat` profiles, secrets external) | — |
| Auth | JWT (`JwtService` + `JwtAuthFilter`) | token in `localStorage`, auto-attach + 401 → login |
| Charts/PDF | — | Recharts, `@react-pdf/renderer` |
| Docs | springdoc OpenAPI (`/swagger-ui`) | — |

## Backend layers

```
controller/  REST endpoints (LensRestController /lens/**, AuthController /auth/**, UserController /user/**)
service/     business logic + reference-number generation (SalesInquiryIdSequence)
repository/  JPA repositories
entity/      JPA entities (33 tables)
dto/         request/response DTOs (BeanUtils copy)
config/      SecurityConfiguration, JwtAuthFilter, JwtService, CORS, FileStorageConfig
utils/       FileUploadUtil / FileDownloadUtil (auto-creates upload dirs)
exception/   LensServiceException + ExceptionResponseHandler (400/403/404/500 JSON)
resources/   application-{local,uat,prod}.properties, schema-local.sql,
             seed-local.sql + seed-local-data.sql, auth-config.json, Queries/*.sql
```

## Frontend structure

```
src/
  App.jsx          all routes (list + form + detail variants)
  pages/           Dashboard, DrfDashboard, OfmDashboard, DrfFilter,
                   Lists (generic list pages), Forms (generic + custom forms),
                   Login, ResetPassword, UpdatePassword, OfmCommunication
  components/      Layout (sidebar), EntityForm (config-driven form engine),
                   ListPage + DataTable (search/filter/paginate UI),
                   FormField/SelectField/TextAreaField/DateField/NumberField/YesNo,
                   FileUpload, AuthContext, RequireAuth
  lib/             axios.js (base URL + JWT), formConfigs.js (all entity schemas),
                   format.js, upload.js, useMasterOptions.js
  pdf/             DataSheetPdf.jsx (quotation/DRF PDFs)
```

## Security model

- `/auth/authenticate`, `/auth/resetPassword`, `/error`, OPTIONS, swagger → public.
- If `security.endpoint.config.enabled=true` → `auth-config.json` (classpath) maps
  endpoint → required authority; `anyRequest().denyAll()` default. If disabled →
  permissive mode (`/lens/**`, `/user/**` permitted).
- Roles are composite: `<Department>_<Designation>` (e.g. `Engineering_Engineer`);
  authorities derive from role→authority mapping.
- JWT secret: `JWT_SECRET` env var (base64); local profile has a dev default.
- CORS: `CORS_ALLOWED_ORIGINS` env var; local profile allows localhost:3000.
- File upload: type/size/path validated in `FileUploadUtil`; max 50 MB.

## Configuration files

- `application-local.properties` — SQLite, seed scripts, relative `./uploads/`,
  dev JWT default, `security.endpoint.config.enabled` toggle. **Tracked** (no secrets).
- `application-prod/uat.properties` — import external `application-secret.properties`
  (DB creds, real JWT secret) via `spring.config.import`. Never committed.
- `auth-config.json` — endpoint→authority map (only used when secure mode on).
