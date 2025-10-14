package eu.europa.eudi.utils.factory;

import eu.europa.eudi.pages.Issuer;
import eu.europa.eudi.pages.Verifier;
import eu.europa.eudi.pages.Wallet;
import eu.europa.eudi.utils.TestSetup;

public class MobilePageObjectFactory {
    TestSetup test;
    Wallet wallet;
    Verifier verifier;
    Issuer issuer;
    Conformance conformance;

    public MobilePageObjectFactory(TestSetup test) {
        this.test = test;
    }

    public Wallet wallet() {
        return (wallet == null) ? wallet = new Wallet(test) : wallet;
    }

    public Verifier verifier() {
        return (verifier == null) ? verifier = new Verifier(test) : verifier;
    }

    public Issuer issuer() {
        return (issuer == null) ? issuer = new Issuer(test) : issuer;
    }

    public Conformance conformance() {return (conformance == null) ? conformance = new Conformance(test) : conformance;
    }
}
