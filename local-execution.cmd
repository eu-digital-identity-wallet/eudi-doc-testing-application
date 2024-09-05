call mvn clean verify -Dcucumber.filter.tags="(@manual and @ANDROID) or (@automated and @ANDROID)" %*
call mvn serenity:aggregate