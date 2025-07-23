call mvn clean verify -Dcucumber.filter.tags="@ANDROID and @automated"
rmdir /s /q target/site/reports
call mvn serenity:aggregate -Dtags="ANDROID or automated"