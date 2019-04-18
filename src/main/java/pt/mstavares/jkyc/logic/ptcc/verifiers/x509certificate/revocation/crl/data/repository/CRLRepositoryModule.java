package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.repository;

import com.google.inject.AbstractModule;

/**
 * This class is used by Guice to bind the interfaces with their implementations
 * @author Miguel Tavares
 */
public class CRLRepositoryModule extends AbstractModule {

    /**
     * This method binds the interfaces with its implementations
     */
    @Override
    protected void configure() {
        bind(CRLRepository.class).to(CRLRepositoryImpl.class);
    }

}
