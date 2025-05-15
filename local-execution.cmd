call mvn clean verify -Dcucumber.filter.tags="@US_RWVCD_TC_01" %*
call mvn serenity:aggregate