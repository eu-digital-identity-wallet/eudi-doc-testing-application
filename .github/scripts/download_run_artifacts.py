import json
import os
import sys
import urllib.request
import zipfile

repo = sys.argv[1]
run_ids = [r for r in sys.argv[2].split(",") if r.strip()]
platform = sys.argv[3]
token = os.environ["GH_TOKEN"]
out_dir = f"artifacts/{platform}"
os.makedirs(out_dir, exist_ok=True)


def api(path, accept_zip=False):
    headers = {
        "Authorization": f"token {token}",
    }
    if accept_zip:
        headers["Accept"] = "application/vnd.github+json"
    else:
        headers["Accept"] = "application/vnd.github+json"
    req = urllib.request.Request(
        f"https://api.github.com/repos/{repo}/{path}",
        headers=headers,
    )
    with urllib.request.urlopen(req) as resp:
        return resp.read()


def api_json(path):
    return json.loads(api(path))


for run_id in run_ids:
    artifacts = api_json(f"actions/runs/{run_id}/artifacts")["artifacts"]

    for art in artifacts:
        # Only pull the per-scenario JSON artifact, skip the full report/logs
        if not art["name"].endswith("-serenity-json"):
            continue

        print(f"[run {run_id}] Downloading artifact: {art['name']}")
        data = api(
            f"actions/artifacts/{art['id']}/zip",
            accept_zip=True,
        )
        with open("tmp.zip", "wb") as f:
            f.write(data)

        with zipfile.ZipFile("tmp.zip") as z:
            for name in z.namelist():
                if name.lower().endswith(".json"):
                    # Prefix to avoid collisions if both runs share scenario ids
                    base = f"{platform}_{os.path.basename(name)}"
                    with z.open(name) as src, open(
                        os.path.join(out_dir, base), "wb"
                    ) as dst:
                        dst.write(src.read())

        os.remove("tmp.zip")

print(f"Done. JSON files in {out_dir}:")
for f in sorted(os.listdir(out_dir)):
    print("  ", f)
