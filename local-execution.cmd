@echo off
setlocal EnableDelayedExpansion

set "RESULTS_DIR=target\site\reports\EUDI_Wallet_Version_2026.08.41-Demo"
set "BACKUP_DIR=target\serenity-backup"

echo ===============================
echo Cleaning previous execution...
echo ===============================

if exist target\serenity rmdir /S /Q target\serenity
if exist target\site\reports rmdir /S /Q target\site\reports
if exist target\site\serenity rmdir /S /Q target\site\serenity

if exist target\rerun.txt del /Q target\rerun.txt
if exist target\rerun2.txt del /Q target\rerun2.txt
if exist target\rerun3.txt del /Q target\rerun3.txt

echo.
echo ===============================
echo Main execution
echo ===============================

call mvn clean verify -ntp ^
    -Dtest=TestRunner ^
    -Dcucumber.filter.tags="@ANDROID and @execution_Q12_2026"

echo.
echo ===============================
echo Backup Serenity JSON
echo ===============================

mkdir "%BACKUP_DIR%" 2>nul

if exist "%RESULTS_DIR%\*.json" (
    copy /Y "%RESULTS_DIR%\*.json" "%BACKUP_DIR%\" >nul
)

echo.
echo ===============================
echo Rerun Pass 1
echo ===============================

if exist target\rerun.txt (
    for %%A in (target\rerun.txt) do (
        if %%~zA GTR 0 (
            call mvn test -Dtest=RerunTestRunner
            python merge_serenity_results.py
        )
    )
)

echo.
echo ===============================
echo Rerun Pass 2
echo ===============================

if exist target\rerun2.txt (
    for %%A in (target\rerun2.txt) do (
        if %%~zA GTR 0 (
            call mvn test ^
                -Dtest=RerunTestRunner2 ^
                -Dcucumber.features="@target/rerun2.txt"

            python merge_serenity_results.py
        )
    )
)

echo.
echo ===============================
echo Rerun Pass 3
echo ===============================

if exist target\rerun3.txt (
    for %%A in (target\rerun3.txt) do (
        if %%~zA GTR 0 (
            call mvn test ^
                -Dtest=RerunTestRunner3 ^
                -Dcucumber.features="@target/rerun3.txt"

            python merge_serenity_results.py
        )
    )
)

echo.
echo ===============================
echo Generate Serenity Report
echo ===============================

call mvn serenity:aggregate ^
    -Dtags="@ANDROID and @execution_Q12_2026"

echo.
echo ===============================
echo Apply custom CSS
echo ===============================

if exist "src\test\resources\custom-style.css" (
    copy /Y ^
        "src\test\resources\custom-style.css" ^
        "%RESULTS_DIR%\css\core.css" >nul
)

echo.
echo ==========================================
echo Execution completed.
echo.
echo Report:
echo target\site\reports\EUDI_Wallet_Version_2026.08.41-Demo\index.html
echo ==========================================

pause