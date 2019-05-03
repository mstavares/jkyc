package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.logic;

import com.google.inject.Inject;
import com.github.mstavares.jkyc.logic.ptcc.utilities.X509CertificateUtilities;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateChainException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.models.CertificationPath;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.gateway.logic.RevocationGateway;
import com.github.mstavares.jkyc.utilities.Logger;

import java.security.GeneralSecurityException;
import java.security.cert.*;
import java.util.Set;

/**
 * This class is used to verify end-entity certificates
 * @author Miguel Tavares
 */
public class CertificateVerifierImpl implements CertificateVerifier {

    /** This attribute is used to verify the certificate revocation status */
    private RevocationGateway revocationGateway;

    @Inject
    public CertificateVerifierImpl(RevocationGateway revocationGateway){
        this.revocationGateway = revocationGateway;
    }

    /**
     * This method does the end-entity certificate verification
     * @param certificationPath certification path to being verified
     * @param revocationEnabled true to verify revocation, false if not
     * @throws CertificateChainException
     * @throws CertificateRevokedException
     */
    public void verify(CertificationPath certificationPath, boolean revocationEnabled) throws CertificateChainException, CertificateRevokedException {
        try {
            // Checks if the end certificate is still valid
            certificationPath.getEndCertificate().checkValidity();
            Logger.logInfo(CertificateVerifierImpl.class.getSimpleName(), "Certificate validity verification successful");

            // Create the selector that specifies the starting certificate
            X509CertSelector endCertificateSelector = new X509CertSelector();
            endCertificateSelector.setCertificate(certificationPath.getEndCertificate());

            // Create the trust anchors (set of root CA certificates)
            Set<TrustAnchor> trustAnchors = X509CertificateUtilities.filterTrustedAnchors(certificationPath.getCertificateChain());

            // Configure the PKIX certificate builder algorithm parameters
            PKIXBuilderParameters pkixParams = new PKIXBuilderParameters(trustAnchors, endCertificateSelector);

            // Disable CRL checks (this is done manually as additional step)
            pkixParams.setRevocationEnabled(false);

            // Specify a list of intermediate certificates
            CertStore intermediateCertStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(
                            X509CertificateUtilities.filterIntermediateCertificates(certificationPath.getCertificateChain())), "BC");
            pkixParams.addCertStore(intermediateCertStore);

            // Build and verify the certification chain
            CertPathBuilder builder = CertPathBuilder.getInstance("PKIX", "BC");
            builder.build(pkixParams);
            Logger.logInfo(CertificateVerifierImpl.class.getSimpleName(), "Certification path verification successful");

            // Certificate revocation status verification
            if(revocationEnabled) {
                revocationGateway.verify(getEndCertificateIssuer(certificationPath), certificationPath.getEndCertificate());
                Logger.logInfo(CertificateVerifierImpl.class.getSimpleName(), "Certificate is not revoked");
            }
        } catch (GeneralSecurityException ex) {
            throw new CertificateChainException(ex.getMessage());
        }
    }

    /**
     * This method returns the issuer of a certain certificate in a given CertificationPath object
     * @param certificationPath CertificatePath instance to being searched
     * @return Issuer's X509Certificate instance
     */
    private X509Certificate getEndCertificateIssuer(CertificationPath certificationPath) {
        for(X509Certificate intermediateCertificate : certificationPath.getCertificateChain()) {
            if(intermediateCertificate.getSubjectDN().equals(certificationPath.getEndCertificate().getIssuerDN())) {
                return intermediateCertificate;
            }
        }
        // TODO substituir por exception
        return null;
    }


}
