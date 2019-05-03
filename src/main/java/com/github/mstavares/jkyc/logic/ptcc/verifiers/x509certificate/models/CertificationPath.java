package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.models;

import com.github.mstavares.jkyc.logic.ptcc.utilities.Utilities;
import com.github.mstavares.jkyc.logic.ptcc.utilities.X509CertificateUtilities;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class carries the certificates needed to build a certificate path chain. This chain
 * is used to verify end-entities certificates.
 * @author Miguel Tavares
 */
public class CertificationPath {

    /** This attribute stores the certificate to be verified */
    private X509Certificate endCertificate;

    /** This list stores the certificates needed fo complete the certificate path chain */
    private List<X509Certificate> certificateChain;

    /**
     * CertificationPath's constructor
     * @param builder instantiates the
     */
    private CertificationPath(CertificationPath.Builder builder) {
        certificateChain = builder.certificateChain;
        endCertificate = builder.endCertificate;
    }

    /**
     * This class is used to allow the developer to dynamically instantiates a CertificationPath object
     * @author Miguel Tavares
     */
    public static class Builder {

        /** This attribute stores the certificate to be verified */
        private X509Certificate endCertificate;

        /** This list stores the certificates needed fo complete the certificate path chain */
        private List<X509Certificate> certificateChain = new ArrayList<>();

        /**
         * This method adds the end certificate which is string hex encoded format
         * @param certificateStringHex certificated string hex encoded format
         * @return
         * @throws CertificateException
         */
        public CertificationPath.Builder addEndCertificate(final String certificateStringHex) throws CertificateException {
            endCertificate = X509CertificateUtilities.buildX509Certificate(Utilities.decodeHexString(certificateStringHex));
            certificateChain.add(endCertificate);
            return this;
        }

        /**
         * This method adds the end certificate which is in a byte array format
         * @param certificateData certificate byte array format
         * @return
         * @throws CertificateException
         */
        public CertificationPath.Builder addEndCertificate(final byte[] certificateData) throws CertificateException {
            endCertificate = X509CertificateUtilities.buildX509Certificate(certificateData);
            certificateChain.add(endCertificate);
            return this;
        }

        /**
         * This method adds the end certificate which is an instance of X509Certificate
         * @param certificate X509Certificate type
         * @return
         */
        public CertificationPath.Builder addEndCertificate(final X509Certificate certificate) {
            endCertificate = certificate;
            certificateChain.add(endCertificate);
            return this;
        }

        /**
         * This method adds a certain list of certificates to the certificate chain
         * @param certificateChain list of certificates to being added
         * @return
         */
        public CertificationPath.Builder addCertificateChain(final List<X509Certificate> certificateChain) {
            this.certificateChain.addAll(certificateChain);
            return this;
        }

        /**
         * This method adds a certificate to the certificate chain which is an instance of X509Certificate
         * @param certificate X509Certificate type
         * @return
         */
        public CertificationPath.Builder addCertificateToChain(final X509Certificate certificate) {
            certificateChain.add(certificate);
            return this;
        }

        /**
         * This method instantiates a CertificationPath object
         * @return CertificationPath
         */
        public CertificationPath build() {
            return new CertificationPath(this);
        }

    }

    /**
     * This method is a getter which returns the end certificate
     * @return endCertificate
     */
    public X509Certificate getEndCertificate() {
        return endCertificate;
    }

    /**
     * This method is a getter which returns the certificate chain
     * @return certificateChain
     */
    public List<X509Certificate> getCertificateChain() {
        return certificateChain;
    }
}
