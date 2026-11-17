#!/usr/bin/env python3
"""Gets the latest Android dev release (version info) from Firebase App Distribution.

Auth (pick one):
  - a service account JSON key:        --service-account-json  (or GOOGLE_APPLICATION_CREDENTIALS)
  - a pre-generated OAuth2 token:      --token                 (or GOOGLE_OAUTH_TOKEN)

Prerequisite: a service account with the "Firebase App Distribution Admin" role,
created in Firebase console -> Project settings -> Service accounts.

Usage:
  pip install google-auth google-auth-httplib2
  python scripts/firebase-latest-release.py --app-id 1:123456789:android:abc
  python scripts/firebase-latest-release.py --app-id 1:123456789:android:abc --service-account-json sa.json
"""
import argparse
import json
import os
import sys
import urllib.parse
import urllib.request

API_BASE = "https://firebaseappdistribution.googleapis.com/v1"


def get_args():
    p = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    p.add_argument("--app-id", default=os.environ.get("FIREBASE_APP_ID"),
                   help="Firebase App ID, e.g. 1:123456789:android:abc")
    p.add_argument("--project-number", default=os.environ.get("FIREBASE_PROJECT_NUMBER"),
                   help="Firebase project number (default: derived from the App ID)")
    p.add_argument("--service-account-json", default=os.environ.get("GOOGLE_APPLICATION_CREDENTIALS"),
                   help="Path to the service account JSON key")
    p.add_argument("--token", default=os.environ.get("GOOGLE_OAUTH_TOKEN"),
                   help="Pre-generated OAuth2 access token (cloud-platform scope)")
    return p.parse_args()


def oauth_token(sa_json_path):
    if not sa_json_path:
        sys.exit("Provide --service-account-json / GOOGLE_APPLICATION_CREDENTIALS or --token / GOOGLE_OAUTH_TOKEN")
    try:
        import google.auth.transport.requests
        from google.oauth2 import service_account
    except ImportError:
        sys.exit("Missing dependency: pip install google-auth google-auth-httplib2")
    creds = service_account.Credentials.from_service_account_file(
        sa_json_path, scopes=["https://www.googleapis.com/auth/cloud-platform"]
    )
    creds.refresh(google.auth.transport.requests.Request())
    return creds.token


def api_get(path, token):
    req = urllib.request.Request(API_BASE + path, headers={"Authorization": "Bearer " + token})
    with urllib.request.urlopen(req) as resp:
        return json.load(resp)


def main():
    args = get_args()
    if not args.app_id:
        sys.exit("Missing --app-id / FIREBASE_APP_ID")

    project = args.project_number
    if not project and args.app_id.startswith("1:"):
        project = args.app_id.split(":")[1]
    if not project:
        sys.exit("Could not determine project number; pass --project-number")

    token = args.token or oauth_token(args.service_account_json)

    parent = "projects/{}/apps/{}".format(
        urllib.parse.quote(project, safe=":"),
        urllib.parse.quote(args.app_id, safe=":"),
    )
    data = api_get(parent + "/releases?pageSize=1", token)

    if not data.get("releases"):
        sys.exit("No releases found for app " + args.app_id)

    rel = data["releases"][0]
    print(json.dumps({
        "display_version": rel.get("displayVersion"),
        "build_version": rel.get("buildVersion"),
        "create_time": rel.get("createTime"),
        "latest": rel.get("latest"),
    }, indent=2))


if __name__ == "__main__":
    main()
