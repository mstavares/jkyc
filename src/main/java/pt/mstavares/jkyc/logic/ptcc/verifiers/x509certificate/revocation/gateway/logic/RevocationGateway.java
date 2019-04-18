package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.gateway.logic;

import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;

import java.io.IOException;
import java.security.cert.X509Certificate;

/**
 * This interface is used by Guice to inject RevocationGatewayImpl objects
 * @author Miguel Tavares
 */
public interface RevocationGateway {

    /**
     * This method dispatches the revocation request to OCSP Verifier, if this service is not available, then the request is
     * dispatched to CRL Verifier.
     * @param issuerCertificate issuer's certificate
     * @param certificateToVerify certificate to being verified
     * @throws CertificateRevokedException
     */
    void verify(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws CertificateRevokedException;

    /**
     * This method requests the certificate verification to the OCSP Verifier service
     * @param issuerCertificate issuer's certificate
     * @param certificateToVerify certificate to being verified
     * @throws CertificateRevokedException
     * @throws IOException
     */
    void verifyOCSP(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws CertificateRevokedException, IOException;

    /**
     * This method requests the certificate verification to the CRL Verifier service
     * @param issuerCertificate issuer's certificate
     * @param certificateToVerify certificate to being verified
     * @throws CertificateRevokedException
     */
    void verifyCRL(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws CertificateRevokedException;

}

