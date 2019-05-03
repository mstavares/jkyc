package com.github.mstavares.jkyc.logic.ptcc.utilities;

import java.io.ByteArrayInputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class provides some utilities related with certificates
 */
public abstract class X509CertificateUtilities {

    /**
     * This method builds a X.509 certificate from a given byte array
     * @param data byte encoded X.509 certificate
     * @return X509 object generated from byte array
     * @throws CertificateException
     */
    public static X509Certificate buildX509Certificate(final byte[] data) throws CertificateException {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        return (X509Certificate)certFactory.generateCertificate(new ByteArrayInputStream(data));
    }

    /**
     * This method filters and builds a Set which contains trustable certificates
     * @param certificates list containing all certificates
     * @return Set containing filtered trustable certificates
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static Set<TrustAnchor> filterTrustedAnchors(List<X509Certificate> certificates) throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException {
        Set<TrustAnchor> trustAnchors = new HashSet<>();
        for (X509Certificate certificate : certificates) {
            if(isSelfSigned(certificate))
            trustAnchors.add(new TrustAnchor(certificate, null));
        }
        return trustAnchors;
    }

    /**
     * This method filters and builds a List which contains intermediate certificates
     * @param certificates list containing all certificates
     * @return List containing filtered intermediate certificates
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static List<X509Certificate> filterIntermediateCertificates(List<X509Certificate> certificates) throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException {
        List<X509Certificate> intermediateCertificates = new ArrayList<>();
        for (X509Certificate certificate : certificates) {
            if(!isSelfSigned(certificate))
                intermediateCertificates.add(certificate);
        }
        return intermediateCertificates;
    }

    /**
     * Checks whether given X.509 certificate is self-signed.
     * @param certificate X.509 certificate to be verified
     * @return true if is self-signed, false if not
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static boolean isSelfSigned(X509Certificate certificate) throws CertificateException, NoSuchAlgorithmException,
            NoSuchProviderException {
        try {
            // Try to verify certificate signature with its own public key
            PublicKey key = certificate.getPublicKey();
            certificate.verify(key);
            return true;
        } catch (SignatureException | InvalidKeyException ex) {
            // Invalid signature || Invalid key --> not self-signed
            return false;
        }

    }

}
