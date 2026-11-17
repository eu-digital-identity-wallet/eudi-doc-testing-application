#!/usr/bin/env python3
"""Fetches the latest TestFlight build (marketing version + build number) via the App Store Connect API.

Prerequisites:
  - pip install pyjwt
  - An App Store Connect API key (role: Admin or App Manager):
      https://appstoreconnect.apple.com/access/api  ->  "Keys" -> generate
      Keep the downloaded .p8 file, plus the Issuer ID and Key ID.

Usage:
  python scripts/testflight-latest-version.py --bundle-id eu.europa.ec.eudiwallet
  python scripts/testflight-latest-version.py --app-id 1234567890

Credentials can also be provided via env vars (ASC_ISSUER_ID, ASC_KEY_ID, ASC_PRIVATE_KEY)
or flags (--issuer-id, --key-id, --private-key).
"""
import argparse
import json
import os
import time
import urllib.parse
import urllib.request

import jwt

API_BASE = "https://api.appstoreconnect.com/v1"


def get_args():
    p = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    p.add_argument("--issuer-id", default=os.environ.get("ASC_ISSUER_ID"), help="App Store Connect Issuer ID")
    p.add_argument("--key-id", default=os.environ.get("ASC_KEY_ID"), help="App Store Connect API Key ID")
    p.add_argument("--private-key", default=os.environ.get("ASC_PRIVATE_KEY"), help="Path to the downloaded .p8 key file")
    p.add_argument("--bundle-id", help="iOS bundle identifier, e.g. eu.europa.ec.eudiwallet")
    p.add_argument("--app-id", help="Numeric App Store Connect app Apple ID")
    return p.parse_args()


def make_token(issuer_id, key_id, private_key_path):
    with open(private_key_path, "rb") as f:
        pem = f.read()
    now = int(time.time())
    payload = {
        "iss": issuer_id,
        "iat": now,
        "exp": now + 1199,  # max allowed lifetime is 20 minutes
        "aud": "appstoreconnect-v1",
    }
    return jwt.encode(payload, pem, algorithm="ES256", headers={"kid": key_id})


def api_get(path, token):
    req = urllib.request.Request(
        API_BASE + path,
        headers={"Authorization": "Bearer " + token, "Accept": "application/json"},
    )
    with urllib.request.urlopen(req) as resp:
        return json.load(resp)


def resolve_app_id(bundle_id, token):
    data = api_get("/apps?filter[bundleId]=" + urllib.parse.quote(bundle_id), token)
    if not data.get("data"):
        raise SystemExit("No app found for bundle id: " + bundle_id)
    return data["data"][0]["id"]


def latest_build(app_id, token):
    path = (
        "/builds?filter[app]={app}"
        "&filter[preReleaseVersion.platform]=IOS"
        "&include=preReleaseVersion"
        "&sort=-uploadedDate"
        "&limit=1"
    ).format(app=app_id)
    data = api_get(path, token)
    if not data.get("data"):
        raise SystemExit("No TestFlight builds found for app: " + app_id)

    build = data["data"][0]
    attrs = build["attributes"]
    build_number = attrs.get("version")
    uploaded_date = attrs.get("uploadedDate", "")

    marketing_version = "?"
    pre = build.get("relationships", {}).get("preReleaseVersion", {}).get("data")
    if pre:
        for included in data.get("included", []):
            if included.get("id") == pre.get("id"):
                marketing_version = included.get("attributes", {}).get("version", "?")
                break

    print(json.dumps(
        {
            "app_id": app_id,
            "marketing_version": marketing_version,
            "build_number": build_number,
            "uploaded_date": uploaded_date,
        },
        indent=2,
    ))


def main():
    args = get_args()
    missing = [n for n, v in (
        ("--issuer-id", args.issuer_id),
        ("--key-id", args.key_id),
        ("--private-key", args.private_key),
    ) if not v]
    if missing:
        raise SystemExit("Missing required argument(s): " + ", ".join(missing))
    if not args.bundle_id and not args.app_id:
        raise SystemExit("Provide --bundle-id or --app-id")

    token = make_token(args.issuer_id, args.key_id, args.private_key)
    app_id = args.app_id or resolve_app_id(args.bundle_id, token)
    latest_build(app_id, token)


if __name__ == "__main__":
    main()
