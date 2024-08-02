call mvn clean verify -Dcucumber.filter.tags="@bug" %*
call mvn serenity:aggregate