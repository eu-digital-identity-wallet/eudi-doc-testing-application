call mvn clean verify -Dcucumber.filter.tags="@US_BRCD_TC_01 and @ANDROID" -Dserenity.threads=16 %*
call mvn serenity:aggregate -Dserenity.threads=16
