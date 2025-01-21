package eu.europa.eudi.data;

public interface Literals {

    enum Wallet{

        WELCOME_HEADER("Welcome to the EUDI Wallet"),
        SUCCESS_MESSAGE("You successfully set the quick pin"),
        AUTHENTICATION_SUCCESS("You successfully shared information with EUDI Remote Verifier"),
        LOGIN("Login"),
        WELCOME_PAGE("Welcome to the EUDI Wallet"),
        USER_PROFIL("National ID"),
        NATIONAL_ID("National ID"),
        MDL("Driving License"),
        DASHBOARD_PAGE("Welcome back"),
        CORRESPONDING_MESSAGE("Your selection of data to be shared may impact the service"),
        ADD_DOCUMENT("Add document"),
        AUTHENTICATION_SELECTION("Authentication Method Selection"),
        DATA_PAGE("Enter the data for your EUDI Wallet"),
        SUCCESS_MESSAGE_PID("You can now add your National ID"),
        SUCCESS_MESSAGE_AGE_VERIFICATION("Success"),
        AGE_VERIFICATION_IS_DISPLAYED("Age Verification"),
        DRIVING_LICENCE("Driving License"),
        SUCCESS_MESSAGE_DRIVING_LICENCE("You can now add your Driving License"),
        ISSUANCE_DETAILS("National ID"),
        SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER("Your documents from issuer.eudiw.dev have been successfully issued."),
        SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER_IOS("Your documents from issuer.eudiw.dev have been successfully issued."),
        SCAN_QR("SCAN QR"),
        DETAILS_NATIONAL_ID("Family Name"),
        DETAILS_MDL("Family Name"),
        DETAILS_DOCUMENT("Family Name"),
        OPTIONAL_DATA("Family Name"),
        ACTUAL_DATA("ANDERSSON"),
        VERIFICATION_DETAILS("Date of issuance"),
        PIN_FIELD_IS_DISPLAYED("EUDI Remote Verifier requests the following"),
        THREE_DOT_MENU_IS_DISPLAYED("More options"),
        CHANGE_QUICK_PIN_IS_DISPLAYED("Change quick pin"),
        TYPO_RETRIEVE_LOGS("Retrrrieve logs"),
        PHOTO_ID_IS_DISPLAYED("Photo ID"),
        QR_SCANNER_IS_ACTIVATED("Scan QR"),
        TRANSACTION_CODE_REQUEST("Type the 5-digit transaction code you received"),
        SECOND_NATIONAL_ID("National ID");
        public final String label;
        Wallet(String label) {
            this.label = label;
        }
    }

    enum Verifier{

        VIEW_DATA_PAGE("Please review carefully before sharing your data. Why we need your data?"),
        VIEW_DATA_PAGE_IOS("Please review carefully before sharing your data. Why we need your data?"),

        APP_OPEN_SUCCESSFULLY("Define your presentation request"),
        AUTHENTICATION_PAGE("Proceed to authentication"),
        APP_OPEN_SUCCESSFULLY_IOS("1");

        public final String label;
        Verifier(String label) {
            this.label = label;
        }
    }

    enum General{
        WEB_DRIVER_NOT_INSTANCE_APPIUM_MESSAGE("WebDriver is not an instance of AppiumDriver"),
        ANDROID("android"),
        IOS("ios");
        public final String label;
        General(String label) {
            this.label = label;
        }
    }

    enum Issuer{
        CREDENTIAL_PAGE("Request Credentials for your EUDI Wallet"),
        QR_CODE("QR Code"),
        ISSUER_SERVICE("Testing OpenID for Verifiable Credential Issuance - draft 13"),
        AUTHENTICATION_PAGE("Authentication Method Selection"),
        FORM("Enter the data for your EUDI Wallet"),
        CONFIRM_PAGE("issuer.eudiw.dev wants to issue the following in your wallet"),
        AUTHORIZE_PAGE("Authorize data from your EUDI Wallet"),
        NATIONAL_ID("National ID"),
        CANCEL_ISSUANCE_PROCESS("Cancel issuance process?");

        public final String label;
        Issuer(String label) {
            this.label = label;
        }
    }
}
