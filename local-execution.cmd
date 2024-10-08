call mvn clean verify -Dcucumber.filter.tags="@manual and @US_BRCD" %*
call mvn serenity:aggregate