package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.repository;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;

/**
 * This interface is used by Guice to inject CRLRepositoryImpl objects
 * @author Miguel Tavares
 */
public interface CRLRepository {

    /**
     * This method is used to store a certain CRL locally
     * @param crlURL CRL file name
     * @param inputStream contains the data to be written
     * @throws IOException
     */
    void store(String crlURL, InputStream inputStream) throws IOException;

    /**
     * This method is used to fetch a certain CRL by file name
     * @param crlURL CRL file name
     * @return InputStream with CRL content
     * @throws IOException
     * @throws CertificateException
     * @throws CRLException
     */
    InputStream fetch(String crlURL) throws IOException, CertificateException, CRLException;

}
