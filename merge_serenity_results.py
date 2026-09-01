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


log(
    f"\n===== merge_serenity_results.py run at "
    f"{datetime.datetime.now().isoformat()} ====="
)


# SUCCESS is always preferred over a failed result.
RESULT_PRIORITY = {
    "SUCCESS": 0,
    "PENDING": 1,
    "IGNORED": 2,
    "SKIPPED": 3,
    "FAILURE": 4,
    "ERROR": 5,
}


def strip_index(desc):
    return re.sub(r"^\d+:\s*", "", desc or "")


def step_signature(step):
    """
    Creates a stable signature for a Serenity test step.

    For Scenario Outlines, child descriptions normally contain
    the example-specific values, so they are used to distinguish
    individual rows.
    """

    children = step.get("children", [])

    if children:
        return tuple(
            strip_index(child.get("description", ""))
            for child in children
        )

    return (
        strip_index(
            step.get("description", "")
        ),
    )


def overall_result(steps):
    """
    Calculate the scenario result from its top-level test steps.

    Any ERROR/FAILURE causes the scenario to fail unless the
    rerun has already replaced that step with SUCCESS.
    """

    if not steps:
        return "ERROR"

    results = [
        step.get("result", "ERROR")
        for step in steps
    ]

    return max(
        results,
        key=lambda r: RESULT_PRIORITY.get(r, 99)
    )


def scenario_id(data):
    """
    Primary scenario identifier.

    scenarioId is preferred because it is the most stable
    Serenity identifier.

    Fallback combines id + methodName.
    """

    full_scenario_id = data.get("scenarioId")

    if full_scenario_id:
        return full_scenario_id

    return (
        f"{data.get('id', '')}|"
        f"{data.get('methodName', '')}"
    )


