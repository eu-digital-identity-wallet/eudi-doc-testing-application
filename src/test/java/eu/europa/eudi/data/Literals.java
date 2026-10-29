package eu.europa.eudi.data;

public interface Literals {

    enum Wallet{

        WELCOME_HEADER("Welcome to your Wallet"),
        WELCOME_SUBTITLE("Secure your wallet with a PIN code and connect to your National System."),
        TYPE_A_PIN_LABEL("Type a PIN"),
        CONFIRM_PIN_LABEL("Confirm PIN"),
        SUCCESS_MESSAGE("Your wallet is secured!"),
        AUTHENTICATION_SUCCESS("You successfully shared the following information with"),
        LOGIN("Welcome back!"),
        LOGIN_ANDROID("Welcome back"),
        PID("PID (MSO MDoc)"),
        PID_PYTHON("PID (MSO Mdoc)"),
        DASHBOARD_PAGE("Welcome back, Nikos"),
        ADD_DOCUMENT("Add document"),
        SUCCESS_MESSAGE_DRIVING_LICENCE("You successfully shared the following information with"),
        SUCCESS_MESSAGE_DRIVING_LICENCE_ANDROID("You have successfully added the following to your wallet"),
        ISSUANCE_DETAILS("PID (MSO Mdoc)"),
        SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER("You have successfully added the following to your wallet"),
        PIN_FIELD_IS_DISPLAYED("PIN"),
        PIN_FIELD_IS_DISPLAYED_IOS("Enter your PIN"),
        DOCUMENTS_PAGE_IS_DISPLAYED("Documents"),
        HOME_PAGE_IS_DISPLAYED("Welcome"),
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
        PID_DEFERRED_DISPLAYED("PID (MSO Mdoc Deferred)"),
        PID_DEFERRED_DISPLAYED_KOTLIN("PID (MSO MDoc) (deferred)"),
        NOTIFICATION_DEFERRED_WALLET("In progress!"),
        DASHBOARD_PAGE_DEFERRED("Welcome"),
        NOTIFICATION_DEFERRED_WALLET_IOS("In progress"),
        COUNTER_DISPLAYED("30/30"),
        COUNTER_DISPLAYED_KOTLIN("5/5"),
        AUTHENTICATION_ERROR("Invalid pin"),
        SUCCESSFULLY_SIGNED("You have successfully signed your document."),
        DASHBOARD_PAGE_SECOND("Welcome, Nikos"),
        DASHBOARD_PAGE_SECOND_KOTLIN("Welcome, Tyler"),
        TRUST_ICON("Verified"),
        TRUST_ICON_IOS("verifiedUser"),
        HOME("Home"),
        HISTORY(""),
        AUTHENTICATE("Authenticate"),
        SIGN("Sign"),
        IN_PERSON("In person"),
        SIGN_DOCUMENT("Sign document"),
        ONLINE("Online"),
        HISTORY_IOS("History"),
        HOME_IOS("home"),
        NAME_OF_ISSUER_DISPLAYED("Digital Credentials Issuer"),
        CHANGE_PIN("Change pin"),
        CANCEL_PIN("Cancel pin changing?"),
        TYPE_A_PIN("Type a PIN"),
        AUTHENTICATE_ME_IDENTITY("Authenticate my identity"),
        SCANQRText("Scan a QR code provided from an interacting party to authenticate your identity."),
        HISTORY_TAB("History"),
        SEARCH("Search"),
        FILTER_BY("Filter by"),
        EXPAND_FILTER_SECTION("Before today (expired)"),
        COUNTER_DISPLAYED_KOTLIN_ON_DETAILS("Instances remaining 5/5"),
        DOCUMENTS_PAGE_IS_DISPLAYED_IOS("Documents"),
        EU_IS_DISPLAYED("EU Digital Identity Wallet"),
        TRANSACTION_INFORMATION("Transaction information"),
        FOR_TESTING_PURPOSES("For Testing Purposes"),
        INTETED_USE("Intended use"),
        URL_PRIVACY("http://data.europa.eu/eudi/policy/trust-service-practice-statement"),
        DATA_SHARED("DATA SHARED"),
        FAMILY_NAME_HISTORY("[\"family_name\"]"),
        GIVEN_NAME_HISTORY("[\"given_name\"]"),
        BIRTH_DATE("[\"birthdate\"]"),
        INITIATE_DELETION("Initiate deletion request"),
        INFORMATIONAL_SCREEN("Before you continue"),
        CLARIFY_FOR_COMMUNICATION_CHANEL("You're about to leave the wallet and go to NiScy’s website, where you can fill in a form to request deletion of the data you shared."),
        CLARIFY_FOR_COMMUNICATION_CHANEL_IOS("You’re about to leave the wallet and go to NiScy’s website, where you can fill in a form to request deletion of the data you shared."),
        THE_RELYING_PARTY_IS_RESPONSIBLE("NiScy may still be required to keep some of your data for legal reasons, even after your request. Any updates on the outcome will come from NiScy directly, not the wallet."),
        BUTTON_TO_WEBSITE("Continue to NiScy’s website"),
        PRIVACY_POLICY("Privacy policy"),
        NOTES_FOR_REQUEST("Before you continue"),
        DATA_DELETION_REQUEST_INITIATION("View previous data deletion requests (1)"),
        PREVIOUS_DATA_DELETION_IS_DISPLAYED("Previous data deletion requests for NiScy"),
        TRUSTMARK_IS_DISPLAYED("Officially Certified And Trusted EUDI Wallet"),
        TRUSTMARK_CONTENT_IS_DISPLAYED("This wallet application has been certified conformant with the EUDI Wallet’s security and privacy requirements and is part of the EUDI Wallet Provider Trusted List[ ]Open new."),
        TRUSTMARK_CONTENT_IS_DISPLAYED_GERMAN("Beispieltext-für-Benutzer"),
        EUROPEAN_COMMISSION_IS_DISPLAYED("The EU Digital Identity Framework"),
        LINK_TO_WALLET_SOLUTION_INFORMATION("See the Certification information page Open new for detailed certification information of this wallet.");


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
        DEFERRED_IS_DISPLAYED("PID (MSO Mdoc Deferred)"),
        DEFERRED_IS_DISPLAYED_KOTLIN("PID (MSO MDoc) (deferred)"),
        DEFERRED_IS_DISPLAYED_IOS("PID (MSO Mdoc deferred)"),
        VERIFIER_ASKS_PERMISION("verifier.eudiw.dev wants to use personal info from your digital wallet."),
        NO_ATTESTATION_FOUND("Your info wasn’t found"),
        CREDENTIAL_TYPE("PID (MSO Mdoc)"),
        WALLET_UNIT("EUDI Wallet"),
        CREDENTIAL_TYPE_KOTLIN("PID (MSO MDoc)");

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
        SUCCESSFULLY_SHARED("You successfully shared the following information with"),
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
