---
name: merge-serenity-reports
description: >
  Merge multiple Serenity BDD test report folders into a single combined report folder.
  Copies all files from two or more report directories into one folder; if a file with the
  same name already exists, it is overwritten/replaced by the later report's file. Can also
  copy the merged result into the "current" report folder (e.g. EUDI_Wallet_Version_2026.10.43-Demo)
  so that folder contains everything. Triggers on "merge reports", "combine reports",
  "copy reports into one", "merge report folders", "combine test report content".
version: 2.0.0
tags: [serenity, reports, merge, combine, consolidate, results]
---

# Skill: Merge Multiple Reports into One Folder

> **Purpose:** Combine the content of two or more report folders into a single folder.
> Every file is copied over; if a filename already exists, the newer copy **replaces** it.
>
> **When to use:** When you have several report directories (e.g. `target/site/reports/`
> contains multiple `EUDI_Wallet_Version_*` folders) and you want all their content in
> one place.

---

## Behavior

1. All files from the first report are copied into the output folder.
2. Files from each following report are copied too.
3. If a file with the same name already exists, the incoming file **overwrites** it.
4. Subfolders are copied recursively (asset folders like `bootstrap/`, `css/`, etc. merge).
5. If `-CopyTo` is given, the merged content is also copied into that folder (e.g. the
   "current" report folder like `EUDI_Wallet_Version_2026.10.43-Demo`), overwriting its
   files — so the current report ends up containing everything.

The output defaults to `target/site/reports/Merged_Report`.

---

## Implementation

Use the included PowerShell script: `scripts/merge-reports.ps1`

```powershell
[CmdletBinding()]
param(
    [Parameter(Mandatory = $true, Position = 0)]
    [string[]]$ReportDirs,

    [Parameter(Position = 1)]
    [string]$OutputDir = "target\site\reports\Merged_Report",

    [Parameter(Position = 2)]
    [string]$CopyTo
)

$ErrorActionPreference = "Stop"

if ($ReportDirs.Count -lt 1) {
    throw "Please provide at least one report directory to copy."
}

$resolvedDirs = @()
foreach ($dir in $ReportDirs) {
    if ([System.IO.Path]::IsPathRooted($dir)) {
        $full = $dir
    } else {
        $full = [System.IO.Path]::GetFullPath((Join-Path (Get-Location) $dir))
    }
    if (-not (Test-Path -LiteralPath $full -PathType Container)) {
        throw "Report directory not found: $full"
    }
    $resolvedDirs += $full
}

if ([System.IO.Path]::IsPathRooted($OutputDir)) {
    $outFull = [System.IO.Path]::GetFullPath($OutputDir)
} else {
    $outFull = [System.IO.Path]::GetFullPath((Join-Path (Get-Location) $OutputDir))
}

$copyToFull = $null
if ($CopyTo) {
    if ([System.IO.Path]::IsPathRooted($CopyTo)) {
        $copyToFull = [System.IO.Path]::GetFullPath($CopyTo)
    } else {
        $copyToFull = [System.IO.Path]::GetFullPath((Join-Path (Get-Location) $CopyTo))
    }
    if (-not (Test-Path -LiteralPath $copyToFull -PathType Container)) {
        throw "CopyTo folder not found: $copyToFull"
    }
}

Write-Host ""
Write-Host "============================================"
Write-Host "  Report Merger (copy + overwrite)"
Write-Host "============================================"
Write-Host "  Reports: $($resolvedDirs.Count)"
for ($i = 0; $i -lt $resolvedDirs.Count; $i++) {
    Write-Host "    $($i + 1). $($resolvedDirs[$i])"
}
Write-Host "  Output:  $outFull"
if ($copyToFull) {
    Write-Host "  CopyTo:  $copyToFull"
}
Write-Host ""

if (-not (Test-Path -LiteralPath $outFull)) {
    New-Item -ItemType Directory -Path $outFull -Force | Out-Null
}

$copiedCount = 0
foreach ($dir in $resolvedDirs) {
    Write-Host "Copying: $dir"
    robocopy $dir $outFull /E /NFL /NDL /NJH /NJS /R:1 /W:1 | Out-Null
    $copiedCount++
}

if ($copyToFull) {
    Write-Host ""
    Write-Host "Copying merged files into current report folder: $copyToFull"
    robocopy $outFull $copyToFull /E /NFL /NDL /NJH /NJS /R:1 /W:1 | Out-Null
}

Write-Host ""
Write-Host "============================================"
Write-Host "  Complete! All files copied to:"
Write-Host "  $outFull"
if ($copyToFull) {
    Write-Host "  and into: $copyToFull"
}
Write-Host "  (same-name files were overwritten by later reports)"
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

# Merge AND copy the result into the "current" report folder (overwrites its files)
.\scripts\merge-reports.ps1 `
  -ReportDirs "target\site\reports\EUDI_Wallet_Version_2026.10.43-Demo", `
              "target\site\reports\android-serenity-report(1)\EUDI_Wallet_Version_2025.10.31-Demo" `
  -CopyTo "target\site\reports\EUDI_Wallet_Version_2026.10.43-Demo"
