#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@sakis" "$@"
rm -rf target/site/serenity
# Clear previous report data
mvn serenity:aggregate -Dtags=sakis