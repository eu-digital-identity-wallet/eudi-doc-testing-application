#!/bin/bash
export CUCUMBER_FILTER_TAGS="@Q3_2025"
mvn clean test -Dtest=TestRunner -Dcucumber.filter.tags="@Q3_2025" "$@"
rm -rf target/site/serenity
# Clear previous report data
mvn serenity:aggregate -Dtags=Q3_2025