def fix_children(step):
    """
    If a step is SUCCESS, ensure Serenity's child steps are
    also SUCCESS so the generated report does not retain
    stale FAILURE/ERROR children.
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


def clean_success_failure_fields(data):
    """
    Remove stale failure metadata after a scenario becomes SUCCESS.
    """

    if data.get("result") != "SUCCESS":
        return

    for field in (
            "testFailureCause",
            "testFailureClassname",
            "testFailureMessage",
            "testFailureSummary",
    ):
        data.pop(field, None)


# ======================================================================
# LOAD CANONICAL BACKUP
# ======================================================================

backup_by_id = {}
backup_filenames = set()

for f in glob.glob(
        os.path.join(BACKUP_DIR, "*.json")
):

    try:

        with open(f, encoding="utf-8") as fp:
            data = json.load(fp)

        sid = scenario_id(data)

        if not sid:
            log(
                f"[WARN] Could not determine scenario ID "
                f"for backup: {f}"
            )
            continue

        backup_by_id[sid] = (
            f,
            copy.deepcopy(data),
        )

        backup_filenames.add(
            os.path.basename(f)
        )

    except Exception as e:

        log(
            f"[WARN] Could not read backup {f}: {e}"
        )


log(
    f"[INFO] Canonical backup scenarios: "
    f"{len(backup_by_id)}"
)


# ======================================================================
# IDENTIFY ONLY NEW RERUN JSON FILES
# ======================================================================

all_result_files = glob.glob(
    os.path.join(RESULTS_DIR, "*.json")
)

rerun_files = []

for f in all_result_files:

    filename = os.path.basename(f)

    # IMPORTANT:
    # Never process the original/canonical JSON as a rerun.
    if filename in backup_filenames:
        continue

    rerun_files.append(f)


log(
    f"[INFO] Total JSON files in results directory: "
    f"{len(all_result_files)}"
)

log(
    f"[INFO] JSON files considered as reruns: "
    f"{len(rerun_files)}"
)


# ======================================================================
# PROCESS RERUN RESULTS
# ======================================================================

processed_rerun_ids = set()

for rerun_file in rerun_files:

    try:

        with open(rerun_file, encoding="utf-8") as fp:
            rerun_data = json.load(fp)

    except Exception as e:

        log(
            f"[WARN] Could not read rerun "
            f"{rerun_file}: {e}"
        )

        continue

    sid = scenario_id(rerun_data)

    if not sid:

        log(
            f"[WARN] No scenario ID for "
            f"{rerun_file}"
        )

        continue

    processed_rerun_ids.add(sid)

    log(
        f"[DEBUG] RERUN "
        f"{os.path.basename(rerun_file)} "
        f"sid='{sid[:100]}' "
        f"result={rerun_data.get('result')} "
        f"testSteps={len(rerun_data.get('testSteps', []))}"
    )

    # --------------------------------------------------------------
    # Scenario not present in original execution
    # --------------------------------------------------------------

    if sid not in backup_by_id:

        log(
            f"[INFO] No canonical backup found for "
            f"'{rerun_data.get('name', '?')[:80]}'. "
            f"Keeping rerun as new scenario."
        )

        destination = os.path.join(
            RESULTS_DIR,
            os.path.basename(rerun_file),
        )

        with open(
                destination,
                "w",
                encoding="utf-8",
        ) as fp:

            json.dump(
                rerun_data,
                fp,
                indent=2,
            )

        continue

    # --------------------------------------------------------------
    # Start from canonical state
    # --------------------------------------------------------------

    backup_file, canonical_original = backup_by_id[sid]

    canonical = copy.deepcopy(
        canonical_original
    )

    original_result = canonical.get(
        "result",
        "ERROR",
    )

    log(
        f"[DEBUG] Canonical before rerun: "
        f"result={original_result}"
    )

    # --------------------------------------------------------------
    # Merge test steps
    # --------------------------------------------------------------

    rerun_steps = {
        step_signature(step): step
        for step in rerun_data.get(
            "testSteps",
            [],
        )
    }

    matched = 0

    for canonical_step in canonical.get(
            "testSteps",
            [],
    ):

        key = step_signature(
            canonical_step
        )

        if key not in rerun_steps:
            fix_children(canonical_step)
            continue

        rerun_step = rerun_steps[key]

        rerun_result = rerun_step.get(
            "result",
            canonical_step.get(
                "result",
                "ERROR",
            ),
        )

        # ----------------------------------------------------------
        # IMPORTANT:
        #
        # A successful rerun MUST replace the original failure.
        # ----------------------------------------------------------

        canonical_step["result"] = rerun_result

        rerun_children = rerun_step.get(
            "children",
            rerun_step.get(
                "testSteps"
            ),
        )

        if rerun_children is not None:
            canonical_step["children"] = copy.deepcopy(
                rerun_children
            )

        fix_children(canonical_step)

        matched += 1

    # --------------------------------------------------------------
    # Calculate final scenario result
    # --------------------------------------------------------------

    canonical["testSteps"] = canonical.get(
        "testSteps",
        [],
    )

    canonical["result"] = overall_result(
        canonical["testSteps"]
    )

    canonical["annotatedResult"] = canonical[
        "result"
    ]

    # --------------------------------------------------------------
    # Merge data table
    # --------------------------------------------------------------

    rerun_rows_by_values = {
        tuple(
            row.get(
                "values",
                []
            )
        ): row
        for row in rerun_data.get(
            "dataTable",
            {}
        ).get(
            "rows",
            []
        )
    }

    rows = canonical.get(
        "dataTable",
        {}
    ).get(
        "rows",
        []
    )

    for row in rows:

        key = tuple(
            row.get(
                "values",
                []
            )
        )

        if key in rerun_rows_by_values:

            rerun_row = rerun_rows_by_values[key]

            row["result"] = rerun_row.get(
                "result",
                row.get(
                    "result",
                    "ERROR",
                ),
            )

    # --------------------------------------------------------------
    # Recalculate result from rows if necessary
    # --------------------------------------------------------------

    row_results = [
        row.get("result")
        for row in rows
        if row.get("result")
    ]

    if row_results:

        row_result = max(
            row_results,
            key=lambda r: RESULT_PRIORITY.get(
                r,
                99,
            )
        )

        # A successful rerun should never become a failure
        # because of stale metadata.
        if row_result == "SUCCESS":
            canonical["result"] = "SUCCESS"

    canonical["annotatedResult"] = canonical[
        "result"
    ]

    clean_success_failure_fields(
        canonical
    )

    # --------------------------------------------------------------
    # Write canonical result back to the ORIGINAL filename
    # --------------------------------------------------------------

    destination = os.path.join(
        RESULTS_DIR,
        os.path.basename(backup_file),
    )

    with open(
            destination,
            "w",
            encoding="utf-8",
    ) as fp:

        json.dump(
            canonical,
            fp,
            indent=2,
        )

    # --------------------------------------------------------------
    # Update canonical backup
    # --------------------------------------------------------------

    with open(
            backup_file,
            "w",
            encoding="utf-8",
    ) as fp:

        json.dump(
            canonical,
            fp,
            indent=2,
        )

    backup_by_id[sid] = (
        backup_file,
        copy.deepcopy(canonical),
    )

    log(
        f"[MERGED] "
        f"{canonical.get('name', '?')[:80]} | "
        f"{original_result} -> "
        f"{canonical['result']} | "
        f"matched steps={matched}"
    )

    # --------------------------------------------------------------
    # Remove rerun JSON
    #
    # This is critical.
    # The rerun must NOT remain as a second scenario.
    # --------------------------------------------------------------

    try:

        os.remove(rerun_file)

        log(
            f"[REMOVED RERUN] "
            f"{os.path.basename(rerun_file)}"
        )

    except Exception as e:

        log(
            f"[WARN] Could not remove rerun "
            f"{rerun_file}: {e}"
        )


# ======================================================================
# RESTORE ALL CANONICAL SCENARIOS
# ======================================================================

restored = 0

for sid, (
        backup_file,
        canonical_data,
) in backup_by_id.items():

    destination = os.path.join(
        RESULTS_DIR,
        os.path.basename(backup_file),
    )

    try:

        with open(
                destination,
                "w",
                encoding="utf-8",
        ) as fp:

            json.dump(
                canonical_data,
                fp,
                indent=2,
            )

        restored += 1

    except Exception as e:

        log(
            f"[WARN] Could not restore "
            f"{destination}: {e}"
        )


# ======================================================================
# FINAL DEDUPLICATION BY SCENARIO ID
# ======================================================================

files_by_id = {}

for f in glob.glob(
        os.path.join(
            RESULTS_DIR,
            "*.json",
        )
):

    try:

        with open(
                f,
                encoding="utf-8",
        ) as fp:

            data = json.load(fp)

        sid = scenario_id(data)

        if not sid:
            continue

        if sid not in files_by_id:

            files_by_id[sid] = f

        else:

            existing = files_by_id[sid]

            # Canonical backup file always wins.
            if os.path.basename(existing) in backup_filenames:

                os.remove(f)

                log(
                    f"[DEDUP] Removed duplicate: "
                    f"{os.path.basename(f)}"
                )

            elif os.path.basename(f) in backup_filenames:

                os.remove(existing)

                files_by_id[sid] = f

                log(
                    f"[DEDUP] Removed duplicate: "
                    f"{os.path.basename(existing)}"
                )

            else:

                # If neither is canonical, keep the first.
                os.remove(f)

                log(
                    f"[DEDUP] Removed duplicate: "
                    f"{os.path.basename(f)}"
                )

    except Exception as e:

        log(
            f"[WARN] Could not process "
            f"{f}: {e}"
        )


# ======================================================================
# FINAL VALIDATION
# ======================================================================

final_files = glob.glob(
    os.path.join(
        RESULTS_DIR,
        "*.json",
    )
)

final_scenarios = {}

for f in final_files:

    try:

        with open(
                f,
                encoding="utf-8",
        ) as fp:

            data = json.load(fp)

        sid = scenario_id(data)

        final_scenarios[sid] = data

    except Exception:
        pass


results = {}

for data in final_scenarios.values():

    result = data.get(
        "result",
        "MISSING",
    )

    results[result] = (
            results.get(result, 0) + 1
    )


log("")
log("========================================")
log("FINAL MERGE VALIDATION")
log("========================================")
log(
    f"Canonical scenarios: "
    f"{len(backup_by_id)}"
)
log(
    f"Final JSON files: "
    f"{len(final_files)}"
)
log(
    f"Final unique scenarios: "
    f"{len(final_scenarios)}"
)
log("")
log("RESULTS:")

for result, count in sorted(
        results.items()
):
    log(
        f"  {result}: {count}"
    )

log("========================================")

if len(final_scenarios) != len(backup_by_id):

    log(
        "[WARNING] FINAL UNIQUE SCENARIO COUNT "
        "DOES NOT MATCH CANONICAL COUNT!"
    )

else:

    log(
        "[OK] FINAL UNIQUE SCENARIO COUNT "
        "MATCHES CANONICAL COUNT."
    )