package pt.mstavares.jkyc.gateways.ptcc.config;

import java.security.cert.X509Certificate;
import java.util.List;

/**
 * This interface is used by Guice to inject ConfigurationImpl objects
 * @author Miguel Tavares
 */
public interface Configuration {

    /**
     * This method returns all the certificates stored in the KYC keystore
     * @return list of all certificates in KYC keystore
     */
    List<X509Certificate> getX509Certificates();

}
