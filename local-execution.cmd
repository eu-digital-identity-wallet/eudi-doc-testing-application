call mvn clean verify -Dcucumber.filter.tags="(@US_VP_TC_01 and @ANDROID) or (@manual and @Android) or (@manual and @IOS)" %*
call mvn serenity:aggregate