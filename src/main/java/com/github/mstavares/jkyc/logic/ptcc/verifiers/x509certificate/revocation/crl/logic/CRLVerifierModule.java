package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.logic;

import com.google.inject.AbstractModule;

/**
 * This class is used by Guice to bind the interfaces with their implementations
 * @author Miguel Tavares
 */
public class CRLVerifierModule extends AbstractModule {

    /**
     * This method binds the interfaces with its implementations
     */
    @Override
    protected void configure() {
        bind(CRLVerifier.class).to(CRLVerifierImpl.class);
    }
}
