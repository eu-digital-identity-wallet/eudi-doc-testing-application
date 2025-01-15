call mvn clean verify -Dcucumber.filter.tags="(@US_COCD_TC_01 and @ANDROID)" %*
call mvn serenity:aggregate