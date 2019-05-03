package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.logic;

import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.models.CertificationPath;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateChainException;

/**
 * This interface is used by Guice to inject CertificateVerifierImpl objects
 * @author Miguel Tavares
 */
public interface CertificateVerifier {

    /**
     * This method does the end-entity certificate verification
     * @param certificationPath certification path to being verified
     * @param revocationEnabled true to verify revocation, false if not
     * @throws CertificateChainException
     * @throws CertificateRevokedException
     */
    void verify(CertificationPath certificationPath, boolean revocationEnabled)
            throws CertificateChainException, CertificateRevokedException;
}
