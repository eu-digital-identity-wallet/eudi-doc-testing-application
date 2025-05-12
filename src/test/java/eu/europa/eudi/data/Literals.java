package eu.europa.eudi.data;

public interface Literals {

    enum Wallet{

        WELCOME_HEADER("Welcome to your Wallet"),
        SUCCESS_MESSAGE("Your wallet is secured!"),
        AUTHENTICATION_SUCCESS("You successfully shared the following information with"),
        LOGIN("Welcome back"),
        LOGIN_IOS("Login"),
        WELCOME_PAGE("Welcome to the EUDI Wallet"),
        USER_PROFIL("National ID"),

        PID("PID"),
        MDL("mDL"),
        DASHBOARD_PAGE("Welcome back, Foteini"),
        CORRESPONDING_MESSAGE("Your selection of data to be shared may impact the service"),
        ADD_DOCUMENT("Add document"),
        AUTHENTICATION_SELECTION("Authentication Method Selection"),
        DATA_PAGE("Enter the data for your EUDI Wallet"),
        SUCCESS_MESSAGE_PID("You can now add your National ID"),
        DRIVING_LICENCE("Driving License"),
        SUCCESS_MESSAGE_DRIVING_LICENCE("You successfully shared the following information with"),
        ISSUANCE_DETAILS("National ID"),
        SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER("Your documents from issuer.eudiw.dev have been successfully issued."),
        SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER_IOS("Your documents from issuer.eudiw.dev have been successfully issued."),
        SCAN_QR("SCAN QR"),
        DETAILS_NATIONAL_ID("Family Name"),
        DETAILS_MDL("Family name"),
        DETAILS_DOCUMENT("Family Name(s)"),
        OPTIONAL_DATA("Family Name"),
        ACTUAL_DATA("ANDERSSON"),
        VERIFICATION_DETAILS("Birth Date"),
        PIN_FIELD_IS_DISPLAYED("Enter your PIN to share data"),
        DOCUMENTS_PAGE_IS_DISPLAYED("Documents"),
        DETAILS_FAMILY_NAME("Family Name(s)"),
        DETAILS_GIVEN_NAME("Given Name(s)"),
        DETAILS_BIRTH_DATE("Birth Date"),
        HOME_PAGE_IS_DISPLAYED("Welcome back, Foteini"),
        DETAILS_ARE_BLURRED("Hide"),
        DETAILS_ARE_NOT_BLURRED("Show"),
        CREDENTIALS_PROVIDER_DISPLAYED("Test Credentials Provider");

        public final String label;
        Wallet(String label) {
            this.label = label;
        }
    }

    enum Verifier{

        VIEW_DATA_PAGE("Please review carefully before sharing your data. Why we need your data?"),
        VIEW_DATA_PAGE_IOS("The following transaction requires your permission and authentication."),
        APP_OPEN_SUCCESSFULLY("Define your presentation request"),
        AUTHENTICATION_PAGE("Proceed to authentication"),
        APP_OPEN_SUCCESSFULLY_IOS("Define your presentation request"),
        WALLET_RESPONDED("eu.europa.ec.eudi.pid.1");

        public final String label;
        Verifier(String label) {
            this.label = label;
        }
    }

    enum General{
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
        SELECT_COUNTRY_IS_DISPLAYED("Please select your country of origin"),
        SUCCESSFULLY_SHARED("You have successfully added the following to your wallet"),
        SUCCESSFULLY_SHARED_IOS("You successfully shared the following information with");

        public final String label;
        Issuer(String label) {
            this.label = label;
        }
    }
}
