call mvn clean verify -Dcucumber.filter.tags="@ANDROID and @automated" %*
call mvn serenity:aggregate