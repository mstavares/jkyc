package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.messages;

/**
 * This class provides messages related with certificate verifier module.
 * @author Miguel Tavares
 */
public enum X509CertificateMessages {

    ISSUER_ERROR_MSG("end entity certificate issuer not found");

    private final String description;

    X509CertificateMessages(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}

