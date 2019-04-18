package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.remote;

import java.io.IOException;
import java.io.InputStream;

/**
 * This interface is used by Guice to inject CRLRemoteStorageImpl objects
 * @author Miguel Tavares
 */
public interface CRLRemoteStorage {

    /**
     * This method fetches a certain CRL by URL
     * @param crlURL CRL URL to be fetched
     * @return InputStream containing the CRL
     * @throws IOException
     */
    InputStream fetch(String crlURL) throws IOException;

}
