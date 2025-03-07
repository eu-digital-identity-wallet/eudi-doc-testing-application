#!/bin/bash
mvn clean verify -Dcucumber.filter.tags="@Q4_2024" "$@"
mvn serenity:aggregate
