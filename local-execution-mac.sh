#!/bin/bash
 rm -rf target/
 mvn clean verify -Dcucumber.filter.tags="@rerun3 and @ANDROID"
 mvn serenity:aggregate -Dtags="rerun3"
 mvn serenity:reports
