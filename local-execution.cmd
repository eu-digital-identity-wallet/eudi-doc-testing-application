@echo off
setlocal

set "CUSTOM_CSS=src\test\resources\custom-style.css"
set "REPORT_DIR=target\site\reports\EUDI_Wallet_Version_2025.12.34-Demo\css"

REM Run tests and generate reports
mvn verify -ntp -Dtest=TestRunner -Dcucumber.filter.tags="@Q2_2025"
mvn serenity:aggregate -Dtags="@Q2_2025"

REM Find core.css using PowerShell
for /f "delims=" %%f in ('powershell -NoProfile -Command "Get-ChildItem -Path \"%REPORT_DIR%\" -Filter core.css -Recurse | Select-Object -First 1 -ExpandProperty FullName"') do set "CORE_CSS=%%f"

if exist "%CUSTOM_CSS%" (
    if exist "%CORE_CSS%" (
        echo Found core.css at: %CORE_CSS%
        copy /Y "%CUSTOM_CSS%" "%CORE_CSS%"
        echo Successfully replaced core.css with custom-style.css.
    ) else (
        echo Warning: core.css not found in %REPORT_DIR%. Cannot apply custom style.
    )
) else (
    echo Warning: custom-style.css not found at %CUSTOM_CSS%.
)

echo --- Local execution complete. Report is available at target\site\serenity\index.html ---
pause
