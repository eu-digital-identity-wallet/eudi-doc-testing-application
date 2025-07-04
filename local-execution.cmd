call mvn clean verify -Dcucumber.filter.tags="(@automated and @ANDROID)"
rmdir /s /q target/site/reports
call mvn serenity:aggregate -Dtags="automated and ANDROID"