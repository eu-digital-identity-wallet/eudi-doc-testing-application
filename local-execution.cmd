@echo off
setlocal EnableDelayedExpansion

set CUCUMBER_FILTER_TAGS=@execution or @Q4_2024
set RESULTS_DIR=target\site\reports\EUDI_Wallet_Version_2025.12.34-Demo
set BACKUP_DIR=target\serenity-backup

echo ========================================================
echo CLEANUP
echo ========================================================

if exist target\rerun.txt del /f /q target\rerun.txt
if exist target\rerun2.txt del /f /q target\rerun2.txt

if exist "%BACKUP_DIR%\*.json" del /f /q "%BACKUP_DIR%\*.json"
if exist "%RESULTS_DIR%\*.json" del /f /q "%RESULTS_DIR%\*.json"

echo ========================================================
echo MAIN EXECUTION
echo ========================================================

call mvn test ^
-Dtest=TestRunner ^
-Dcucumber.filter.tags="@execution or @Q4_2024" ^
%*

echo ========================================================
echo BACKUP RESULTS
echo ========================================================

if not exist "%BACKUP_DIR%" mkdir "%BACKUP_DIR%"

copy "%RESULTS_DIR%\*.json" "%BACKUP_DIR%\" >nul 2>&1

set COUNT=0

for %%f in ("%BACKUP_DIR%\*.json") do (
    if exist "%%f" set /a COUNT+=1
)

echo --- Backed up !COUNT! JSON file(s) ---

echo ========================================================
echo RERUN PASS 1
echo ========================================================

if exist target\rerun.txt (

    for %%A in (target\rerun.txt) do (

        if %%~zA gtr 0 (

            echo Failures detected. Rerunning failed scenarios...

            call mvn test ^
            -Dtest=RerunTestRunner ^
            %*

            echo Merging rerun results...

            python merge_serenity_results.py

            copy "%RESULTS_DIR%\*.json" "%BACKUP_DIR%\" >nul 2>&1
        )
    )
)

echo ========================================================
echo RERUN PASS 2
echo ========================================================

if exist target\rerun2.txt (

    for %%A in (target\rerun2.txt) do (

        if %%~zA gtr 0 (

            echo Failures still present. Running second rerun...

            call mvn test ^
            -Dtest=RerunTestRunner ^
            -Dcucumber.features="@target/rerun2.txt" ^
            %*

            echo Merging second rerun results...

            python merge_serenity_results.py
        )
    )
)

echo ========================================================
echo GENERATE SERENITY REPORT
echo ========================================================

if exist target\site\serenity (
    rmdir /s /q target\site\serenity
)

call mvn serenity:aggregate -Dtags="ANDROID and execution"

echo ========================================================
echo APPLY CUSTOM CSS
echo ========================================================

set REPORT_DIR=target\site\reports\EUDI_Wallet_Version_2025.12.34-Demo\css
set CUSTOM_CSS=src\test\resources\custom-style.css
set CORE_CSS_PATH=

if exist "%CUSTOM_CSS%" (

    for /r "%REPORT_DIR%" %%f in (core.css) do (
        set CORE_CSS_PATH=%%f
    )

    if not "!CORE_CSS_PATH!"=="" (

        echo Found core.css:
        echo !CORE_CSS_PATH!

        copy /y "%CUSTOM_CSS%" "!CORE_CSS_PATH!" >nul

        echo Custom CSS applied successfully.

    ) else (

        echo WARNING: core.css not found.
    )

) else (

    echo WARNING: custom-style.css not found.
)

echo ========================================================
echo EXECUTION COMPLETE
echo ========================================================

echo Report:
echo target\site\serenity\index.html

endlocal