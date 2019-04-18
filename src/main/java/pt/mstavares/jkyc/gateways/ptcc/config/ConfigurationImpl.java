package pt.mstavares.jkyc.gateways.ptcc.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import pt.mstavares.jkyc.utilities.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * This class provides configurations to the library, such as keystore file path and password
 * @author Miguel Tavares
 */
public class ConfigurationImpl implements Configuration {

    /** This list contains all certificates that are store in the keystore */
    private List<X509Certificate> certificateChain;

    /** This variable references the keystore where the certificates are stored */
    private KeyStore keystore;

    /**
     * This is the constructor of ConfigurationImpl class
     * @param keystorePath keystore file path
     * @param keystorePassword keystore password
     * @throws IOException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     */
    @Inject
    public ConfigurationImpl(@Named("keystorePath") String keystorePath, @Named("keystorePassword") String keystorePassword)
            throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        Logger.logInfo(ConfigurationImpl.class.getSimpleName(), keystorePath);
        keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());
        certificateChain = fetchX509Certificates();
    }

    /**
     * This method provides all the certificates that are stored in the keystore
     * @return list of certificates stored in the keystore
     */
    @Override
    public List<X509Certificate> getX509Certificates() {
        return certificateChain;
    }

    /**
     * Fetches all certificates in stored in the keystore and place them in a list
     * @return list of certificates stored in the keystore
     * @throws KeyStoreException
     */
    private List<X509Certificate> fetchX509Certificates() throws KeyStoreException {
        Enumeration aliases = keystore.aliases();
        List<X509Certificate> certificates = new ArrayList<>();
        while(aliases.hasMoreElements()) {
            String alias = aliases.nextElement().toString();
            Logger.logInfo(ConfigurationImpl.class.getSimpleName(), alias);
            certificates.add((X509Certificate) keystore.getCertificate(alias));
        }
        return certificates;
    }

}
