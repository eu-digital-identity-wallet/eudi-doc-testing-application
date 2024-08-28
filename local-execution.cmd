call mvn clean verify -Dcucumber.filter.tags="@ANDROID" %*
call mvn serenity:aggregate