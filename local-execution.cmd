call mvn clean verify -Dcucumber.filter.tags="@US_VP_TC_01"
rmdir /s /q target/site/reports
call mvn serenity:aggregate -Dtags="US_VP_TC_01"
