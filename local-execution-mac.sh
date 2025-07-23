#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@automated or @Q2_2025" "$@"
rm -rf target/site/serenity
# Clear previous report data
mvn serenity:aggregate -Dtags="automated or Q2_2025"