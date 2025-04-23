@echo off
echo ===============================
echo [INFO] Cleaning and Compiling...
echo ===============================
mvn clean compile

if %errorlevel% neq 0 (
    echo [ERROR] Build failed.
    goto end
)

echo ===============================
echo [SUCCESS] Build completed!
echo ===============================

:end
echo.
echo [INFO] Execution finished. Press any key to exit.
pause > nul
