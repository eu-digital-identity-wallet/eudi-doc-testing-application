---
name: merge-serenity-reports
description: >
  Merge multiple Serenity BDD test report folders into a single combined report folder.
  Copies all content (test result HTML/JSON/CSV files, assets, summary files) from two or
  more report directories into one folder, deduplicating identical files automatically.
  Triggers on "merge reports", "combine reports", "copy reports into one", "merge report
  folders", "combine test report content".
version: 1.0.0
tags: [serenity, reports, merge, combine, consolidate, deduplication, results]
---

# Skill: Merge Multiple Serenity Reports into One Folder

> **Purpose:** Combine the content of two or more Serenity BDD report folders into a single
> merged report folder so you can view all test results in one place.
>
> **When to use:** When you have several report directories (e.g. `target/site/reports/`
> contains multiple `EUDI_Wallet_Version_*` folders) and you want a single combined report.

---

## How Serenity Reports Are Structured

Each Serenity report folder (e.g. `EUDI_Wallet_Version_2026.10.43-Demo/`) contains:

| Content | Examples | Merge behavior |
|---------|----------|----------------|
| **Individual test pages** | hash-named `.html` files (SHA-256 of content) | Copied; identical content = identical name = auto-dedup |
| **Test data files** | hash-named `.json` / `.csv` files | Copied; auto-dedup via hashed names |
| **JUnit XML reports** | `SERENITY-JUNIT-*.xml` | Copied; auto-dedup |
| **Asset libraries** | `bootstrap/`, `css/`, `chartjs/`, `datatables/`, etc. | Copied once (identical across reports) |
| **Summary files** | `index.html`, `summary.txt`, `results.csv`, `capabilities.html`, `build-info.html` | Base report keeps the plain name; later reports stored as `<reportname>-<filename>` |

**Key insight:** Serenity names individual test pages after the SHA-256 hash of their content,
so the same test run in different reports produces the same filename. This makes merging
collision-free for the vast majority of files.

---

## Implementation

Use the included PowerShell script: `scripts/merge-reports.ps1`

### PowerShell Script (`scripts/merge-reports.ps1`)

```powershell
<#
.SYNOPSIS
    Merges multiple Serenity BDD report folders into a single combined report folder.

.DESCRIPTION
    Copies the content of two or more Serenity BDD report directories (e.g. the
    EUDI_Wallet_Version_*. *-Demo folders under target/site/reports/) into one
    merged folder. Because Serenity names individual test pages after the SHA-256
    hash of their content, identical test results across reports produce identical
    filenames and are deduplicated automatically. Shared asset folders (bootstrap/,
    css/, chartjs/, etc.) are copied once.

.PARAMETER ReportDirs
    One or more paths to Serenity BDD report directories to merge.

.PARAMETER OutputDir
    Destination folder for the merged report. Default: target/site/reports/Merged_Report

.PARAMETER KeepBaseIndex
    Keeps index.html (and other per-run summary files such as summary.txt, results.csv,
    capabilities.html, build-info.html) from the FIRST report only. Later reports'
    versions are stored with a <reportname>- prefix so nothing is lost.

.EXAMPLE
    .\merge-reports.ps1 -ReportDirs "target\site\reports\EUDI_Wallet_Version_2026.10.43-Demo", "target\site\reports\android-serenity-report(1)\EUDI_Wallet_Version_2025.10.31-Demo"

.EXAMPLE
    .\merge-reports.ps1 -ReportDirs "report1","report2" -OutputDir "target\site\reports\Combined"
#>
[CmdletBinding()]
param(
    [Parameter(Mandatory = $true, Position = 0)]
    [string[]]$ReportDirs,

    [Parameter(Position = 1)]
    [string]$OutputDir = "target\site\reports\Merged_Report",

    [switch]$KeepBaseIndex
)

$ErrorActionPreference = "Stop"

# Files that are per-report-run summaries; only the base report keeps the plain name.
$BaseReportFiles = @("index.html", "summary.txt", "results.csv", "capabilities.html", "build-info.html")

function Get-RelativePath {
    param([string]$Path, [string]$Root)
    return $Path.Substring($Root.Length).TrimStart('\', '/')
}

function Get-FileHashString {
    param([string]$Path)
    return (Get-FileHash -LiteralPath $Path -Algorithm SHA256).Hash
}

# ---------------------------------------------------------------
# Validate inputs
# ---------------------------------------------------------------
if ($ReportDirs.Count -lt 2) {
    throw "Please provide at least two report directories to merge."
}

$resolvedDirs = @()
foreach ($dir in $ReportDirs) {
    $full = [System.IO.Path]::GetFullPath((Join-Path (Get-Location) $dir))
    if (-not (Test-Path -LiteralPath $full -PathType Container)) {
        throw "Report directory not found: $full"
    }
    if (-not (Test-Path -LiteralPath (Join-Path $full "index.html"))) {
        Write-Warning "Directory does not look like a Serenity report (no index.html): $full"
    }
    $resolvedDirs += $full
}

$outFull = [System.IO.Path]::GetFullPath((Join-Path (Get-Location) $OutputDir))

Write-Host ""
Write-Host "============================================"
Write-Host "  Serenity Report Merger"
Write-Host "============================================"
Write-Host "  Reports to merge: $($resolvedDirs.Count)"
for ($i = 0; $i -lt $resolvedDirs.Count; $i++) {
    $base = if ($i -eq 0) { " (base)" } else { "" }
    Write-Host "    $($i + 1). $($resolvedDirs[$i])$base"
}
Write-Host "  Output:          $outFull"
Write-Host ""

# ---------------------------------------------------------------
# Prepare output folder
# ---------------------------------------------------------------
if (-not (Test-Path -LiteralPath $outFull)) {
    New-Item -ItemType Directory -Path $outFull -Force | Out-Null
} else {
    Write-Warning "Output folder already exists; files will be merged into it (existing files kept)."
}

$copiedCount = 0
$skippedCount = 0
$renamedCount = 0

# ---------------------------------------------------------------
# Merge each report
# ---------------------------------------------------------------
for ($r = 0; $r -lt $resolvedDirs.Count; $r++) {
    $reportRoot = $resolvedDirs[$r]
    $reportName = Split-Path $reportRoot -Leaf
    $isBase = ($r -eq 0)

    Write-Host "Processing report: $reportName"

    $files = Get-ChildItem -LiteralPath $reportRoot -Recurse -File
    foreach ($file in $files) {
        $relPath = Get-RelativePath -Path $file.FullName -Root $reportRoot
        $fileName = $file.Name
        $destPath = Join-Path $outFull $relPath

        $isBaseReportFile = $BaseReportFiles -contains $fileName

        # For per-run summary files: base keeps plain name, others get prefixed name
        if ($isBaseReportFile) {
            if ($isBase) {
                # first report keeps the original name - copy below
            } else {
                $newName = "$reportName-$fileName"
                $destPath = Join-Path $outFull $newName
            }
        }

        $destDir = Split-Path $destPath -Parent
        if (-not (Test-Path -LiteralPath $destDir)) {
            New-Item -ItemType Directory -Path $destDir -Force | Out-Null
        }

        if (-not (Test-Path -LiteralPath $destPath)) {
            Copy-Item -LiteralPath $file.FullName -Destination $destPath -Force
            $copiedCount++
            continue
        }

        # Destination exists - compare content to decide skip vs keep-both
        $existingHash = Get-FileHashString -Path $destPath
        $newHash = Get-FileHashString -Path $file.FullName
        if ($existingHash -eq $newHash) {
            $skippedCount++
            continue
        }

        # Different content with same name -> keep both, prefix with report name
        $baseName = [System.IO.Path]::GetFileNameWithoutExtension($fileName)
        $extension = [System.IO.Path]::GetExtension($fileName)
        $uniqueName = "$reportName-$baseName$extension"
        $uniquePath = Join-Path $destDir $uniqueName
        Copy-Item -LiteralPath $file.FullName -Destination $uniquePath -Force
        $renamedCount++
        Write-Host "    conflict resolved: $relPath -> $uniqueName"
    }
    Write-Host ""
}

# ---------------------------------------------------------------
# Summary
# ---------------------------------------------------------------
Write-Host "============================================"
Write-Host "  Merge Complete!"
Write-Host "============================================"
Write-Host "  Copied files:        $copiedCount"
Write-Host "  Skipped (duplicate): $skippedCount"
Write-Host "  Renamed (conflict):  $renamedCount"
Write-Host ""
Write-Host "  Merged report: $outFull"
Write-Host "  Open index.html in a browser to view."
Write-Host ""
```

