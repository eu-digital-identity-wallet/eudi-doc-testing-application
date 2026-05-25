package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"@target/rerun.txt"},
        monochrome = true,
        glue = {"eu.europa.eudi.stepdefs"},
        plugin = {"rerun:target/rerun2.txt"})
public class RerunTestRunner {
}
