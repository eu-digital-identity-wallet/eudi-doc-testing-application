package eu.europa.eudi.stepdefs;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.utils.TestSetup;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

//public class WalletStepDefs extends GeneralStepDefs{
public class WalletStepDefs{
    TestSetup test;
    @Before
    public void setup(Scenario scenario) {

        boolean noReset = scenario.getSourceTagNames().contains("@noreset");
        boolean data = scenario.getSourceTagNames().contains("@before_01");
        boolean two_pid_data = scenario.getSourceTagNames().contains("@before_02");
        boolean pid_and_mdl_data = scenario.getSourceTagNames().contains("@before_03");
        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
        boolean ios = scenario.getSourceTagNames().contains("@IOS");
        if (android) {
            test = new TestSetup(noReset, Literals.General.ANDROID.label, scenario);
            test.startAndroidDriverSession();
            test.setScenario(scenario);
            test.startLogging();
        }
        if (ios) {
            test = new TestSetup(noReset, Literals.General.IOS.label, scenario);
            test.startIosDriverSession();
            test.setScenario(scenario);
            test.startLogging();
        }
        if (data) {
            test.mobile().wallet().checkIfPageIsTrue();
            test.mobile().wallet().createAPin();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().renterThePin();
            test.mobile().wallet().clickConfirm();
            test.mobile().wallet().successMessageOfSetUpPin();
            test.mobile().wallet().clickAddMyDigitalID();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
        }

        if (two_pid_data) {
            test.mobile().wallet().checkIfPageIsTrue();
            test.mobile().wallet().createAPin();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().renterThePin();
            test.mobile().wallet().clickConfirm();
            test.mobile().wallet().successMessageOfSetUpPin();
            test.mobile().wallet().clickAddMyDigitalID();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
            test.mobile().wallet().clickOnDocuments();
            test.mobile().wallet().clickToAddDocument();
            test.mobile().wallet().clickFromList();
            test.mobile().wallet().scrollUntilPID();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
        }

        if (pid_and_mdl_data) {
            test.mobile().wallet().checkIfPageIsTrue();
            test.mobile().wallet().createAPin();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().renterThePin();
            test.mobile().wallet().clickConfirm();
            test.mobile().wallet().successMessageOfSetUpPin();
            test.mobile().wallet().clickAddMyDigitalID();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
            test.mobile().wallet().clickOnDocuments();
            test.mobile().wallet().clickToAddDocument();
            test.mobile().wallet().clickFromList();
            test.mobile().wallet().clickMdl();
            test.mobile().issuer().clickCountrySelection();
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().clickFormEu();
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().enterFamilyName();
            test.mobile().issuer().enterGivenName();
            test.mobile().issuer().chooseBirthDate();
            test.mobile().issuer().enterDocumentNumber();
            test.mobile().issuer().clickScreen();
            test.mobile().issuer().scrollUntilFindDate();
            test.mobile().issuer().chooseIssueDate();
            test.mobile().issuer().chooseExpiryDate();
            test.mobile().issuer().scrollUntilFindSubmit();
            test.mobile().issuer().clickRemove();
            test.mobile().issuer().clickRemove();
            test.mobile().issuer().clickRemove();
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().scrollUntilAuthorize();
            test.mobile().issuer().clickAuthorize();
            test.mobile().wallet().clickClose();
            test.mobile().wallet().clickHome();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
        boolean ios = scenario.getSourceTagNames().contains("@IOS");
        if (android){
            test.stopAndroidDriverSession();
        }
        if (ios){
            test.stopIosDriverSession();
        }
        test.stopLogging();
    }
}
