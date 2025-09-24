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
        CORRESPONDING_MESSAGE("Choosing not to share certain data may result in failure to issue the requested document."),
        ADD_DOCUMENT("Add document"),
        AUTHENTICATION_SELECTION("Authentication Method Selection"),
        DATA_PAGE("Enter the data for your EUDI Wallet"),
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
        DETAILS_FAMILY_NAME("Family Name(s)"),
        DETAILS_GIVEN_NAME("Given Name(s)"),
        DETAILS_BIRTH_DATE("Birth Date"),
        HOME_PAGE_IS_DISPLAYED("Welcome back, Foteini"),
        DETAILS_ARE_BLURRED("Hide"),
        DETAILS_ARE_NOT_BLURRED("Show"),
        CREDENTIALS_PROVIDER_DISPLAYED("Test Credentials Provider"),
        WELCOME_HEADER_IOS("Welcome to your wallet"),
        ADD_PID_PAGE("Add document from list"),
        SUCCESS_MESSAGE_VERIFIER("You successfully shared the following information with"),
        CLOSE_BUTTON("Close"),
        VERIFICATION_IS_DISPLAYED("Digital Credentials Issuer requires verification"),
        DETAILS_OVER_18("Age over 18 Pseudonym"),
        DETAILS_FOR_DEFERRED("Pseudonym Deferred"),
        SIGN_DOCUMENT("Sign Document"),
        SELECT_SIGNING("Select signing service"),
        SIGNING_SERVICES("Signing services"),
        CANCEL_SIGNING_PROCESS("Cancel signing process?"),
        SELECT_SIGNING_CERTIFICATE("Select signing certificate"),
        SUCCESS_SCREEN_WITH_SIGNED_DOCUMENT("You have successfully signed your document."),
        SHARE_BUTTON("Share"),
        PIN_PAGE("To approve the transaction you need to verify your identity."),
        TEST_PROVIDER_FORM("Test Provider Form"),
        AGE_OVER_18("Age over 18 Pseudonym"),
        IS_ON_WALLET("In progress!"),
        MESSAGE_IN_PROGRESS("Your documents from Digital Credentials Issuer have been requested. You will be notified when they have been issued to your wallet."),
        MESSAGE_IN_PENDING("Pending"),
        ISSUANCE_FAILED("//android.widget.TextView[@text=\"10/10\"]"),
        INSTANCE_INITIAL("10/10"),
        DOCUMENT_ISSUED("Documents issued"),
        DOCUMENT_OPENED("Pseudonym Deferred");

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
        PRESENTATION_QUERY_TYPE("Presentation Query Type"),
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
        CREDENTIAL_PAGE("Request Credentials for your EUDI Wallet"),
        CREDENTIAL_PAGE_IOS("1"),
        QR_CODE("QR Code"),
        ISSUER_SERVICE("Testing OpenID for Verifiable Credential Issuance - draft 13"),
        AUTHENTICATION_PAGE("Authentication Method Selection"),
        FORM(" demo application."),
        SELECT_COUNTRY_IS_DISPLAYED("Please select your country of origin"),
        SUCCESSFULLY_SHARED("You have successfully added the following to your wallet"),
        SUCCESSFULLY_SHARED_IOS("You successfully shared the following information with"),
        AUTHORIZE_IS_DISPLAYED("Review & Send"),
        ISSUER_SERVICE_IS_DISPLAYED("Request Credentials for your EUDI Wallet"),
        TRANSACTION_CODE_IS_DISPLAYED("Transaction Code");

        public final String label;
        Issuer(String label) {
            this.label = label;
        }
    }
}
