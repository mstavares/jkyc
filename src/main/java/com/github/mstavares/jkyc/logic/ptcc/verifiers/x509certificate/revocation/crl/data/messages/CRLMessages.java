package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.messages;

/**
 * This class provides messages related with CRL cache module.
 * @author Miguel Tavares
 */
public enum CRLMessages {

    CRL_INVALID_CACHE("Cached CRL outdated.");

    private final String description;

    CRLMessages(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
