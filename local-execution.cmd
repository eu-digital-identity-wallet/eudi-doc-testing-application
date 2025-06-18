call mvn clean verify -Dcucumber.filter.tags="(@US_VP_TC_01 and @ANDROID) or (@US_BRCD_TC_01 and @ANDROID)"
rmdir /s /q target/site/reports
call mvn serenity:aggregate -Dtags="US_VP_TC_01 or US_BRCD_TC_01"