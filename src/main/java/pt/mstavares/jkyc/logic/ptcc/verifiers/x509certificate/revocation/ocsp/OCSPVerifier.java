package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.ocsp;

import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;

import java.io.IOException;
import java.security.cert.X509Certificate;

/**
 * This interface is used by Guice to inject OCSPVerifierImpl objects
 * @author Miguel Tavares
 */
public interface OCSPVerifier {

    /**
     * This method is used to verify through a OCSP request if a certain certificate is revoked or not
     * @param issuerCertificate issuer's certificate
     * @param certificateToVerify certificate to verify
     * @throws CertificateRevokedException
     * @throws IOException
     */
    void verify(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws CertificateRevokedException, IOException;
}
