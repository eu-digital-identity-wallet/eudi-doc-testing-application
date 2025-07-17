call mvn clean verify -Dcucumber.filter.tags="(@Q2_2025) or (@automated and @ANDROID)"
rmdir /s /q target/site/reports
call mvn serenity:aggregate -Dtags="Q2_2025 or automated"