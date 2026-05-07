@echo off
setlocal enabledelayedexpansion

set CUCUMBER_FILTER_TAGS=@ANDROID and @execution
set RESULTS_DIR=target/site/reports/EUDI_Wallet_Version_2025.12.34-Demo
set BACKUP_DIR=target/serenity-backup

REM Clean up leftovers from previous runs
del /q target\rerun.txt 2>nul
del /q target\rerun2.txt 2>nul
del /q "%BACKUP_DIR%\*.json" 2>nul
del /q "%RESULTS_DIR%\*.json" 2>nul

REM Main run
call mvn test -Dtest=TestRunner -Dcucumber.filter.tags="@ANDROID and @execution" %*

REM Backup full results before any rerun overwrites them
if not exist "%BACKUP_DIR%" mkdir "%BACKUP_DIR%"
copy "%RESULTS_DIR%\*.json" "%BACKUP_DIR%\" >nul 2>&1

set COUNT=0
for %%f in ("%BACKUP_DIR%\*.json") do (
    set /a COUNT+=1
)

echo --- Backed up !COUNT! JSON file(s) ---

REM Rerun pass 1
if exist target\rerun.txt (
    for %%A in (target\rerun.txt) do (
        if %%~zA gtr 0 (
            echo --- Failures detected. Rerunning failed scenarios (pass 1/2) ---
            call mvn test -Dtest=RerunTestRunner %*

            echo --- Merging pass 1 results ---
            python merge_serenity_results.py

            copy "%RESULTS_DIR%\*.json" "%BACKUP_DIR%\" >nul 2>&1
        )
    )
)

REM Rerun pass 2
if exist target\rerun2.txt (
    for %%A in (target\rerun2.txt) do (
        if %%~zA gtr 0 (
            echo --- Failures still present. Rerunning failed scenarios (pass 2/2) ---
            call mvn test -Dtest=RerunTestRunner -Dcucumber.features="@target/rerun2.txt" %*

            echo --- Merging pass 2 results ---
            python merge_serenity_results.py
        )
    )
)

REM Remove old Serenity report
rmdir /s /q target\site\serenity 2>nul

REM Aggregate Serenity report
call mvn serenity:aggregate -Dtags="ANDROID and execution"

echo --- Applying custom CSS ---

set REPORT_DIR=target/site/reports/EUDI_Wallet_Version_2025.12.34-Demo/css
set CUSTOM_CSS=src/test/resources/custom-style.css

if exist "%CUSTOM_CSS%" (

    set CORE_CSS_PATH=

    for /r "%REPORT_DIR%" %%f in (core.css) do (
        set CORE_CSS_PATH=%%f
        goto :foundcss
    )

    :foundcss

    if defined CORE_CSS_PATH (
        echo Found core.css at: !CORE_CSS_PATH!
        copy /y "%CUSTOM_CSS%" "!CORE_CSS_PATH!" >nul
        echo Successfully replaced core.css with custom-style.css.
    ) else (
        echo Warning: core.css not found in %REPORT_DIR%. Cannot apply custom style.
    )

) else (
    echo Warning: custom-style.css not found at %CUSTOM_CSS%.
)

echo --- Local execution complete. Report is available at target/site/serenity/index.html ---

endlocal