@echo off
setlocal enabledelayedexpansion

:: ============================================================================
:: Serenity Report Optimizer - Windows Batch Script
:: 
:: Purpose: Creates a lightweight, fast-to-share package from Serenity BDD
::          test reports by including only essential files and using optimal
::          compression.
:: 
:: Usage: package-report.cmd [output-name]
:: Example: package-report.cmd
::          package-report.cmd MyTestReport
:: 
:: Output: 
::   - target\site\reports\<report-name>-optimized\ (unpacked, open in browser)
::   - target\site\reports\<output-name>.7z (archive for sharing)
::     (defaults to report directory name if no output-name given)
:: 
:: Requirements: 
::   - 7-Zip (recommended for best compression)
::   - Or PowerShell (for fallback ZIP creation)
:: ============================================================================

:: -----------------------------------------------------------------------------
:: CONFIGURATION
:: -----------------------------------------------------------------------------

set REPORT_SOURCE=target\site\reports
set OUTPUT_DIR=target\site\reports
set OUTPUT_NAME=%1

:: -----------------------------------------------------------------------------
:: VALIDATION
:: -----------------------------------------------------------------------------

echo ============================================
echo  Serenity Report Optimizer (Windows)
echo ============================================
echo.

:: Check if report source exists
if not exist "%REPORT_SOURCE%" (
    echo ERROR: Report source directory not found: %REPORT_SOURCE%
    echo.
    echo Please run your tests first:
    echo   mvn verify
    exit /b 1
)

:: Find the report directory (should contain version name like EUDI_Wallet_Version_2026.10.43-Demo)
set REPORT_DIR=
for /d %%d in ("%REPORT_SOURCE%\*" ) do (
    if not defined REPORT_DIR set REPORT_DIR=%%d
)

if not defined REPORT_DIR (
    echo ERROR: No report directory found in %REPORT_SOURCE%
    exit /b 1
)

:: Get the report directory name
for %%d in ("%REPORT_DIR%") do set REPORT_NAME=%%~nxd

:: If no output name provided, use the report directory name
if "%OUTPUT_NAME%"=="" (
    set OUTPUT_NAME=%REPORT_NAME%
)

echo Found report directory: %REPORT_NAME%
echo.

:: -----------------------------------------------------------------------------
:: CREATE OUTPUT DIRECTORY
:: -----------------------------------------------------------------------------

if not exist "%OUTPUT_DIR%" (
    mkdir "%OUTPUT_DIR%"
)

:: -----------------------------------------------------------------------------
:: CREATE TEMPORARY DIRECTORY
:: -----------------------------------------------------------------------------

set TEMP_DIR=%TEMP%\serenity-report-%RANDOM%
mkdir "%TEMP_DIR%" >nul 2>&1

:: -----------------------------------------------------------------------------
:: COPY ESSENTIAL FILES
:: -----------------------------------------------------------------------------

echo Copying essential files...

:: 1. Copy asset directories (skip unused icon SVGs to reduce file count)
echo  - Asset directories...
for /d %%d in ("%REPORT_DIR%\*" ) do (
    set DIR_NAME=%%~nxd
    if "!DIR_NAME!"=="icons" (
        echo     Skipping icons/ - unused SVG files
    ) else if "!DIR_NAME!"=="bootstrap-icons" (
        echo     Copying bootstrap-icons/ - CSS and fonts only...
        if not exist "%TEMP_DIR%\bootstrap-icons" mkdir "%TEMP_DIR%\bootstrap-icons"
        if exist "%%d\bootstrap-icons.css" copy /Y "%%d\bootstrap-icons.css" "%TEMP_DIR%\bootstrap-icons\" >nul 2>&1
        if exist "%%d\fonts" xcopy /E /I /Q /Y "%%d\fonts" "%TEMP_DIR%\bootstrap-icons\fonts" >nul 2>&1
    ) else (
        echo     Copying !DIR_NAME!...
        xcopy /E /I /Q /Y "%%d" "%TEMP_DIR%\!DIR_NAME!" >nul 2>&1
    )
)

:: 2. Copy all JSON data files
echo  - JSON data files...
set JSON_COUNT=0
for %%f in ("%REPORT_DIR%\*.json") do (
    copy /Y "%%f" "%TEMP_DIR%\" >nul 2>&1
    set /a JSON_COUNT+=1
)
echo     Copied %JSON_COUNT% JSON files

:: 3. Copy ALL HTML files (individual test pages are linked from index.html)
echo  - HTML files...
set HTML_COUNT=0
for %%f in ("%REPORT_DIR%\*.html") do (
    copy /Y "%%f" "%TEMP_DIR%\" >nul 2>&1
    set /a HTML_COUNT+=1
)
echo     Copied %HTML_COUNT% HTML files

:: 4. Copy any CSS or JS files from root
for %%f in ("%REPORT_DIR%\*.css" "%REPORT_DIR%\*.js") do (
    copy /Y "%%f" "%TEMP_DIR%\" >nul 2>&1
)

:: -----------------------------------------------------------------------------
:: COPY OPTIMIZED BUNDLE TO REPORTS DIRECTORY
:: -----------------------------------------------------------------------------

