package pt.mstavares.jkyc.logic.ptcc.verifiers.sod.logic;

import com.google.inject.AbstractModule;

/**
 * This class is used by Guice to bind the interfaces with their implementations
 * @author Miguel Tavares
 */
public class SODVerifierModule extends AbstractModule {

    /**
     * This method binds the interfaces with its implementations
     */
    @Override
    protected void configure() {
        bind(SODVerifier.class).to(SODVerifierImpl.class);
    }
}
