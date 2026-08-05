package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "@target/rerun3.txt",
        monochrome = true,
        glue = {"eu.europa.eudi.stepdefs"}
)

public class RerunTestRunner3 {
}
