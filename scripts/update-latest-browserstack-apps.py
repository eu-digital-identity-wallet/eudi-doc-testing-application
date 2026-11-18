#!/usr/bin/env python3
"""Fetch the latest eudi-wallet Android/iOS builds from BrowserStack and update
the bs:// app URLs in env.properties.

Four keys are maintained (selected at test time via app.environment=dev|prod in
env.properties or -Dapp.environment on the Maven command line):

  browserstack.android.appUrl.dev  <- latest Dev  Android build
  browserstack.android.appUrl.prod <- latest Demo Android build
  browserstack.ios.appUrl.dev      <- latest Dev  iOS build
  browserstack.ios.appUrl.prod     <- latest Demo iOS build

Matching rules against the BrowserStack recent_apps API:

  Dev  builds: custom_id must equal the wallet custom id
               AND (file name contains "-Dev.apk"/"-Dev.ipa" when a file name
               is reported by the API; older uploads report no file name and
               are matched by custom_id alone).
  Demo builds: file name must contain "-Demo.apk"/"-Demo.ipa".

Credentials are read from browserstack-local.properties (repo root) or, when that
file is missing, from the BROWSERSTACK_USERNAME / BROWSERSTACK_ACCESS_KEY env vars
(used by the GitHub Actions cron workflow).

Exit codes:
  0  success (updated or already up to date)
  2  API/network error
  3  a required (Dev) build could not be found
"""

import base64
import json
import os
import sys
import urllib.request

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
ENV_FILE = os.path.join(ROOT, "env.properties")
BS_LOCAL_FILE = os.path.join(ROOT, "browserstack-local.properties")
RECENT_APPS_URL = "https://api-cloud.browserstack.com/app-automate/recent_apps?limit=50"

TARGETS = [
    {
        "label": "Android Dev",
        "key": "browserstack.android.appUrl.dev",
        "custom_id": "eudi-wallet-android",
        "file_marker": "-Dev.apk",
        "required": True,
    },
    {
        "label": "Android Demo",
        "key": "browserstack.android.appUrl.prod",
        "custom_id": None,
        "file_marker": "-Demo.apk",
        "required": False,
    },
    {
        "label": "iOS Dev",
        "key": "browserstack.ios.appUrl.dev",
        "custom_id": "eudi-wallet-ios",
        "file_marker": "-Dev.ipa",
        "required": True,
    },
    {
        "label": "iOS Demo",
        "key": "browserstack.ios.appUrl.prod",
        "custom_id": None,
        "file_marker": "-Demo.ipa",
        "required": False,
    },
]


def read_credentials():
    creds = {}
    if os.path.exists(BS_LOCAL_FILE):
        with open(BS_LOCAL_FILE, encoding="utf-8", errors="replace") as f:
            for line in f:
                line = line.strip()
                if not line or line.startswith("#") or "=" not in line:
                    continue
                key, _, value = line.partition("=")
                creds[key.strip()] = value.strip()
    username = creds.get("browserstack.general.username") or os.environ.get("BROWSERSTACK_USERNAME")
    access_key = creds.get("browserstack.general.accessKey") or os.environ.get("BROWSERSTACK_ACCESS_KEY")
    if not username or not access_key:
        print("ERROR: BrowserStack credentials not found in browserstack-local.properties "
              "or BROWSERSTACK_USERNAME/BROWSERSTACK_ACCESS_KEY env vars.")
        sys.exit(2)
    return username, access_key


def fetch_recent_apps(username, access_key):
    auth = base64.b64encode(f"{username}:{access_key}".encode("ascii")).decode("ascii")
    req = urllib.request.Request(RECENT_APPS_URL, headers={"Authorization": f"Basic {auth}"})
    with urllib.request.urlopen(req, timeout=60) as resp:
        return json.loads(resp.read().decode("utf-8"))


def matches(app, target):
    custom_id = target["custom_id"]
    file_marker = target["file_marker"]

    if custom_id:
        if (app.get("custom_id") or "").lower() != custom_id.lower():
            return False
        file_name = app.get("file_name") or ""
        if file_name and file_marker and file_marker not in file_name:
            return False
        return True

    if file_marker:
        file_name = app.get("file_name") or ""
        if not file_name:
            return False
        return file_marker in file_name

    return False


def pick_latest(apps, target):
    matches_list = [a for a in apps if matches(a, target)]
    if not matches_list:
        return None
    matches_list.sort(key=lambda a: a.get("uploaded_at") or "", reverse=True)
    return matches_list[0]


def read_env_lines():
    with open(ENV_FILE, encoding="utf-8", errors="replace") as f:
        content = f.read()
    if "\r\n" in content:
        newline = "\r\n"
        lines = content.split("\r\n")
    else:
        newline = "\n"
        lines = content.split("\n")
    return lines, newline


def write_env_lines(lines, newline):
    with open(ENV_FILE, "w", encoding="utf-8", newline="") as f:
        f.write(newline.join(lines))


def replace_key(lines, key, new_value):
    for i, line in enumerate(lines):
        if line.startswith("#") or "=" not in line:
            continue
        lhs, _, _ = line.partition("=")
        if lhs.strip() == key:
            lines[i] = f"{lhs.strip()}={new_value}"
            return True
    return False


def main():
    if len(sys.argv) > 1:
        global ENV_FILE
        ENV_FILE = os.path.abspath(sys.argv[1])

    username, access_key = read_credentials()
    try:
        apps = fetch_recent_apps(username, access_key)
    except Exception as exc:  # noqa: BLE001
        print(f"ERROR: failed to fetch apps from BrowserStack: {exc}")
        sys.exit(2)

    if not isinstance(apps, list) or not apps:
        print("ERROR: no apps returned from BrowserStack API.")
        sys.exit(2)

    lines, newline = read_env_lines()
    changed = False
    missing_required = False

    for target in TARGETS:
        latest = pick_latest(apps, target)
        if latest is None:
            note = "required" if target["required"] else "optional"
            print(f"{target['label']}: no matching build found (custom_id={target['custom_id'] or '-'}, "
                  f"file contains {target['file_marker']!r}) - {note} build.")
            if target["required"]:
                missing_required = True
            continue

        new_url = latest.get("app_url", "")
        uploaded = latest.get("uploaded_at") or ""
        version = latest.get("app_version") or "unknown"

        current = None
        for line in lines:
            if line.startswith("#") or "=" not in line:
                continue
            lhs, _, rhs = line.partition("=")
            if lhs.strip() == target["key"]:
                current = rhs.strip()
                break

        if current is None:
            print(f"{target['label']}: key {target['key']} not found in env.properties, adding it.")
            lines.append(f"{target['key']}={new_url}")
            changed = True
        elif current == new_url:
            print(f"{target['label']}: already up to date ({current}, v{version}, uploaded {uploaded}).")
        else:
            print(f"{target['label']}: updating {current or '(empty)'} -> {new_url} "
                  f"(v{version}, uploaded {uploaded}).")
            replace_key(lines, target["key"], new_url)
            changed = True

    if changed:
        write_env_lines(lines, newline)
        print("env.properties updated.")

    if missing_required:
        sys.exit(3)
    sys.exit(0)


if __name__ == "__main__":
    main()
