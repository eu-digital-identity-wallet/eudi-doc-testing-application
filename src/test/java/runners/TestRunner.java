package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/"},
        monochrome = true,
        tags = "@Q4_2024",
        glue = {"eu.europa.eudi.stepdefs"})
public class TestRunner {
}