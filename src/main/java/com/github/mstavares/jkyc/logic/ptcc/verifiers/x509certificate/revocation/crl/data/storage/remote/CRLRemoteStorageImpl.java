package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.storage.remote;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This class is used to fetch CRLs remotely
 * @author Miguel Tavares
 */
public class CRLRemoteStorageImpl implements CRLRemoteStorage {

    /**
     * This method fetches a certain CRL by URL
     * @param crlURL CRL URL to be fetched
     * @return InputStream containing the CRL
     * @throws IOException
     */
    @Override
    public InputStream fetch(String crlURL) throws IOException {
        return new BufferedInputStream(new URL(crlURL).openStream());
    }

}
