package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.ocsp;

import com.google.inject.AbstractModule;

/**
 * This class is used by Guice to bind the interfaces with their implementations
 * @author Miguel Tavares
 */
public class OCSPVerifierModule extends AbstractModule {

    /**
     * This method binds the interfaces with its implementations
     */
    @Override
    protected void configure() {
        bind(OCSPVerifier.class).to(OCSPVerifierImpl.class);
    }
}
