# LENS CRM — UI v2

Modern rebuild of `lens-ui` on **Vite + React 19 + Tailwind CSS v4 + shadcn/ui**.

## Stack

- Vite 8 (dev server on :3000, proxies `/lens`, `/user`, `/auth` to `localhost:8080`)
- React 19 + React Router 7
- Tailwind v4 + shadcn/ui components (`src/components/ui`)
- axios (auth cookie + 401 interceptor + sonner toasts)
- recharts (dashboards), @react-pdf/renderer (datasheet PDFs)

## Run

```bash
npm install
npm run dev     # http://localhost:3000
npm run build   # production build -> dist/
```

## Structure

- `src/lib/axios.js` — API client (interceptor: Bearer token from `access_token` cookie, 401 → logout, error toasts)
- `src/lib/formConfigs.js` — **all create/edit form definitions** (sections/fields/item editors). Change a form = edit its config.
- `src/lib/useMasterOptions.js` — master-table & API dropdown hooks (`MasterTableForDrawing`, branches, designations)
- `src/lib/upload.js` — file upload/download (`/lens/fileUpload/file`, `/lens/file/download/`)
- `src/components/` — `CrudForm`, `EntityForm`, `FormField`, `DataTable`, `ListPage`, `RequireAuth`, `Layout`
- `src/pages/` — screens (Lists.jsx groups the list pages, Forms.jsx groups the form pages)
- `src/pdf/DataSheetPdf.jsx` — generic PDF datasheet generated from form config

## Notes

- Backend unchanged — same `/lens`, `/user`, `/auth` endpoints.
- Add fields/master values: edit `formConfigs.js` and/or run `lens-svc/.../Queries/masterData.sql`.
- `lens-ui` (MUI) is kept for reference until v2 is signed off.
