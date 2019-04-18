package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.remote;

import com.google.inject.AbstractModule;

/**
 * This class is used by Guice to bind the interfaces with their implementations
 * @author Miguel Tavares
 */
public class CRLRemoteStorageModule extends AbstractModule {

    /**
     * This method binds the interfaces with its implementations
     */
    @Override
    protected void configure() {
        bind(CRLRemoteStorage.class).to(CRLRemoteStorageImpl.class);
    }

}
