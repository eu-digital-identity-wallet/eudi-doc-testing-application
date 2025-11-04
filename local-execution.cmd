TEST_CASES="@US_VP_TC_01 and @ANDROID"
echo "Running Android tests with tags: $TEST_CASES"
export CUCUMBER_FILTER_TAGS="$TEST_CASES"
mvn test -ntp -Dapp.url=$BROWSERSTACK_APP_URL -Dcucumber.filter.tags="$TEST_CASES"
cp src/test/resources/custom-style.css target/site/reports/EUDI_Wallet_Version_2025.10.31-Demo/css/core.css
mvn serenity:aggregate -Dtags="@US_VP_TC_01 and @ANDROID"
