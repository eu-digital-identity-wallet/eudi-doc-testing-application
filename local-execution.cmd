call mvn clean verify -Dcucumber.filter.tags="@report" %*
call mvn serenity:aggregate