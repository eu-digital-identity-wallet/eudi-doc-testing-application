call mvn clean verify -Dcucumber.filter.tags="@manual and @ANDROID" %*
call mvn serenity:aggregate