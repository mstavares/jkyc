package pt.mstavares.jkyc.gateways.ptcc.config;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import pt.mstavares.jkyc.gateways.ptcc.logic.PortugueseCC;
import pt.mstavares.jkyc.gateways.ptcc.logic.PortugueseCCImpl;
import pt.mstavares.jkyc.logic.ptcc.verifiers.sod.logic.SODVerifier;
import pt.mstavares.jkyc.logic.ptcc.verifiers.sod.logic.SODVerifierImpl;
import pt.mstavares.jkyc.logic.ptcc.verifiers.wallet.logic.WalletVerifier;
import pt.mstavares.jkyc.logic.ptcc.verifiers.wallet.logic.WalletVerifierImpl;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.logic.CertificateVerifier;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.logic.CertificateVerifierImpl;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.repository.CRLRepository;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.repository.CRLRepositoryImpl;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.local.CRLLocalStorage;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.local.CRLLocalStorageImpl;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.remote.CRLRemoteStorage;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.remote.CRLRemoteStorageImpl;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.logic.CRLVerifier;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.logic.CRLVerifierImpl;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.gateway.logic.RevocationGateway;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.gateway.logic.RevocationGatewayImpl;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.ocsp.OCSPVerifier;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.ocsp.OCSPVerifierImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is used by Guice to bind the interfaces with their implementations
 * @author Miguel Tavares
 */
public class ConfigurationModule extends AbstractModule {

    /** This variable stores the properties read from the configuration file */
    private Properties properties = new Properties();

    /**
     * ConfigurationModule's constructor
     * @param configFilePath this variable contains the path to the configuration file which is injected through the
     *                       environment variable KYC_CONFIG
     * @throws IOException
     */
    public ConfigurationModule(final String configFilePath) throws IOException {
        properties.load(new FileInputStream(configFilePath));
        properties.setProperty("configPath", configFilePath);
    }

    /**
     * This method does the binding between interfaces and its implementations
     */
    @Override
    protected void configure() {
        Names.bindProperties(binder(), properties);
        bind(Configuration.class).to(ConfigurationImpl.class);
        bind(PortugueseCC.class).to(PortugueseCCImpl.class);
        bind(CertificateVerifier.class).to(CertificateVerifierImpl.class);
        bind(RevocationGateway.class).to(RevocationGatewayImpl.class);
        bind(OCSPVerifier.class).to(OCSPVerifierImpl.class);
        bind(CRLVerifier.class).to(CRLVerifierImpl.class);
        bind(CRLRepository.class).to(CRLRepositoryImpl.class);
        bind(CRLLocalStorage.class).to(CRLLocalStorageImpl.class);
        bind(CRLRemoteStorage.class).to(CRLRemoteStorageImpl.class);
        bind(WalletVerifier.class).to(WalletVerifierImpl.class);
        bind(SODVerifier.class).to(SODVerifierImpl.class);
    }

}
