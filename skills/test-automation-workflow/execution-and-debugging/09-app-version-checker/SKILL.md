---
name: browserstack-app-checker
description: Use when checking BrowserStack for the latest uploaded eudi-wallet-ios and eudi-wallet-android apps and comparing their bs:// app URLs with the values in env.properties, updating env.properties when a new build has been uploaded. Triggers on "check latest bs", "update app url", "new app build", "browserstack latest app", "is the app up to date".
---

# BrowserStack App Checker

Check whether the `bs://` app IDs in `env.properties` still point to the latest builds uploaded to BrowserStack, and update them if a newer eudi-wallet-ios / eudi-wallet-android build exists.

Repo root: `C:\Users\ftheofil\Projects\eu-digital-identity-walleteudi-doc-testing-application-internal`

The canonical way to run this check is the script:

```
python scripts/update-latest-browserstack-apps.py
```

It fetches the latest builds, updates `env.properties` when newer builds exist, and prints a per-build report. It manages four keys (selected at test time via `app.environment=dev|prod` in `env.properties` or `-Dapp.environment`):

- `browserstack.android.appUrl.dev`  → latest Dev  Android build (custom_id `eudi-wallet-android`, file name contains `-Dev.apk`)
- `browserstack.android.appUrl.prod` → latest Demo Android build (file name contains `-Demo.apk`)
- `browserstack.ios.appUrl.dev`      → latest Dev  iOS build (custom_id `eudi-wallet-ios`, file name contains `-Dev.ipa`)
- `browserstack.ios.appUrl.prod`     → latest Demo iOS build (file name contains `-Demo.ipa`)

The script reports a required (Dev) build as missing with exit code 3; a missing optional (Demo) build is reported as a warning but is not an error (the Demo builds are not uploaded to BrowserStack yet).

## 1. Get BrowserStack credentials

Read them from `browserstack-local.properties` (repo root):

- `browserstack.general.username`
- `browserstack.general.accessKey`

If missing, fall back to env vars `BROWSERSTACK_USERNAME` / `BROWSERSTACK_ACCESS_KEY`.

**Never print the username/access key.** Only the `app_url` values and app names may be shown.

## 2. Fetch the recent apps from BrowserStack

Run in PowerShell from the repo root (Windows):

```powershell
$props = @{}
Get-Content browserstack-local.properties -ErrorAction SilentlyContinue | ForEach-Object {
  if ($_ -match '^\s*([^#=]+?)\s*=\s*(.*?)\s*$') { $props[$matches[1].Trim()] = $matches[2].Trim() }
}
$u = $props['browserstack.general.username']; if (-not $u) { $u = $env:BROWSERSTACK_USERNAME }
$k = $props['browserstack.general.accessKey']; if (-not $k) { $k = $env:BROWSERSTACK_ACCESS_KEY }
$headers = @{ Authorization = "Basic " + [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("$u`:$k")) }
$apps = Invoke-RestMethod -Uri "https://api-cloud.browserstack.com/app-automate/recent_apps" -Headers $headers
$apps | Where-Object { $_.custom_id -eq 'eudi-wallet-ios' } | Sort-Object uploaded_at -Descending | Select-Object -First 1 | Format-List custom_id, file_name, app_url, uploaded_at
$apps | Where-Object { $_.custom_id -eq 'eudi-wallet-android' } | Sort-Object uploaded_at -Descending | Select-Object -First 1 | Format-List custom_id, file_name, app_url, uploaded_at
```

Notes:
- `recent_apps` returns apps newest-first; still sort by `uploaded_at` descending to be safe and take the first match.
- The API only reports `file_name` for apps that were uploaded with a file name. The current Dev builds expose only `custom_id`; the Demo builds (named `-Demo.apk` / `-Demo.ipa`) are not uploaded yet.
- If the API call fails or returns nothing, report it and **do not touch** `env.properties`.

## 3. Read the current values in env.properties

Keys in `env.properties` (repo root):

- `browserstack.android.appUrl.dev` / `browserstack.android.appUrl.prod` (used by `EnvDataConfig.getAppiumBrowserstackAndroidAppUrl()` → `appium:app` in `MobileWebDriverFactory`, resolved via `app.environment` suffix)
- `browserstack.ios.appUrl.dev` / `browserstack.ios.appUrl.prod` (used by `EnvDataConfig.getAppiumBrowserstackIosAppUrl()`)

Compare each current `bs://...` value with the latest `app_url` fetched in step 2.

## 4. Update only when different

- **Same value** → do nothing, report "already up to date" for that platform.
- **Different value** → edit `env.properties` with the Edit tool, replacing only the `bs://...` value on the matching `browserstack.<platform>.appUrl.<dev|prod>=` line. Preserve everything else exactly (comment lines like `#dev`/`#demo`, other keys, formatting, line endings).
- **New value found but key missing** → add the missing `browserstack.<platform>.appUrl.<dev|prod>=<bs://...>` line in the correct section (Android section lines ~52-62, iOS section lines ~64-70).

## 5. Report

Output per build:

```
### Android Dev / Android Demo / iOS Dev / iOS Demo
BrowserStack latest: <app_url>  (v<app_version>, uploaded <uploaded_at>)
env.properties:     <old value> → <new value>   (or: unchanged)
Action: updated / no change / no matching build on BrowserStack
```

If a build had no matching app on BrowserStack, state that explicitly.
