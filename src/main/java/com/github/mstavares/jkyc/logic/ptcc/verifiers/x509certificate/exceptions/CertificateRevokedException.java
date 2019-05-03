package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions;

/**
 * This exception is thrown when a certain certificate is detected as revoked
 * @author Miguel Tavares
 */
public class CertificateRevokedException extends Exception {

    public CertificateRevokedException(final String message) {
        super(message);
    }
}
