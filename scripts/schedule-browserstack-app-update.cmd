@echo off
setlocal

REM Registers a daily Windows Task Scheduler job that fetches the latest
REM eudi-wallet-android / eudi-wallet-ios builds from BrowserStack and updates
REM the bs:// app URLs in env.properties.

set "SCRIPT_DIR=%~dp0"
set "PYTHON="

REM Locate a Python interpreter (py launcher first, then python)
for /f "delims=" %%P in ('py -3 -c "import sys; print(sys.executable)" 2^>nul') do set "PYTHON=%%P"
if "%PYTHON%"=="" for /f "delims=" %%P in ('where python 2^>nul') do (
    if "%PYTHON%"=="" set "PYTHON=%%P"
)

if "%PYTHON%"=="" (
    echo ERROR: Python not found. Install Python 3 and add it to PATH.
    exit /b 1
)

set "RUN_CMD=\"%PYTHON%\" \"%SCRIPT_DIR%update-latest-browserstack-apps.py\""

echo Registering scheduled task 'EUDI-BrowserStack-App-Update' (daily at %~1)...

schtasks /Create /F ^
    /TN "EUDI-BrowserStack-App-Update" ^
    /TR "%RUN_CMD%" ^
    /SC DAILY ^
    /ST %~1 ^
    /RL LIMITED

if %errorlevel% neq 0 (
    echo ERROR: failed to create scheduled task.
    exit /b 1
)

echo.
echo Task created. It will fetch the latest BrowserStack builds every day at %~1.
echo You can manage it in Task Scheduler (taskschd.msc) or run:
echo   schtasks /Run /TN EUDI-BrowserStack-App-Update
echo   schtasks /Query /TN EUDI-BrowserStack-App-Update

endlocal
