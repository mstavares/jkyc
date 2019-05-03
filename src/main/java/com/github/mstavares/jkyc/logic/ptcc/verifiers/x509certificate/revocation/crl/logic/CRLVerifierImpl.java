package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.logic;

import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.repository.CRLRepository;
import com.google.inject.Inject;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.asn1.x509.Extension;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.gateway.messages.RevocationMessages;
import com.github.mstavares.jkyc.utilities.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to verify through a CRL if certain certificate revoked or not
 * @author Miguel Tavares
 */
public class CRLVerifierImpl implements CRLVerifier {

    /** This attribute manages CRLs access */
    @Inject
    private CRLRepository repository;

    /**
     * This method verifies through a CRL if a certain certificate is revoked or not
     * @param issuerCertificate certificate to be verified issuer
     * @param certificateToVerify certificate to be verified
     * @throws com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException
     */
    @Override
    public void verify(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException {
        try {
            List<String> crlDistPoints = getCrlDistributionPoints(certificateToVerify);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            for (String crlDistPoint : crlDistPoints) {
                Logger.logInfo(CRLVerifierImpl.class.getSimpleName(), "CRL: " + crlDistPoint);
                X509CRL crl = (X509CRL) certificateFactory.generateCRL(repository.fetch(crlDistPoint));
                crl.verify(issuerCertificate.getPublicKey());
                if(crl.isRevoked(certificateToVerify))
                    throw new com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException(RevocationMessages.CRT_REVOKED_ERROR.toString());
            }
        } catch (IOException | CertificateException | SignatureException | InvalidKeyException | CRLException ex) {
            throw new CertificateRevokedException(ex.getMessage());
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            Logger.logError(CRLVerifierImpl.class.getSimpleName(), e.getMessage());
        }
    }

    /**
     * This method fetches the CRLs distribution points from a certain certificate
     * @param certificate target certificate
     * @return List of CRLs distribution points
     * @throws IOException
     */
    private List<String> getCrlDistributionPoints(X509Certificate certificate) throws IOException {
        List<String> crlDistPoints = new ArrayList<>();
        final String[] crlDistPointsExts = { Extension.cRLDistributionPoints.getId(), Extension.freshestCRL.getId() };
        for(String crlDistPointExt : crlDistPointsExts) {
            byte[] crldpExt = certificate.getExtensionValue(crlDistPointExt);
            if(crldpExt != null) {
                ASN1InputStream oAsnInStream = new ASN1InputStream(new ByteArrayInputStream(crldpExt));
                ASN1Primitive derObjCrlDP = oAsnInStream.readObject();
                DEROctetString dosCrlDP = (DEROctetString) derObjCrlDP;
                byte[] crldpExtOctets = dosCrlDP.getOctets();
                ASN1InputStream oAsnInStream2 = new ASN1InputStream(new ByteArrayInputStream(crldpExtOctets));
                ASN1Primitive derObj2 = oAsnInStream2.readObject();
                CRLDistPoint distPoint = CRLDistPoint.getInstance(derObj2);
                for (DistributionPoint dp : distPoint.getDistributionPoints()) {
                    DistributionPointName dpn = dp.getDistributionPoint();
                    // Look for URIs in fullName
                    if (dpn != null) {
                        if (dpn.getType() == DistributionPointName.FULL_NAME) {
                            GeneralName[] generalNames = GeneralNames.getInstance(dpn.getName()).getNames();
                            // Look for an URI
                            for(GeneralName generalName : generalNames) {
                                if (generalName.getTagNo() == GeneralName.uniformResourceIdentifier) {
                                    String url = DERIA5String.getInstance(generalName.getName()).getString();
                                    crlDistPoints.add(url);
                                }
                            }
                        }
                    }
                }
            }
        }
        return crlDistPoints;
    }

}
