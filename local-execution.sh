#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@Q1_2025 and @ANDROID" "$@"
rm -rf target/site/serenity
mvn serenity:aggregate -Dtags="Q1_2025 AND ANDROID"