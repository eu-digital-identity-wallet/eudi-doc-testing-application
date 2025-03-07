call mvn clean verify -Dcucumber.filter.tags="@manual and @US_BRCD_TC_01" %*
call mvn serenity:aggregate

