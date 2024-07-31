call mvn clean verify -Dcucumber.filter.tags="@ANDROID and @US_COSD_TC_01" %*
call mvn serenity:aggregate