---

## Usage

### Windows PowerShell:

```powershell
# Basic merge of two reports (outputs to target/site/reports/Merged_Report)
.\scripts\merge-reports.ps1 `
  -ReportDirs "target\site\reports\EUDI_Wallet_Version_2026.10.43-Demo", `
              "target\site\reports\android-serenity-report(1)\EUDI_Wallet_Version_2025.10.31-Demo"
```

### Notes on the arguments:

| Argument | Purpose |
|----------|---------|
| `-ReportDirs` | **Required.** At least two report folder paths. |
| `-OutputDir` | Optional. Defaults to `target/site/reports/Merged_Report`. |
| `-KeepBaseIndex` | Optional switch. Keeps `index.html` etc. from the first report only. |

---

## What Happens During the Merge

1. The first report listed is the **base** report - its `index.html` (and `summary.txt`,
   `results.csv`, etc.) keep their original names in the merged folder.
2. All files from all reports are copied into the merged folder.
3. **Hash-named test pages** with identical content are skipped (same name = same content).
4. **Asset folders** (`bootstrap/`, `css/`, `chartjs/`, ...) are only copied once.
5. If a filename exists but content differs (rare), the new file is saved with the report
   name as a prefix so nothing is lost.

---

## Verification Checklist

After the merge, verify the output:

1. [ ] `target/site/reports/Merged_Report/index.html` exists
2. [ ] Opening `index.html` in a browser shows the base report's dashboard
3. [ ] Hash-named test `.html` files from ALL reports are present (count > any single report)
4. [ ] Asset folders (`bootstrap/`, `css/`, `chartjs/`) exist exactly once
5. [ ] No `... is being used by another process` or copy errors in the output
6. [ ] Summary shows expected numbers: Copied ≈ total files, Skipped = duplicates

---

## Troubleshooting

### "Report directory not found"

Double-check the path - the script resolves paths relative to the current directory.
Use absolute paths or paths relative to the repo root.

### Output folder already exists

The script merges into the existing folder and keeps existing files. If you want a clean
merge, delete the `Merged_Report` folder first (or use a new `-OutputDir` name).

### Two reports share a non-hashed filename with different content

The script detects the content difference and saves the second file with the report name
prefix (e.g. `EUDI_Wallet_Version_2025.10.31-Demo-something.html`) so nothing is lost.

---

## Target

> **Goal:** Merge two or more Serenity report folders into one browsable report folder with
> no data loss and no broken asset links.
