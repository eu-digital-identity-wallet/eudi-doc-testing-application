import copy
import datetime
import glob
import json
import os
import re
import shutil

RESULTS_DIR = "target/site/reports/EUDI_Wallet_Version_2026.08.41-Demo"
BACKUP_DIR = "target/serenity-backup"
LOG_FILE = "target/merge-log.txt"


def log(msg):
    print(msg)
    try:
        with open(LOG_FILE, "a", encoding="utf-8") as fp:
            fp.write(str(msg) + "\n")
    except Exception:
        pass


log(f"\n===== merge_serenity_results.py run at {datetime.datetime.now().isoformat()} =====")

PRIORITY = {
    "SUCCESS": 0,
    "PENDING": 1,
    "IGNORED": 2,
    "SKIPPED": 3,
    "FAILURE": 4,
    "ERROR": 5,
}


def strip_index(desc):
    return re.sub(r"^\d+:\s*", "", desc)


def step_signature(step):
    """
    For Scenario Outlines, every example row's top-level testStep
    description is just "<row number>: <scenario title>" -- identical
    for every row once the index is stripped. Build a per-row
    fingerprint from the (unique, value-substituted) child step
    descriptions instead, so rows don't collide with each other.
    """
    children = step.get("children", [])
    if children:
        return tuple(
            strip_index(child.get("description", ""))
            for child in children
        )

    return (strip_index(step.get("description", "")),)


def overall_result(steps):
    if not steps:
        return "ERROR"

    results = [s.get("result", "ERROR") for s in steps]
    return max(results, key=lambda r: PRIORITY.get(r, 99))


def scenario_id(data):
    full_scenario_id = data.get("scenarioId")
    if full_scenario_id:
        return full_scenario_id

    return f"{data.get('id', '')}|{data.get('methodName', '')}"


def fix_children(step):
    """
    If a parent step is SUCCESS, make sure all children are SUCCESS too.
    Serenity derives parent status from children.
    """
    if step.get("result") == "SUCCESS":
        for child in step.get("children", []):
            if child.get("result") in (
                    "ERROR",
                    "FAILURE",
                    "SKIPPED",
                    "PENDING",
            ):
                child["result"] = "SUCCESS"

            fix_children(child)


# ----------------------------------------------------------------------
# Load backup
# ----------------------------------------------------------------------

backup_by_id = {}

for f in glob.glob(f"{BACKUP_DIR}/*.json"):
    try:
        with open(f, encoding="utf-8") as fp:
            data = json.load(fp)

        backup_by_id[scenario_id(data)] = (f, data)

    except Exception as e:
        log(f"[WARN] Could not read backup {f}: {e}")

log(f"Backups loaded: {len(backup_by_id)}")


# ----------------------------------------------------------------------
# Merge rerun results
# ----------------------------------------------------------------------

merged_ids = set()

