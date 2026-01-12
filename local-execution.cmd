mvn verify -ntp -Dtest=TestRunner -Dcucumber.filter.tags="@endtoend"
mvn serenity:aggregate -Dtags="@endtoend"