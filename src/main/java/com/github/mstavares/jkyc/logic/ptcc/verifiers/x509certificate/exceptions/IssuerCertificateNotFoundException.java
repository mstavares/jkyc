package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions;

import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.messages.X509CertificateMessages;

/**
 * This exception is thrown if a there is no issuer for a certain end entity certificate
 * @author Miguel Tavares
 */
public class IssuerCertificateNotFoundException extends Exception {

    public IssuerCertificateNotFoundException() {
        super(X509CertificateMessages.ISSUER_ERROR_MSG.toString());
    }
}
