package eu.europa.eudi.utils.factory;

import eu.europa.eudi.pages.*;
import eu.europa.eudi.utils.TestSetup;
import org.springframework.beans.factory.annotation.Autowired;


public class MobilePageObjectFactory {
    TestSetup test;
    Wallet wallet;
    Verifier verifier;
    Issuer issuer;

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
}
