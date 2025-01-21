call mvn clean verify -Dcucumber.filter.tags="@manual" -Dserenity.threads=16 %*
call mvn serenity:aggregate -Dserenity.threads=16
