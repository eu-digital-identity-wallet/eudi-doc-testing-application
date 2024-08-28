call mvn clean verify -Dcucumber.filter.tags="@IOS and @manual" %*
call mvn serenity:aggregate