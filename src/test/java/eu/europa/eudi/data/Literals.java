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

        PID("eu.europa.ec.eudi.pid.1"),
        PID_demo("PID"),
        PID_IOS("eu_pid_doctype_name"),
        MDL("mDL"),
        MDL_IOS("mdl_doctype_name"),
        DASHBOARD_PAGE("Welcome back, Foteiniiii"),
        CORRESPONDING_MESSAGE("Choosing not to share certain data may result in failure to issue the requested document."),
        ADD_DOCUMENT("Add document"),
        SUCCESS_MESSAGE_PID("You can now add your National ID"),
        DRIVING_LICENCE("Driving License"),
        SUCCESS_MESSAGE_DRIVING_LICENCE("You successfully shared the following information with"),
        SUCCESS_MESSAGE_DRIVING_LICENCE_ANDROID("You have successfully added the following to your wallet"),
        ISSUANCE_DETAILS("PID"),
        SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER("You have successfully added the following to your wallet"),
        SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER_IOS("You successfully shared the following information with"),
        SCAN_QR("SCAN QR"),
        DETAILS_NATIONAL_ID("Family Name"),
        DETAILS_MDL("Family name"),
        DETAILS_DOCUMENT("Family Name(s)"),
        OPTIONAL_DATA("Family Name"),
        ACTUAL_DATA("ANDERSSON"),
        VERIFICATION_DETAILS("Birth Date"),
        PIN_FIELD_IS_DISPLAYED("PIN"),
        PIN_FIELD_IS_DISPLAYED_IOS("Enter your PIN to share data"),
        DOCUMENTS_PAGE_IS_DISPLAYED("Documents"),
        HOME_PAGE_IS_DISPLAYED("Welcome back, Foteini"),
        DETAILS_ARE_BLURRED("Hide"),
        DETAILS_ARE_NOT_BLURRED("Show"),
        CREDENTIALS_PROVIDER_DISPLAYED("Test Credentials Provider"),
        WELCOME_HEADER_IOS("Welcome to your wallet"),
        ADD_PID_PAGE("Choose a digital document from the list below to add to your wallet."),
        SUCCESS_MESSAGE_VERIFIER("You successfully shared the following information with");

        public final String label;
        Wallet(String label) {
            this.label = label;
        }
    }

    enum Verifier{

        VIEW_DATA_PAGE("The following transaction requires your permission and authentication."),
        APP_OPEN_SUCCESSFULLY("Define your presentation request"),
        AUTHENTICATION_PAGE("Proceed to authentication"),
        APP_OPEN_SUCCESSFULLY_IOS("Define your presentation request"),
        WALLET_RESPONDED("eu.europa.ec.eudi.pid.1"),
        PRESENTATION_QUERY_TYPE("Request URI Method"),
        CHOOSE_WALLET_DISPLAYED("Invoke Wallet");

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
        CREDENTIAL_PAGE_IOS("1"),
        CREDENTIAL_PAGE("Request Credentials for your EUDI Wallet"),
        QR_CODE("QR Code"),
        ISSUER_SERVICE("Testing OpenID for Verifiable Credential Issuance - draft 13"),
        AUTHENTICATION_PAGE("Authentication Method Selection"),
        FORM("Test Provider Form"),
        SELECT_COUNTRY_IS_DISPLAYED("Please select your country of origin"),
        SUCCESSFULLY_SHARED("You have successfully added the following to your wallet"),
        SUCCESSFULLY_SHARED_IOS("You successfully shared the following information with"),
        AUTHORIZE_IS_DISPLAYED("Authorize data from your EUDI Wallet"),
        AUTHORIZE_IS_DISPLAYED_DEV("Review & Send"),
        ISSUER_SERVICE_IS_DISPLAYED("Request Credentials for your EUDI Wallet"),
        TRANSACTION_CODE_IS_DISPLAYED("Transaction Code"),
        FORM_ANDROID("EUDI Wallet Credential"),
        FORM_IOS("Enter the data for your EUDI Wallet"),
        FORM_DEV("For testing purposes only."),
        SELECT_COUNTRY_IS_DISPLAYED_DEV("Please select your country of origin");

        public final String label;
        Issuer(String label) {
            this.label = label;
        }
    }
}
