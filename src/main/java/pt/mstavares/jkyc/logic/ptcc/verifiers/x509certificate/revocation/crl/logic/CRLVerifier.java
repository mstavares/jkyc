package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.logic;

import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;

import java.security.cert.X509Certificate;

/**
 * This interface is used by Guice to inject CRLVerifierImpl objects
 * @author Miguel Tavares
 */
public interface CRLVerifier {

    /**
     * This method verifies through a CRL if a certain certificate is revoked or not
     * @param issuerCertificate certificate to be verified issuer
     * @param certificateToVerify certificate to be verified
     * @throws CertificateRevokedException
     */
    void verify(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws CertificateRevokedException;
}
