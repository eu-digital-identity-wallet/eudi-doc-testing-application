call mvn clean verify -Dcucumber.filter.tags="@automated and @ANDROID" %*
call mvn serenity:aggregate