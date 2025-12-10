#!/bin/bash
export CUCUMBER_FILTER_TAGS="@automated"
mvn test -Dtest=TestRunner -Dcucumber.filter.tags="@automated" "$@"
rm -rf target/site/serenity
# Clear previous report data
mvn serenity:aggregate -Dtags="automated"
echo "--- Applying custom CSS ---"

REPORT_DIR="target/site/reports/EUDI_Wallet_Version_2025.12.34-Demo/css"
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
