#!/bin/bash
 rm -rf target/
 mvn clean verify -Dcucumber.filter.tags="(@Q2_2025) or (@automated)"
 mvn serenity:aggregate -Dtags="Q2_2025 or automated"
 mvn serenity:reports
