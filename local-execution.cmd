mvn verify -ntp -Dtest=TestRunner -Dcucumber.filter.tags="@US_VP_TC_01 and @ANDROID"
mvn serenity:aggregate -Dtags="@US_VP_TC_01 AND @ANDROID"