<#
.SYNOPSIS
    Copies all files from multiple report folders into one folder, overwriting duplicates.

.DESCRIPTION
    Copies every file from two or more report directories into a single output folder.
    If the same filename appears in more than one report, the later report's file
    replaces (overwrites) the earlier one. Then (optionally) copies all merged files
    into a "current" report folder, overwriting its files, so the current report
    contains everything.

.PARAMETER ReportDirs
    One or more report directories to copy.

.PARAMETER OutputDir
    Destination folder for the combined report. Default: target/site/reports/Merged_Report

.PARAMETER CopyTo
    Optional folder (e.g. the current report, "EUDI_Wallet_Version_2026.10.43-Demo")
    that receives all merged files, overwriting existing ones.

.EXAMPLE
    .\merge-reports.ps1 -ReportDirs "report1", "report2"

.EXAMPLE
    .\merge-reports.ps1 -ReportDirs "report1", "report2" -OutputDir "target\site\reports\Merged_Report" -CopyTo "target\site\reports\EUDI_Wallet_Version_2026.10.43-Demo"
#>
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
