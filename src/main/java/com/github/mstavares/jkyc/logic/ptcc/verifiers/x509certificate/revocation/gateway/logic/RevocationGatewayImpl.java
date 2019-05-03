package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.gateway.logic;

import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.logic.CRLVerifier;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.ocsp.OCSPVerifier;
import com.google.inject.Inject;
import com.github.mstavares.jkyc.utilities.Logger;

import java.io.IOException;
import java.security.cert.X509Certificate;

/**
 * This class is used to dispatch the revoke verification requests
 */
public class RevocationGatewayImpl implements RevocationGateway {

    /** This attribute is used to verify a certain certificate through a OCSP request */
    private final OCSPVerifier ocspVerifier;

    /** This attribute is used to verify a certain certificate through a CRL */
    private final CRLVerifier crlVerifier;

    /**
     * This is the RevocationGatewayImpl's contructor
     * @param ocspVerifier OCSP Verifier used to verify a certain certificate
     * @param crlVerifier CRL Verifier used to verify a certain certificate
     */
    @Inject
    public RevocationGatewayImpl(OCSPVerifier ocspVerifier, CRLVerifier crlVerifier) {
        this.ocspVerifier = ocspVerifier;
        this.crlVerifier = crlVerifier;
    }

    /**
     * This method dispatches the revocation request to OCSP Verifier, if this service is not available, then the request is
     * dispatched to CRL Verifier.
     * @param issuerCertificate issuer's certificate
     * @param certificateToVerify certificate to being verified
     * @throws CertificateRevokedException
     */
    @Override
    public void verify(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws CertificateRevokedException {
        try {
            verifyOCSP(issuerCertificate, certificateToVerify);
        } catch (IOException e) {
            Logger.logError(RevocationGatewayImpl.class.getSimpleName(), "OCSP verification failure, let me try CRL");
            verifyCRL(issuerCertificate, certificateToVerify);
        }
    }

    /**
     * This method requests the certificate verification to the OCSP Verifier service
     * @param issuerCertificate issuer's certificate
     * @param certificateToVerify certificate to being verified
     * @throws CertificateRevokedException
     * @throws IOException
     */
    @Override
    public void verifyOCSP(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws CertificateRevokedException, IOException {
        Logger.logInfo(RevocationGatewayImpl.class.getSimpleName(), "Verifying certificate status through OCSP");
        ocspVerifier.verify(issuerCertificate, certificateToVerify);
    }

    /**
     * This method requests the certificate verification to the CRL Verifier service
     * @param issuerCertificate issuer's certificate
     * @param certificateToVerify certificate to being verified
     * @throws CertificateRevokedException
     */
    @Override
    public void verifyCRL(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws CertificateRevokedException {
        Logger.logError(RevocationGatewayImpl.class.getSimpleName(), "Verifying certificate status through CRL");
        crlVerifier.verify(issuerCertificate, certificateToVerify);
    }


}
