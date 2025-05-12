#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@check" "$@"
rm -rf target/site/serenity
# Clear previous report data
mvn serenity:aggregate -Dtags=check