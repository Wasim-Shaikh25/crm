#!/usr/bin/env bash
# LENS CRM — one-command local run (SQLite, no external deps)
#   Backend : http://localhost:8080  (Spring Boot, profile=local)
#   Frontend: http://localhost:3000  (Vite / shadcn UI)
set -e
cd "$(dirname "$0")"

echo "Starting LENS CRM (local profile)..."

# --- Backend (prefer installed mvn; fall back to the mvnw wrapper) ---
if command -v mvn >/dev/null 2>&1; then MVN=mvn; else MVN=./mvnw; fi
( cd lens-svc && $MVN spring-boot:run -Dspring-boot.run.profiles=local ) &
BACKEND_PID=$!

# --- Frontend ---
[ -f lens-ui/package.json ] || { echo "ERROR: lens-ui/package.json not found"; exit 1; }
if [ ! -d lens-ui/node_modules ]; then
    echo "Installing frontend dependencies (first run)..."
    ( cd lens-ui && npm install )
fi
( cd lens-ui && npm run dev ) &
FRONTEND_PID=$!

echo ""
echo " Backend  : http://localhost:8080  (SQLite, auto-seeded)"
echo " Frontend : http://localhost:3000"
echo " Test user: QA001 / Test@123  (see ai-agent-workflow for details)"
echo ""
echo " Press Ctrl+C to stop both servers."

cleanup() {
    echo ""; echo "Stopping servers..."
    kill $BACKEND_PID $FRONTEND_PID 2>/dev/null || true
    exit 0
}
trap cleanup SIGINT SIGTERM
wait $BACKEND_PID $FRONTEND_PID