:: Place the unpacked optimized report next to the original report in
:: target\site\reports\ so it can be viewed directly after running the script.
set OPTIMIZED_REPORT_DIR=%REPORT_SOURCE%\%REPORT_NAME%-optimized

:: Remove a previous optimized report so we always get a fresh copy
if exist "%OPTIMIZED_REPORT_DIR%" (
    rd /s /q "%OPTIMIZED_REPORT_DIR%" 2>nul
)

:: Copy the essential-file bundle (asset dirs, JSON, entry HTML) into place
xcopy /E /I /Q /Y "%TEMP_DIR%\" "%OPTIMIZED_REPORT_DIR%\" >nul 2>&1

echo.
echo  - Unpacked optimized report:
echo      %OPTIMIZED_REPORT_DIR%

:: -----------------------------------------------------------------------------
:: COUNT FILES AND SHOW STATISTICS
:: -----------------------------------------------------------------------------

echo.
echo File count:

:: Count original files
set ORIGINAL_COUNT=0
for /r "%REPORT_DIR%" %%f in (*) do set /a ORIGINAL_COUNT+=1

:: Count minimal bundle files
set MINIMAL_COUNT=0
for /r "%TEMP_DIR%" %%f in (*) do set /a MINIMAL_COUNT+=1

echo  Original: %ORIGINAL_COUNT% files
echo  Optimized: %MINIMAL_COUNT% files
echo  Reduction: %ORIGINAL_COUNT% - %MINIMAL_COUNT% = %ORIGINAL_COUNT% - %MINIMAL_COUNT% files

:: Calculate percentage
set /a REDUCTION_PCT=100 * (ORIGINAL_COUNT - MINIMAL_COUNT) / ORIGINAL_COUNT
echo  Saved: ~%REDUCTION_PCT%%% of files

:: -----------------------------------------------------------------------------
:: CREATE ARCHIVE
:: -----------------------------------------------------------------------------

echo.
echo Creating compressed archive...

:: Check if 7-Zip is installed
set SEVENZIP=
if exist "%ProgramFiles%\7-Zip\7z.exe" (
    set SEVENZIP="%ProgramFiles%\7-Zip\7z.exe"
) else if exist "%ProgramFiles(x86)%\7-Zip\7z.exe" (
    set SEVENZIP="%ProgramFiles(x86)%\7-Zip\7z.exe"
)

if defined SEVENZIP goto :create_7z
goto :create_zip

:create_7z
echo Using 7-Zip - 7z format, fast extraction...
%SEVENZIP% a -t7z "%OUTPUT_DIR%\%OUTPUT_NAME%.7z" "%TEMP_DIR%\*" >nul 2>&1

:: Get file size
for %%f in ("%OUTPUT_DIR%\%OUTPUT_NAME%.7z") do set ARCH_SIZE=%%~zf
set /a ARCH_SIZE_MB=ARCH_SIZE / 1024 / 1024 + 1

echo Created: %OUTPUT_DIR%\%OUTPUT_NAME%.7z (%ARCH_SIZE_MB% MB)
echo.
echo NOTE: Team members need 7-Zip to extract .7z files
echo       .7z extracts much faster than .zip for many small files
goto :after_archive

:create_zip
echo 7-Zip not found. Using PowerShell for ZIP compression...
echo Install 7-Zip from https://www.7-zip.org/ for faster extraction

powershell -command "Compress-Archive -Path '%TEMP_DIR%\*' -DestinationPath '%OUTPUT_DIR%\%OUTPUT_NAME%.zip' -CompressionLevel Optimal" >nul 2>&1

:: Get file size
for %%f in ("%OUTPUT_DIR%\%OUTPUT_NAME%.zip") do set ZIP_SIZE=%%~zf
set /a ZIP_SIZE_MB=ZIP_SIZE / 1024 / 1024 + 1

echo Created: %OUTPUT_DIR%\%OUTPUT_NAME%.zip (%ZIP_SIZE_MB% MB)
echo.
echo NOTE: Install 7-Zip for .7z format - much faster extraction

:after_archive

:: -----------------------------------------------------------------------------
:: CLEANUP
:: -----------------------------------------------------------------------------

:: Remove temporary directory
rd /s /q "%TEMP_DIR%" 2>nul

:: -----------------------------------------------------------------------------
:: FINAL OUTPUT
:: -----------------------------------------------------------------------------

echo.
echo ============================================
echo  Optimization Complete!
echo ============================================
echo.
echo Your optimized report is ready at:
echo   %OPTIMIZED_REPORT_DIR%  (unpacked, open directly in a browser)
echo   %OUTPUT_DIR%\%OUTPUT_NAME%.7z  (or .zip)  (archive for sharing)
echo.
echo To share with your team:
echo   1. Send the archive file above
echo   2. Team extracts with 7-Zip (for .7z) or built-in tools (for .zip)
echo   3. Open the extracted folder in a browser
echo.
echo To verify before sharing:
echo   1. Open %OPTIMIZED_REPORT_DIR% directly in a browser
echo   2. Confirm the report loads and displays all test results
echo   3. Extract the archive to a test folder and confirm it works too
