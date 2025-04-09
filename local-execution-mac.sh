#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@Q4_2024" "$@"
rm -rf target/site/serenity  # Clear previous report data
mvn serenity:aggregate -Dtags=Q4_2024