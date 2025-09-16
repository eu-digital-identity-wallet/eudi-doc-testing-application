<<<<<<< HEAD
call mvn clean verify -Dcucumber.filter.tags="@US_BRCD_TC_01"
rmdir /s /q target/site/reports
call mvn serenity:aggregate -Dtags="US_BRCD_TC_01"
