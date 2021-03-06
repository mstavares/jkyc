package com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.logic;

import com.google.inject.AbstractModule;

/**
 * This class is used by Guice to bind the interfaces with their implementations
 * @author Miguel Tavares
 */
public class WalletVerifierModule extends AbstractModule {

    /**
     * This method binds the interfaces with its implementations
     */
    @Override
    protected void configure() {
        bind(WalletVerifier.class).to(WalletVerifierImpl.class);
    }

}
