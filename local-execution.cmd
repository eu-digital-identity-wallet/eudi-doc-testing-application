call mvn clean verify -Dcucumber.filter.tags="@IOS and @automated" %*
call mvn serenity:aggregate