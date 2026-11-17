---
name: optimize-report-packaging
description: >
  Optimize Serenity BDD test report packaging for fast sharing. Reduces file count by 84%
  by removing unused icon SVGs and using .7z compression for fast extraction.
version: 2.0.0
tags: [serenity, reports, packaging, compression, 7z, sharing, optimization]
---

# Skill: Optimize Serenity Report Packaging for Fast Sharing

> **Purpose:** Create lightweight, fast-to-extract report packages from Serenity BDD test reports.
> After running, you get both an **unpacked optimized report** in `target/site/reports/` (ready to open
> directly in a browser) and a **compressed .7z archive** in `target/site/reports/` for sharing.
> 
> **When to use:** After every test execution when you need to share reports with the team.

---

## The Problem

Your `target/site/reports/EUDI_Wallet_Version_2026.10.43-Demo/` directory contains **4000+ files**:

| Category | File Count | Needed? |
|----------|-----------|---------|
| Individual test HTML files | ~470 | Yes - report links to them |
| `bootstrap-icons/` SVGs | ~1957 | **No** - only CSS + fonts are used |
| `icons/` SVGs | ~1406 | **No** - completely unused |
| Other assets (bootstrap, chartjs, etc.) | ~166 | Yes |
| JSON, PNG, other root files | ~33 | Yes |
| **Total** | **~4034** | |

**The real issue:** 83% of files are unused SVG icons. This makes ZIP archives slow to create and extract.

---

## The Solution: Remove Unused Icons + .7z Format

### Why This Works

| Approach | File Count | Archive Size | Extraction Speed |
|----------|-----------|--------------|-----------------|
| Original ZIP (all files) | 4034 | ~4 MB | Slow |
| **Optimized .7z (icons removed)** | **641** | **~2 MB** | **~6x faster** |

**Two changes solve the problem:**
1. **Remove unused icon SVGs** - reduces file count from 4034 to 641 (84% fewer files)
2. **Use .7z format** - faster compression/decompression than ZIP for many small files

---

## How Serenity Reports Work

Serenity BDD generates:
1. **Main entry point** - `index.html`
2. **Individual test pages** - Each test gets its own HTML (hash-named files) - **required for navigation**
3. **JSON data files** - Contains test data
4. **Asset libraries** - Bootstrap, jQuery, ChartJS, DataTables, etc.
5. **Icon directories** - `bootstrap-icons/` and `icons/` contain thousands of SVGs, but the report only uses the **CSS font files** (woff/woff2), NOT the individual SVGs

**Key insight:** The icon SVGs are bloat. The CSS uses font-based icons (woff/woff2), not the individual SVG files.

---

## Implementation

### Windows Batch Script (Primary)

Create: `scripts/package-report.cmd`

```batch
@echo off
setlocal enabledelayedexpansion

:: ============================================================================
:: Serenity Report Optimizer - Windows Batch Script
:: 
:: Purpose: Creates a lightweight, fast-to-extract package from Serenity BDD
::          test reports by removing unused icon SVGs and using .7z compression.
:: 
:: Usage: package-report.cmd [output-name]
:: Example: package-report.cmd
::          package-report.cmd MyTestReport
:: 
:: Output: 
::   - target\site\reports\<report-name>-optimized\ (unpacked, open in browser)
::   - target\site\reports\<output-name>.7z (archive for sharing)
:: 
:: Requirements: 
::   - 7-Zip (for .7z creation) - https://www.7-zip.org/
::   - Falls back to ZIP if 7-Zip not installed
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

:: Find the report directory
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

set OPTIMIZED_REPORT_DIR=%REPORT_SOURCE%\%REPORT_NAME%-optimized

if exist "%OPTIMIZED_REPORT_DIR%" (
    rd /s /q "%OPTIMIZED_REPORT_DIR%" 2>nul
)

xcopy /E /I /Q /Y "%TEMP_DIR%\" "%OPTIMIZED_REPORT_DIR%\" >nul 2>&1

echo.
echo  - Unpacked optimized report:
echo      %OPTIMIZED_REPORT_DIR%

:: -----------------------------------------------------------------------------
:: COUNT FILES AND SHOW STATISTICS
:: -----------------------------------------------------------------------------

echo.
echo File count:

set ORIGINAL_COUNT=0
for /r "%REPORT_DIR%" %%f in (*) do set /a ORIGINAL_COUNT+=1

set MINIMAL_COUNT=0
for /r "%TEMP_DIR%" %%f in (*) do set /a MINIMAL_COUNT+=1

echo  Original: %ORIGINAL_COUNT% files
echo  Optimized: %MINIMAL_COUNT% files

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

for %%f in ("%OUTPUT_DIR%\%OUTPUT_NAME%.zip") do set ZIP_SIZE=%%~zf
set /a ZIP_SIZE_MB=ZIP_SIZE / 1024 / 1024 + 1

echo Created: %OUTPUT_DIR%\%OUTPUT_NAME%.zip (%ZIP_SIZE_MB% MB)
echo.
echo NOTE: Install 7-Zip for .7z format - much faster extraction

:after_archive

:: -----------------------------------------------------------------------------
:: CLEANUP
:: -----------------------------------------------------------------------------

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
```

