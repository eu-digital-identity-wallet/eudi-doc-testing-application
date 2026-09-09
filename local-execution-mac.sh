#!/bin/bash

set +e

export CUCUMBER_FILTER_TAGS="@ANDROID and @execution_Q2_2026"

RESULTS_DIR="target/site/reports/EUDI_Wallet_Version_2026.08.41-Demo"
BACKUP_DIR="target/serenity-backup"

echo "========== CLEAN =========="

rm -rf target/serenity
rm -rf target/site/reports
rm -rf target/site/serenity
rm -f target/rerun.txt target/rerun2.txt target/rerun3.txt

mkdir -p "$BACKUP_DIR"

echo "========== MAIN EXECUTION =========="

mvn clean verify \
    -ntp \
    -Dtest=TestRunner \
    -Dcucumber.filter.tags="@ANDROID and @execution_Q2_2026"

echo "========== BACKUP RESULTS =========="

mkdir -p "$BACKUP_DIR"
cp "$RESULTS_DIR"/*.json "$BACKUP_DIR/" 2>/dev/null || true

echo "========== RERUN PASS 1 =========="

if [ -s target/rerun.txt ]; then
    mvn test -Dtest=RerunTestRunner
    python3 merge_serenity_results.py || true
else
    echo "No rerun.txt found"
fi

echo "========== RERUN PASS 2 =========="

if [ -s target/rerun2.txt ]; then
    mvn test \
        -Dtest=RerunTestRunner2 \
        -Dcucumber.features="@target/rerun2.txt"

    python3 merge_serenity_results.py || true
else
    echo "No rerun2.txt found"
fi

echo "========== RERUN PASS 3 =========="

if [ -s target/rerun3.txt ]; then
    mvn test \
        -Dtest=RerunTestRunner3 \
        -Dcucumber.features="@target/rerun3.txt"

    python3 merge_serenity_results.py || true
else
    echo "No rerun3.txt found"
fi

echo "========== GENERATE SERENITY REPORT =========="

mvn serenity:aggregate \
    -Dtags="@ANDROID and @execution"

echo "========== APPLY CUSTOM CSS =========="

REPORT_DIR="$RESULTS_DIR"

cp src/test/resources/custom-style.css \
   "$REPORT_DIR/css/core.css" 2>/dev/null || true

echo
echo "========================================"
echo "Execution completed."
echo "Report:"
echo "target/site/reports/EUDI_Wallet_Version_2026.08.41-Demo/index.html"
echo "========================================"