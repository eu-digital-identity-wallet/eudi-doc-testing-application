package eu.europa.eudi.data;

public interface Literals {

    enum Wallet {

        WELCOME_HEADER("Welcome to your Wallet"),
        SUCCESS_MESSAGE("Your wallet is secured!"),
        AUTHENTICATION_SUCCESS("You successfully shared the following information with"),
        LOGIN("Welcome back"),
        LOGIN_IOS("Login"),
        WELCOME_PAGE("Welcome to the EUDI Wallet"),
        USER_PROFIL("National ID"),

        PID("PID"),
        PID_IOS("eu_pid_doctype_name"),
        MDL("mDL"),
        MDL_IOS("mdl_doctype_name"),
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
        HOME_PAGE_IS_DISPLAYED("Welcome back, Foteini"),
        DETAILS_ARE_BLURRED("Hide"),
        DETAILS_ARE_NOT_BLURRED("Show"),
        CREDENTIALS_PROVIDER_DISPLAYED("Test Credentials Provider"),
        WELCOME_HEADER_IOS("Welcome to your wallet"),
        ADD_PID_PAGE("Choose a digital document from the list below to add to your wallet."),
        SUCCESS_MESSAGE_VERIFIER("You successfully shared the following information with"),
        VERIFICATION_IS_DISPLAYED("Digital Credentials Issuer requires verification"),
        DETAILS_OVER_18("eu.europa.ec.eudi.pseudonym.age_over_18.1"),
        DETAILS_FOR_DEFERRED("eu.europa.ec.eudi.pseudonym.age_over_18.deferred_endpoint"),
        SIGN_DOCUMENT("Sign Document"),
        SIGN_DOCUMENT_IOS("Sign document"),
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
        DOCUMENT_OPENED("Pseudonym Deferred"),
        INFORM_ATTESTATION("eu.europa.ec.eudi.pid.1"),
        INFORM_ATTESTATION_IOS("eu_pid_doctype_name"),
        DONE_BUTTON("Done");

        public final String label;

        Wallet(String label) {
            this.label = label;
        }
    }

    enum Verifier {

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

    enum General {
        ANDROID("android"),
        IOS("ios"),
        WEB("web");
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
        FORM("Test Provider Form"),
        SELECT_COUNTRY_IS_DISPLAYED("Please select your country of origin"),
        SUCCESSFULLY_SHARED("You have successfully added the following to your wallet"),
        SUCCESSFULLY_SHARED_IOS("You successfully shared the following information with"),
        AUTHORIZE_IS_DISPLAYED("Authorize data from your EUDI Wallet"),
        ISSUER_SERVICE_IS_DISPLAYED("Request Credentials for your EUDI Wallet"),
        TRANSACTION_CODE_IS_DISPLAYED("Transaction Code"),
        FORM_ANDROID("EUDI Wallet Credential"),
        FORM_IOS("Enter the data for your EUDI Wallet");

        public final String label;

        Issuer(String label) {
            this.label = label;
        }
    }

    enum WebApp {
        TEST_PLAN("OpenID for Verifiable Credential Issuance draft 16: Test an issuer (alpha version - may be incomplete or incorrect, please email certification@oidf.org)"),
        SENDER_CONSTRAINT("dpop"),
        CLIENT_AUTH_TYPE("private_key_jwt"),
        AUTH_CODE_FLOW("wallet_initiated"),
        AUTH_REQUEST_TYPE("simple"),
        REQUEST_METHOD("unsigned"),
        GRANT_TYPE("authorization_code"),
        METADATA_DISCOVERY("static"),
        FAPI_PROFILE("plain_fapi"),
        FAPI_RESPONSE_MODE("plain_response"),
        ALIAS("niscy"),
        PUBLISH("No"),
        CREDENTIAL_ISSUER_URL(getEnvOrDefault("CREDENTIAL_ISSUER_URL", "https://dev.issuer-backend.eudiw.dev")),
        CREDENTIAL_CONF_ID(getEnvOrDefault("CREDENTIAL_CONF_ID", "eu.europa.ec.eudi.pid_vc_sd_jwt")),
        CREDENTIAL_ISSUER_METADATA_URL("https://dev.issuer-backend.eudiw.dev/.well-known/openid-credential-issuer"),
        AUTH_SERVER_URL_OVERRIDE("https://dev.authenticate.eudiw.dev/realms/pid-issuer-realm"),
        CLIENT_ID("wallet-dev-2"),
        DPOP_SIGNING_ALG("ES256"),
        CREDENTIAL_OFFER_PARAMETER("by_value"),
        JWKS("""
                {
                    "keys": [
                        {
                            "kty": "EC",
                            "alg": "ES256",
                            "x5t#S256": "AXS9DLiBee4J9MqBQv3LMgSUzroWJ2aBvPNUci8x8ok",
                            "nbf": 1754570145,
                            "d": "QxlKkvuWlafFDlH-2_01l8PObA8V8iSdLaETLdJJyf8",
                            "crv": "P-256",
                            "kid": "vDb94EzNWD4UyfeWLRz9L6mUbamh_LTHxjdqo0u4FPw",
                            "x5c": [
                                "MIIBLTCB1KADAgECAhRSguV9DB+wjtsB2BPrnQGYhIe9PDAKBggqhkjOPQQDAjAXMRUwEwYDVQQDDAx3YWxsZXQtZGV2LTIwHhcNMjUwODA3MTIzNTQ1WhcNMzAwODA3MTIzNTQ1WjAXMRUwEwYDVQQDDAx3YWxsZXQtZGV2LTIwWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAAQoYiPaMdarrXgh15CIYLiqgxDBcPWqhiSgX5qxLtCTJ+gDgrDxrDq38CythMtOQ84yEs7O4XzWVV1NJZc2wZvgMAoGCCqGSM49BAMCA0gAMEUCIBwrjADf4vX9iNu5WC9WbwcdQ5AUwBpeWHEGlQsyZz0wAiEAtAUaUPT773lxssdu2Z7xMZDQ+u+C8aq0Bm6EAbsacBY="
                            ],
                            "x": "KGIj2jHWq614IdeQiGC4qoMQwXD1qoYkoF-asS7Qkyc",
                            "y": "6AOCsPGsOrfwLK2Ey05DzjISzs7hfNZVXU0llzbBm-A",
                            "exp": 1912336545
                        }
                    ]
                }
                """);


        public final String label;

        WebApp(String label) {
            this.label = label;
        }
    }
    private static String getEnvOrDefault(String envVarName, String defaultValue) {
        String value = System.getenv(envVarName);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }
}