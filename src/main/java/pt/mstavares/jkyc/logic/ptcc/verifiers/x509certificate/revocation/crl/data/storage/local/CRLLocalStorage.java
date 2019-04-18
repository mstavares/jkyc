package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.local;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This interface is used by Guice to inject CRLLocalStorageImpl objects
 * @author Miguel Tavares
 */
public interface CRLLocalStorage {

    /**
     * This method stores locally a certain CRL
     * @param crlUrl CRL URL used to file name
     * @param inputStream content that will be written in the file
     * @throws IOException
     */
    void store(String crlUrl, InputStream inputStream) throws IOException;

    /**
     * This method deletes a certain CRL stored locally
     * @param crlURL crlURL to be deleted
     */
    void delete(String crlURL);

    /**
     * This method fetches a certain CRL by URL
     * @param crlUrl CRL URL to be fetched
     * @return ByteArrayInputStream containing the CRL
     * @throws IOException
     */
    ByteArrayInputStream fetch(String crlUrl) throws IOException;

}