```

> **Note for automation / agents:** when calling the script through a nested
> `powershell -Command` (instead of a `powershell -File`), bind the array explicitly:

```powershell
powershell -Command "& 'skills\test-automation-workflow\07-results-and-defects\13-report-merger\scripts\merge-reports.ps1' -ReportDirs @('target\site\reports\EUDI_Wallet_Version_2026.10.43-Demo','target\site\reports\android-serenity-report(1)\EUDI_Wallet_Version_2025.10.31-Demo') -CopyTo 'target\site\reports\EUDI_Wallet_Version_2026.10.43-Demo'"
```

### Notes on the arguments:

| Argument | Purpose |
|----------|---------|
| `-ReportDirs` | **Required.** One or more report folder paths. |
| `-OutputDir` | Optional. Defaults to `target/site/reports/Merged_Report`. |
| `-CopyTo` | Optional. Folder (e.g. the current report `EUDI_Wallet_Version_2026.10.43-Demo`) that receives all merged files, overwriting existing ones. |

---

## What Happens During the Merge

1. All files from each report (including subfolders) are copied into the output folder.
2. Later reports overwrite earlier files with the same name.
3. Everything else is just added.
4. If `-CopyTo` is given, the whole merged folder is copied into that report folder too,
   replacing its files. The current report then contains all reports' content.

---

## Verification Checklist

After the merge, verify the output:

1. [ ] `target/site/reports/Merged_Report/index.html` exists
2. [ ] Opening `index.html` in a browser shows the report
3. [ ] Test `.html` / `.json` / `.csv` files from ALL reports are present
4. [ ] Asset folders (`bootstrap/`, `css/`, `chartjs/`) exist
5. [ ] If `-CopyTo` was used, the target report folder has the same file count as the merged folder
6. [ ] No copy errors in the output

---

## Troubleshooting

### "Report directory not found"

Double-check the path - the script resolves paths relative to the current directory.
Use absolute paths or paths relative to the repo root.

### Output folder already exists

Files are merged in. Existing files with the same name as an incoming file are replaced.

### `ReportDirs` array not binding when invoked with `powershell -File`

`-File` mode does not bind comma-separated arrays reliably. Use the `-Command` form shown
in the Usage section (wrap the paths in `@(...)`).

### `-CopyTo` folder not found

The folder must already exist (the script does not create it). Point it at an existing
report folder such as `target/site/reports/EUDI_Wallet_Version_2026.10.43-Demo`.

---

## Target

> **Goal:** Copy all files from multiple report folders into one folder (and optionally into
> the current report folder), replacing any same-named files with the latest copy.
