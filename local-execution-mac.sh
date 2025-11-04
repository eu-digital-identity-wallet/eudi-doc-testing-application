#!/bin/bash
export CUCUMBER_FILTER_TAGS="@automated"
mvn clean test -Dtest=TestRunner -Dcucumber.filter.tags="@automated" "$@"
rm -rf target/site/serenity
# Clear previous report data
mvn serenity:aggregate -Dtags="automated"