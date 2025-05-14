#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@US_VP_TC_01 and @IOS" "$@"
rm -rf target/site/serenity
# Clear previous report data
mvn serenity:aggregate -Dtags=check