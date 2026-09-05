@echo off
REM ============================================================
REM  LENS CRM — one-command local run (SQLite, no external deps)
REM  Backend : http://localhost:8080  (Spring Boot, profile=local)
REM  Frontend: http://localhost:3000  (Vite / shadcn UI)
REM ============================================================
echo Starting LENS CRM (local profile)...

REM --- Backend (prefer installed mvn; fall back to the mvnw wrapper) ---
mvn -v >nul 2>&1
if %errorlevel%==0 (set MVN=mvn) else (set MVN=mvnw.cmd)
start "LENS Backend" cmd /k "cd /d %~dp0lens-svc && %MVN% spring-boot:run -Dspring-boot.run.profiles=local"

REM --- Frontend ---
if not exist lens-ui\package.json (
    echo ERROR: lens-ui\package.json not found
    pause
    exit /b 1
)
if not exist lens-ui\node_modules (
    echo Installing frontend dependencies (first run)...
    pushd lens-ui
    call npm install
    popd
)
start "LENS Frontend" cmd /k "cd /d %~dp0lens-ui && npm run dev"

echo.
echo  Backend  : http://localhost:8080   (SQLite, auto-seeded)
echo  Frontend : http://localhost:3000
echo  Test user: QA001 / Test@123   (see ai-agent-workflow for details)
echo.
echo  Close the two server windows to stop the app.
pause
