# Local Setup & Run

## One command

```bat
:: Windows
start.bat
```
```bash
# Linux/macOS/git-bash
./start.sh
```

Starts backend (`:8080`, `local` profile) and frontend (`:3000`), installs
`node_modules` on first run, prints URLs + the seeded test user.

## Manual

```bash
# backend (needs Maven on PATH, or ./mvnw)
cd lens-svc && mvn spring-boot:run -Dspring-boot.run.profiles=local

# frontend
cd lens-ui && npm install && npm run dev
```

## Local profile behavior

- `lens-local.db` created in `lens-svc/` on first run.
- `schema-local.sql` creates all tables; `seed-local.sql` + `seed-local-data.sql`
  run every startup and are idempotent (`WHERE NOT EXISTS` guards).
- Uploads go to `./uploads/<type>/` (auto-created).
- JWT: set `JWT_SECRET` to override the documented dev default.
- CORS allows `http://localhost:3000`; Vite proxies `/lens`, `/user`, `/auth`.

## Seeded logins

| EmpId | Password | Role |
|---|---|---|
| `QA001` | `Test@123` | ADMIN |

(other seeded users exist for UAT-style testing — see `seed-local.sql`)

## Verify

```bash
curl -X POST http://localhost:8080/auth/authenticate \
  -H "Content-Type: application/json" -d '{"empId":"QA001","password":"Test@123"}'
```

Playwright E2E suite (external): `C:\qa-workspace\lens-qa` → `npx playwright test`
(19 tests: auth, CRUD, chain-fetch, OFM comm, PDF, upload, dashboards).
