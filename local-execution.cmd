call mvn clean verify -Dcucumber.filter.tags="@test" %*
call mvn serenity:aggregate