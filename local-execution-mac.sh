#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@Q1_2025" "$@"
rm -rf target/site/serenity
# Clear previous report data
mvn serenity:aggregate -Dtags=US_ADBSQRC