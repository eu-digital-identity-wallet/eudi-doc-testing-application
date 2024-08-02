call mvn clean verify -Dcucumber.filter.tags="(@IOS and @automated) or @manual" %*
call mvn serenity:aggregate