for rerun_file in glob.glob(f"{RESULTS_DIR}/*.json"):

    try:
        with open(rerun_file, encoding="utf-8") as fp:
            rerun_data = json.load(fp)

    except Exception as e:
        log(f"[WARN] Could not read {rerun_file}: {e}")
        continue

    sid = scenario_id(rerun_data)
    merged_ids.add(sid)

    log(
        f"[DEBUG] Processing {os.path.basename(rerun_file)} "
        f"sid='{sid[:90]}' "
        f"rerun_testSteps={len(rerun_data.get('testSteps', []))} "
        f"rerun_rows={len(rerun_data.get('dataTable', {}).get('rows', []))}"
    )

    if sid not in backup_by_id:
        log(
            f"[INFO] No backup for: "
            f"{rerun_data.get('name','?')[:60]} -> keeping as-is"
        )
        continue

    backup_file, backup_original = backup_by_id[sid]

    backup_data = copy.deepcopy(backup_original)

    log(
        f"[DEBUG] Backup before merge: "
        f"testSteps={len(backup_data.get('testSteps', []))} "
        f"rows={len(backup_data.get('dataTable', {}).get('rows', []))} "
        f"result={backup_data.get('result')}"
    )

    rerun_steps = {
        step_signature(step): step
        for step in rerun_data.get("testSteps", [])
    }

    merged = []
    matched = 0

    for step in backup_data.get("testSteps", []):

        key = step_signature(step)

        if key in rerun_steps:
            rerun_step = rerun_steps[key]

            step["result"] = rerun_step.get(
                "result",
                step.get("result"),
            )

            rerun_children = rerun_step.get(
                "children",
                rerun_step.get("testSteps"),
            )

            if rerun_children is not None:
                step["children"] = rerun_children

            matched += 1

        fix_children(step)
        merged.append(step)

    backup_data["testSteps"] = merged
    backup_data["result"] = overall_result(merged)
    backup_data["annotatedResult"] = backup_data["result"]

    if backup_data["result"] == "SUCCESS":
        for field in (
                "testFailureCause",
                "testFailureClassname",
                "testFailureMessage",
                "testFailureSummary",
        ):
            backup_data.pop(field, None)

    rerun_rows_by_values = {
        tuple(r.get("values", [])): r
        for r in rerun_data.get("dataTable", {}).get("rows", [])
    }

    rows = backup_data.get("dataTable", {}).get("rows", [])

    for row in rows:
        key = tuple(row.get("values", []))

        if key in rerun_rows_by_values:
            row["result"] = rerun_rows_by_values[key].get(
                "result",
                row.get("result"),
            )

    # Update report JSON
    with open(rerun_file, "w", encoding="utf-8") as fp:
        json.dump(backup_data, fp, indent=2)

    # Update backup JSON so future reruns build on latest merged state
    with open(backup_file, "w", encoding="utf-8") as fp:
        json.dump(backup_data, fp, indent=2)

    # Update in-memory backup too
    backup_by_id[sid] = (backup_file, copy.deepcopy(backup_data))

    log(
        f"Merged '{rerun_data.get('name','?')[:60]}': "
        f"{matched}/{len(merged)} matched, "
        f"overall={backup_data['result']}"
    )
    log(
        f"[DEBUG] After merge -> written to {os.path.basename(rerun_file)} "
        f"and {os.path.basename(backup_file)}: "
        f"testSteps={len(merged)} "
        f"row_results={[r.get('result') for r in rows]}"
    )


# ----------------------------------------------------------------------
# Restore scenarios Serenity dropped
# ----------------------------------------------------------------------

restored = 0

for sid, (backup_file, _) in backup_by_id.items():

    if sid not in merged_ids:

        destination = os.path.join(
            RESULTS_DIR,
            os.path.basename(backup_file),
        )

        shutil.copy(backup_file, destination)

        restored += 1

        log(f"Restored '{os.path.basename(backup_file)}'")


# ----------------------------------------------------------------------
# Remove duplicate JSONs
# ----------------------------------------------------------------------

files_by_id = {}

for f in glob.glob(f"{RESULTS_DIR}/*.json"):

    try:
        with open(f, encoding="utf-8") as fp:
            data = json.load(fp)

        sid = scenario_id(data)

        if sid in files_by_id:

            older = min(
                files_by_id[sid],
                f,
                key=os.path.getmtime,
            )

            os.remove(older)

            files_by_id[sid] = max(
                files_by_id[sid],
                f,
                key=os.path.getmtime,
            )

            log(
                f"Deduplicated: removed older file for "
                f"'{data.get('name','?')[:60]}'"
            )

        else:
            files_by_id[sid] = f

    except Exception:
        pass

log(
    f"Done. "
    f"Merged: {len(merged_ids)}, "
    f"Restored: {restored}, "
    f"Final files: {len(files_by_id)}"
)

for f in sorted(glob.glob(f"{RESULTS_DIR}/*.json")):
    try:
        with open(f, encoding="utf-8") as fp:
            data = json.load(fp)
        rows = data.get("dataTable", {}).get("rows", [])
        log(
            f"[DEBUG] FINAL STATE {os.path.basename(f)} "
            f"name='{data.get('name','?')[:50]}' "
            f"result={data.get('result')} "
            f"testSteps={len(data.get('testSteps', []))} "
            f"row_results={[r.get('result') for r in rows]}"
        )
    except Exception:
        pass