#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@US_ADBSQRC" "$@"
rm -rf target/site/serenity
# Clear previous report data
mvn serenity:aggregate -Dtags=US_ADBSQRC