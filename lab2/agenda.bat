@echo off
chcp 65001 > nul

echo ===============================
echo [INFO] Compiling and running tests...
echo ===============================
call mvn clean compile
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed.
    pause
    exit /b %errorlevel%
)

echo ===============================
echo [SUCCESS] Compilation and tests passed!
echo ===============================

echo ===============================
echo [INFO] Starting Agenda System...
echo ===============================
call mvn exec:java "-Dexec.mainClass=com.Agenda.AgendaService"
if %errorlevel% neq 0 (
    echo [ERROR] Failed to start Agenda system.
    pause
    exit /b %errorlevel%
)

echo ===============================
echo [INFO] Execution finished. Press any key to exit.
echo ===============================
pause
