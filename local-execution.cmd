call mvn clean verify -Dcucumber.filter.tags="@ANDROID or @manual or @automated" %*
call mvn serenity:aggregate