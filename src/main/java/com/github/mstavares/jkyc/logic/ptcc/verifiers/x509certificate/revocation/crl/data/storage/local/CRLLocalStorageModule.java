package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.local;

import com.google.inject.AbstractModule;

/**
 * This class is used by Guice to bind the interfaces with their implementations
 * @author Miguel Tavares
 */
public class CRLLocalStorageModule extends AbstractModule {

    /**
     * This method binds the interfaces with its implementations
     */
    @Override
    protected void configure() {
        bind(CRLLocalStorage.class).to(CRLLocalStorageImpl.class);
    }
}