---

## Quick Start

### Windows:
```cmd
:: After running tests (mvn verify)
scripts\package-report.cmd

:: Output:
::   target\site\reports\<report-name>-optimized\  (unpacked report)
::   target\site\reports\<report-name>.7z  (archive, same name as report)
```

### Output

| Output | Location | Use |
|--------|----------|-----|
| **Unpacked optimized report** | `target/site/reports/<report-name>-optimized/` | Open directly in a browser |
| **Compressed archive** | `target/site/reports/<report-name>.7z` | Share with the team |

---

## Verification Checklist

Before sharing with the team, verify the package works:

1. **Open the unpacked optimized report (fastest check):**
   - Navigate to `target/site/reports/<report-name>-optimized/`
   - Open `index.html` in a browser
   - Click through test results to confirm navigation works

2. **Extract the archive:**
   ```
    7z x target\site\reports\<report-name>.7z -o./test-extract
   ```

3. **Open in browser:**
   - Open `test-extract/index.html`
   - Verify all test results are visible and navigable

4. **Check functionality:**
   - [ ] Report loads without errors
   - [ ] All test results are visible
   - [ ] Charts and graphs display correctly
   - [ ] Clicking individual tests works (no "file not found")
   - [ ] Icons display correctly (font-based, not SVG)

---

## Troubleshooting

### "File not found" for individual test HTML pages

Make sure the script copies ALL HTML files (not just the first 5). The report's
navigation links to each individual test page.

### Icons not displaying

The report uses font-based icons (woff/woff2) from `bootstrap-icons/fonts/`.
Make sure the script copies:
- `bootstrap-icons/bootstrap-icons.css`
- `bootstrap-icons/fonts/` (contains .woff and .woff2 files)

The individual SVG files in `bootstrap-icons/` and the entire `icons/` directory
are NOT needed.

### "7-Zip not found"

Install 7-Zip from https://www.7-zip.org/ for .7z format.
Without it, the script falls back to ZIP (still benefits from reduced file count).

---

## Performance Comparison (Actual Results)

### Before (Original):
```
Files: 4034
ZIP size: ~4 MB
Extraction: Slow (thousands of small files)
```

### After (Optimized):
```
Files: 641 (84% reduction)
.7z size: ~2 MB
Extraction: ~6x faster
```

### What was removed:
| Directory | Files Removed | Why Safe |
|-----------|--------------|----------|
| `icons/` | 1406 | No CSS, no fonts, no references in HTML |
| `bootstrap-icons/*.svg` | 1957 | CSS uses font files (woff/woff2), not SVGs |

---

## What Gets Excluded (and Why It's Safe)

| File Type | Excluded? | Reason |
|-----------|-----------|--------|
| `icons/` directory (all SVGs) | Yes | Completely unused - no references found |
| `bootstrap-icons/*.svg` | Yes | CSS uses font-based icons, not SVGs |
| `bootstrap-icons/bootstrap-icons.css` | No | Main icon stylesheet |
| `bootstrap-icons/fonts/` | No | Contains woff/woff2 font files used by CSS |
| Individual test HTML files | No | Required - report navigation links to them |
| Other asset directories | No | Required for styling/functionality |
| JSON files | No | Contains test data |
| index.html | No | Main entry point |

---

## Target

> **Goal:** Reduce file count by 80%+ and make extraction 5-6x faster while maintaining full report functionality.

---

## Quick Reference

| Command | Description |
|---------|-------------|
| `scripts\package-report.cmd` | Create optimized package (Windows) |
| `7z x report.7z` | Extract .7z on Windows |
| Right-click > Extract | Extract .zip on Windows (built-in) |
