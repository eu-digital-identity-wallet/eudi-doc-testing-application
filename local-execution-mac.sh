#!/bin/bash
export CUCUMBER_FILTER_TAGS="@ANDROID and @execution"
RESULTS_DIR="target/site/reports/EUDI_Wallet_Version_2026.05.37-Demo"
BACKUP_DIR="target/serenity-backup"

# Clean up leftovers from previous runs
rm -f target/rerun.txt target/rerun2.txt
rm -f "$BACKUP_DIR"/*.json
rm -f "$RESULTS_DIR"/*.json

# Main run
mvn test -Dtest=TestRunner -Dcucumber.filter.tags="@ANDROID and @execution" "$@"

# Backup full results before any rerun overwrites them
mkdir -p "$BACKUP_DIR"
cp "$RESULTS_DIR"/*.json "$BACKUP_DIR/" 2>/dev/null || true
echo "--- Backed up $(ls $BACKUP_DIR/*.json 2>/dev/null | wc -l | tr -d ' ') JSON file(s) ---"

# Rerun pass 1
if [ -s target/rerun.txt ]; then
  echo "--- Failures detected. Rerunning failed scenarios (pass 1/2) ---"
  mvn test -Dtest=RerunTestRunner "$@"
  echo "--- Merging pass 1 results ---"
  python3 merge_serenity_results.py
  cp "$RESULTS_DIR"/*.json "$BACKUP_DIR/" 2>/dev/null || true
fi

# Rerun pass 2
if [ -s target/rerun2.txt ]; then
  echo "--- Failures still present. Rerunning failed scenarios (pass 2/2) ---"
  mvn test -Dtest=RerunTestRunner -Dcucumber.features="@target/rerun2.txt" "$@"
  echo "--- Merging pass 2 results ---"
  python3 merge_serenity_results.py
fi

rm -rf target/site/serenity
mvn serenity:aggregate -Dtags="ANDROID and execution"
echo "--- Applying custom CSS ---"

REPORT_DIR="target/site/reports/EUDI_Wallet_Version_2026.05.37-Demo/css"
CUSTOM_CSS="src/test/resources/custom-style.css"

if [ -f "$CUSTOM_CSS" ]; then
CORE_CSS_PATH=$(find "$REPORT_DIR" -name "core.css" | head -n 1)
if [ -n "$CORE_CSS_PATH" ]; then
  echo "Found core.css at: $CORE_CSS_PATH"
  cp "$CUSTOM_CSS" "$CORE_CSS_PATH"
  echo "Successfully replaced core.css with custom-style.css."
  else
    echo "Warning: core.css not found in $REPORT_DIR. Cannot apply custom style."
    fi
    else
      echo "Warning: custom-style.css not found at $CUSTOM_CSS."
      fi
      echo "--- Local execution complete. Report is available at target/site/serenity/index.html ---"