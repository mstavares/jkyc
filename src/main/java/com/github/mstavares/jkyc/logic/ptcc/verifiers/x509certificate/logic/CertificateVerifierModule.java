package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.logic;

import com.google.inject.AbstractModule;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.logic.CRLVerifier;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.logic.CRLVerifierImpl;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.gateway.logic.RevocationGateway;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.gateway.logic.RevocationGatewayImpl;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.ocsp.OCSPVerifier;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.ocsp.OCSPVerifierImpl;

/**
 * This class is used by Guice to bind the interfaces with their implementations
 * @author Miguel Tavares
 */
public class CertificateVerifierModule extends AbstractModule {

    /**
     * This method binds the interfaces with its implementations
     */
    @Override
    protected void configure() {
        bind(CertificateVerifier.class).to(CertificateVerifierImpl.class);
        bind(RevocationGateway.class).to(RevocationGatewayImpl.class);
        bind(OCSPVerifier.class).to(OCSPVerifierImpl.class);
        bind(CRLVerifier.class).to(CRLVerifierImpl.class);
    }
}
