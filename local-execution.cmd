call mvn clean verify -Dcucumber.filter.tags="@manual" %*
call mvn serenity:aggregate