package eu.europa.eudi.data;

public interface Literals {

    enum Wallet{

        WELCOME_HEADER("Welcome to your Wallet"),
        SUCCESS_MESSAGE("Your wallet is secured!"),
        AUTHENTICATION_SUCCESS("You successfully shared the following information with"),
        LOGIN("Welcome back!"),
        LOGIN_ANDROID("Welcome back"),

        PID("PID (MSO MDoc)"),
        DASHBOARD_PAGE("Welcome back, Nikos"),
        ADD_DOCUMENT("Add document"),
        SUCCESS_MESSAGE_DRIVING_LICENCE("You successfully shared the following information with"),
        SUCCESS_MESSAGE_DRIVING_LICENCE_ANDROID("You have successfully added the following to your wallet"),
        ISSUANCE_DETAILS("PID (MSO Mdoc)"),
        SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER("You have successfully added the following to your wallet"),
        PIN_FIELD_IS_DISPLAYED("PIN"),
        PIN_FIELD_IS_DISPLAYED_IOS("Enter your PIN"),
        DOCUMENTS_PAGE_IS_DISPLAYED("Documents"),
        HOME_PAGE_IS_DISPLAYED("Welcome back, Foteini"),
        WELCOME_HEADER_IOS("Welcome to your wallet"),
        PID_KOTLIN("PID (MSO MDoc)"),
        QR_SCANNER_IS_ACTIVATED_FOR_ISSUANCE("Scan a QR code provided from an issuer to add a digital document to your wallet."),
        MDL_KOTLIN("Mobile Driving Licence (MSO MDoc)"),
        PID_IOS("PID (MSO Mdoc)"),
        PID_SD_JWT("PID (SD-JWT VC)"),
        PID_SD_JWT_ANDROID("PID (SD-JWT VC Compact)"),
        PID_SD_JWT_ON_DOCUMENTS_ANDROID_PYTHON("PID (SD-JWT VC)"),
        PID_SD_JWT_ON_DOCUMENTS_ANDROID("PID (SD-JWT VC Compact)"),
        PID_SD_JWT_ON_DOCUMENTS_ANDROID_("PID (SD-JWT VC)"),
        PID_SD_JWT_ON_DOCUMENTS_IOS("PID (SD-JWT VC Compact)"),
        PID_SD_JWT_ON_DOCUMENTS_IOS_PYTHON("PID (SD-JWT VC)"),
        POP_UP_CONFIRMATION("Documents issued"),
        PENDING_STATUS("Pending"),
        PID_DEFERRED_DISPLAYED("PID (MSO Mdoc Deferred"),
        NOTIFICATION_DEFERRED_WALLET("In progress!");

        public final String label;
        Wallet(String label) {
            this.label = label;
        }
    }

    enum Verifier{

        VIEW_DATA_PAGE("Issuance request"),
        DATA_SHARING_REQUEST("Data sharing request"),
        APP_OPEN_SUCCESSFULLY("Define your presentation request"),
        WALLET_RESPONDED("eu.europa.ec.eudi.pid.1"),
        PRESENTATION_QUERY_TYPE("Request URI Method"),
        CHOOSE_WALLET_DISPLAYED("Invoke Wallet"),
        PID_IS_DISPLAYED_ON_WEB("Person Identification Data (PID)"),
        MDL_IS_DISPLAYED_ON_WEB_KOTLIN("Mobile Driving Licence (MDL)"),
        URI_METHOD_IS_DISPLAYED_ON_WEB("Request URI Method"),
        WALLET_RESPONDED_MDL_KOTLIN("org.iso.18013.5.1.mDL"),
        WALLET_RESPONDED_SDJWT("urn:eudi:pid:1"),
        TRANSACTION_CODE_PAGE("Digital Credentials Issuer requires verification"),
        DEFERRED_IS_DISPLAYED("PID (MSO Mdoc Deferred)");

        public final String label;
        Verifier(String label) {
            this.label = label;
        }
    }

    enum General{
        ANDROID("android"),

        IOS("ios_execution_with_browserstack.yml");
        public final String label;
        General(String label) {
            this.label = label;
        }
    }

    enum Issuer{
        CREDENTIAL_PAGE_IOS("1"),
        CREDENTIAL_PAGE("Please select credentials"),
        QR_CODE("QR Code"),
        AUTHENTICATION_PAGE("Authentication Method Selection"),
        SUCCESSFULLY_SHARED("You have successfully added the following to your wallet"),
        SUCCESSFULLY_SHARED_IOS("You successfully shared the following information with"),
        AUTHORIZE_IS_DISPLAYED("Please review the selected attributes before sending to the EudiWallet demo app."),
        FORM_IOS("1"),
        ISSUANCE_CREDENTIALS("Scan the generated QR Code to issue the requested Credentials:"),
        SIGN_IN_USER_PAGE("Sign in to your account"),
        SELECT_COUNTRY_IS_DISPLAYED("Please select your country of origin");


        public final String label;
        Issuer(String label) {
            this.label = label;
        }
    }
}
