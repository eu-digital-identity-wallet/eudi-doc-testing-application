#!/bin/bash
<<<<<<< HEAD
mvn clean verify -Dcucumber.filter.tags="@check" "$@"
=======
mvn clean verify -Dcucumber.filter.tags="@US_VP_TC_01 and @IOS" "$@"
>>>>>>> 45788ff23e7b1f892a6555ec661448faa45c06c5
rm -rf target/site/serenity
# Clear previous report data
mvn serenity:aggregate -Dtags=check