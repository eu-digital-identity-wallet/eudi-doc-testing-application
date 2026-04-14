package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/android/regressionTests/Automated_Regression_Tests/**"},
        monochrome = true,
        glue = {"eu.europa.eudi.stepdefs"})
public class TestRunner {
}