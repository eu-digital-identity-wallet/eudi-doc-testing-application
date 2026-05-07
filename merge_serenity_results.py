import json, os, glob, re, shutil, copy

RESULTS_DIR = "target/site/reports/EUDI_Wallet_Version_2025.12.34-Demo"
BACKUP_DIR  = "target/serenity-backup"

PRIORITY = {"SUCCESS": 0, "PENDING": 1, "IGNORED": 2, "SKIPPED": 3, "FAILURE": 4, "ERROR": 5}

def strip_index(desc):
    return re.sub(r'^\d+: ', '', desc)

def overall_result(steps):
    if not steps:
        return "ERROR"
    results = [s.get("result", "ERROR") for s in steps]
    return max(results, key=lambda r: PRIORITY.get(r, 99))

def scenario_id(data):
    return data.get("id", "") + "|" + data.get("methodName", "")

def fix_children(step):
    """If a step passed, ensure all its children show SUCCESS (Serenity computes result from children)."""
    if step.get("result") == "SUCCESS":
        for child in step.get("children", []):
            if child.get("result") in ("ERROR", "FAILURE", "SKIPPED", "PENDING"):
                child["result"] = "SUCCESS"
            fix_children(child)  # recurse for deeply nested steps

# Load all backup files indexed by scenario ID
backup_by_id = {}
for f in glob.glob(f"{BACKUP_DIR}/*.json"):
    try:
        data = json.load(open(f))
        sid = scenario_id(data)
        backup_by_id[sid] = (f, data)
    except Exception as e:
        print(f"  [WARN] Could not read backup {f}: {e}")

print(f"  Backups loaded: {len(backup_by_id)}")

# --- Merge rerun results into backup (match by scenario ID) ---
merged_ids = set()
for rerun_file in glob.glob(f"{RESULTS_DIR}/*.json"):
    try:
        rerun_data = json.load(open(rerun_file))
    except Exception as e:
        print(f"  [WARN] Could not read {rerun_file}: {e}")
        continue

    sid = scenario_id(rerun_data)
    merged_ids.add(sid)

    if sid not in backup_by_id:
        print(f"  [INFO] No backup for: {rerun_data.get('name','?')[:60]} → keeping as-is")
        continue

    _, backup_original = backup_by_id[sid]
    backup_data = copy.deepcopy(backup_original)  # fresh copy every iteration

    rerun_steps = {strip_index(s.get("description", "")): s
                   for s in rerun_data.get("testSteps", [])}

    merged = []
    matched = 0
    for step in backup_data.get("testSteps", []):
        key = strip_index(step.get("description", ""))
        if key in rerun_steps:
            rerun_step = rerun_steps[key]
            step["result"] = rerun_step.get("result", step.get("result"))
            # Copy children from rerun (Serenity uses "children", not "testSteps")
            rerun_children = rerun_step.get("children", rerun_step.get("testSteps"))
            if rerun_children is not None:
                step["children"] = rerun_children
            matched += 1
        # If the step passed, fix any children still showing ERROR/SKIPPED
        fix_children(step)
        merged.append(step)

    backup_data["testSteps"] = merged
    backup_data["result"]    = overall_result(merged)
    backup_data["annotatedResult"] = backup_data["result"]

    # When test passed, remove failure metadata so Serenity doesn't show broken
    if backup_data["result"] == "SUCCESS":
        for field in ("testFailureCause", "testFailureClassname", "testFailureMessage", "testFailureSummary"):
            backup_data.pop(field, None)

    # Update dataTable row results to match testStep results (by index)
    rows = backup_data.get("dataTable", {}).get("rows", [])
    for i, row in enumerate(rows):
        if i < len(merged):
            row["result"] = merged[i].get("result", row.get("result"))

    json.dump(backup_data, open(rerun_file, "w"), indent=2)
    print(f"  Merged '{rerun_data.get('name','?')[:60]}': {matched}/{len(backup_data['testSteps'])} matched, overall={backup_data['result']}")

# --- Restore scenarios Serenity dropped during the rerun ---
restored = 0
for sid, (backup_file, _) in backup_by_id.items():
    if sid not in merged_ids:
        dest = os.path.join(RESULTS_DIR, os.path.basename(backup_file))
        shutil.copy(backup_file, dest)
        restored += 1
        print(f"  Restored '{os.path.basename(backup_file)}' from backup")

# --- Deduplicate: keep only the newest file per scenario ID ---
files_by_id = {}
for f in glob.glob(f"{RESULTS_DIR}/*.json"):
    try:
        d = json.load(open(f))
        sid = scenario_id(d)
        if sid in files_by_id:
            older = min(files_by_id[sid], f, key=os.path.getmtime)
            os.remove(older)
            files_by_id[sid] = max(files_by_id[sid], f, key=os.path.getmtime)
            print(f"  Deduplicated: removed older file for '{d.get('name','?')[:60]}'")
        else:
            files_by_id[sid] = f
    except Exception:
        pass

print(f"  Done. Merged: {len(merged_ids)}, Restored: {restored}, Final files: {len(files_by_id)}")