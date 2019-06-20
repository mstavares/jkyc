package com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.ocsp;


import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.ocsp.*;
import org.bouncycastle.cert.ocsp.jcajce.JcaCertificateID;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import com.github.mstavares.jkyc.utilities.Logger;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * This class is responsible to verify if a certain certificate is revoked through an OCSP request
 * @author Miguel Tavares
 */
public class OCSPVerifierImpl implements OCSPVerifier {

    /** This variable is used to store generated nonce */
    //private BigInteger nonce;

    /**
     * This method is used to verify through a OCSP request if a certain certificate is revoked or not
     * @param issuerCertificate issuer's certificate
     * @param certificateToVerify certificate to verify
     * @throws CertificateRevokedException
     * @throws IOException
     */
    @Override
    public void verify(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws CertificateRevokedException, IOException {
        OCSPReq request = buildOCSPRequest(issuerCertificate, certificateToVerify);
        String ocspUrl = getOCSPUrl(certificateToVerify);
        OCSPResp response = sendOCSPRequest(request, ocspUrl);
        isGoodCertificate(request, response, issuerCertificate, certificateToVerify);
    }


    /**
     * This method builds a OCSP request.
     * @param issuerCertificate issuer's certificate
     * @param certificateToVerify certificate to verify
     * @return OCSP request as a OCSPReq instance
     * @throws CertificateRevokedException
     */
    private OCSPReq buildOCSPRequest(X509Certificate issuerCertificate, X509Certificate certificateToVerify) throws CertificateRevokedException {
        try {
            //  CertID structure is used to uniquely identify certificates that are the subject of
            // an OCSP request or response and has an ASN.1 definition. CertID structure is defined in RFC 2560
            DigestCalculatorProvider digCalcProv = new JcaDigestCalculatorProviderBuilder().setProvider("BC").build();
            CertificateID certId = new JcaCertificateID(digCalcProv.get(CertificateID.HASH_SHA1), issuerCertificate, certificateToVerify.getSerialNumber());
            // basic request generation
            OCSPReqBuilder generator = new OCSPReqBuilder();
            generator.addRequest(certId);
            // create details for nonce extension. The nonce extension is used to bind
            // a request to a response to prevent replay attacks. As the name implies,
            // the nonce value is something that the client should only use once within a reasonably small period.
            BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
            //to create the request Extension
            generator.setRequestExtensions(new Extensions(new Extension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce, false, new DEROctetString(nonce.toByteArray()))));
            return generator.build();
        } catch (OCSPException | OperatorCreationException | CertificateEncodingException e) {
            e.printStackTrace();
            throw new CertificateRevokedException("Cannot generate OSCP Request with the given certificates");
        }
    }

    /**
     * This method is used to fetch the OCSP endpoint from a certain certificate
     * @param certificate certificate to fetch the endpoint
     * @return OCSP URL
     */
    private String getOCSPUrl(X509Certificate certificate) {
        byte[] octetBytes = certificate.getExtensionValue(Extension.authorityInfoAccess.getId());
        DLSequence dlSequence = null;
        ASN1Encodable asn1Encodable = null;
        try {
            ASN1Primitive fromExtensionValue = JcaX509ExtensionUtils.parseExtensionValue(octetBytes);
            if (!(fromExtensionValue instanceof DLSequence))
                return null;
            dlSequence = (DLSequence) fromExtensionValue;
            for (int i = 0; i < dlSequence.size(); i++) {
                asn1Encodable = dlSequence.getObjectAt(i);
                if (asn1Encodable instanceof DLSequence)
                    break;
            }
            if (!(asn1Encodable instanceof DLSequence))
                return null;
            dlSequence = (DLSequence) asn1Encodable;
            for (int i = 0; i < dlSequence.size(); i++) {
                asn1Encodable = dlSequence.getObjectAt(i);
                if (asn1Encodable instanceof DERTaggedObject)
                    break;
            }
            if (!(asn1Encodable instanceof DERTaggedObject))
                return null;
            DERTaggedObject derTaggedObject = (DERTaggedObject) asn1Encodable;
            byte[] encoded = derTaggedObject.getEncoded();
            if (derTaggedObject.getTagNo() == 6) {
                int len = encoded[1];
                return new String(encoded, 2, len);
            }
        } catch (IOException e) {
            Logger.logError(OCSPVerifierImpl.class.getSimpleName(), e.getMessage());
        }
        return null;
    }

    /**
     * This method sends the OCSP request
     * @param request request to be sent
     * @param url OCSP url
     * @return OCSP response
     * @throws CertificateRevokedException
     * @throws IOException
     */
    private OCSPResp sendOCSPRequest(OCSPReq request, String url) throws CertificateRevokedException, IOException {
        byte[] bytes = request.getEncoded();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "application/ocsp-request");
        connection.setRequestProperty("Accept", "application/ocsp-response");
        connection.setDoOutput(true);
        Logger.logInfo(OCSPVerifierImpl.class.getSimpleName(),"Sending OCSP request to " + url);
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        if (connection.getResponseCode() != 200) {
            throw new CertificateRevokedException("OCSP request has been failed " + connection.getResponseMessage());
        }
        InputStream in = (InputStream) connection.getContent();
        return new OCSPResp(in);
    }

    /**
     * This method verifies the response and if the certificate is revoked or not
     * @param response OCSP response
     * @param issuer issuer's certificate
     * @param toVerify certificate to being verified
     * @throws CertificateRevokedException
     */
    private void isGoodCertificate(OCSPReq request, OCSPResp response, X509Certificate issuer, X509Certificate toVerify) throws CertificateRevokedException {
        try {
            // SUCCESSFUL here means the OCSP request worked, it doesn't mean the certificates is valid.
            if (response.getStatus() == OCSPRespBuilder.SUCCESSFUL) {
                BasicOCSPResp basicOCSPResponse = (BasicOCSPResp) response.getResponseObject();
                verifySignature(basicOCSPResponse);
                verifyNonce(request, basicOCSPResponse);
                verifyIssuer(basicOCSPResponse, issuer);
                verifyCertificateId(basicOCSPResponse, toVerify);
                verifyCertificateStatus(basicOCSPResponse);
            } else {
                throw new CertificateRevokedException("OCSP Request Failed");
            }
        } catch(OCSPException ex) {
            throw new CertificateRevokedException(ex.getMessage());
        }
    }

    /**
     * This method verifies if the target certificate is revoked or not
     * FROM RFC 6960 page 15
     * CertStatus ::= CHOICE {
     * good        [0]     IMPLICIT NULL,
     * revoked     [1]     IMPLICIT RevokedInfo,
     * unknown     [2]     IMPLICIT UnknownInfo
     * @param response OCSP response
     * @throws CertificateRevokedException
     */
    private void verifyCertificateStatus(BasicOCSPResp response) throws CertificateRevokedException {
        if(response.getResponses()[0].getCertStatus() != CertificateStatus.GOOD) {
            throw new CertificateRevokedException("Certificate status is not good");
        }
    }

    /**
     * This method verifies the certificate id that comes on the response
     * @param response OCSP response
     * @param certificateToVerify certificate to verify
     * @throws CertificateRevokedException
     */
    private void verifyCertificateId(BasicOCSPResp response, X509Certificate certificateToVerify) throws CertificateRevokedException {
        if(!response.getResponses()[0].getCertID().getSerialNumber().equals(certificateToVerify.getSerialNumber())) {
            throw new CertificateRevokedException("Wrong certificate serial number");
        }
    }

    /**
     * This method verifies the certificate issuer that comes on the response
     * @param response OCSP response
     * @param issuer issuer's certificate
     * @throws CertificateRevokedException
     */
    private void verifyIssuer(BasicOCSPResp response, X509Certificate issuer) throws CertificateRevokedException {
        try {
            DigestCalculatorProvider digCalcProv = new JcaDigestCalculatorProviderBuilder().setProvider("BC").build();
            if(!response.getResponses()[0].getCertID().matchesIssuer(new JcaX509CertificateHolder(issuer), digCalcProv)) {
                throw new CertificateRevokedException("Issuer verification missed");
            }
        } catch (CertificateException | OperatorCreationException | OCSPException e) {
            throw new CertificateRevokedException("Issuer verification missed");
        }
    }

    /**
     * This method verifies the OCSP response signature
     * @param response OCSP response
     * @throws CertificateRevokedException
     */
    private void verifySignature(BasicOCSPResp response) throws CertificateRevokedException {
        try {
            final X509CertificateHolder[] chain = response.getCerts();
            JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
            X509Certificate signerCertificate = converter.getCertificate(chain[0]);
            final PublicKey signerPub = signerCertificate.getPublicKey();
            response.isSignatureValid(new JcaContentVerifierProviderBuilder().setProvider(BouncyCastleProvider.PROVIDER_NAME).build(signerPub));
        } catch (CertificateException | OperatorCreationException | OCSPException ex) {
            throw new CertificateRevokedException(ex.getMessage());
        }
    }

    /**
     * This method compares the sent nonce with the one that is on the response
     * @param response OCSP response
     * @throws CertificateRevokedException
     */
    private void verifyNonce(OCSPReq request, BasicOCSPResp response) throws CertificateRevokedException {
        Extension generatedNonce = request.getExtension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce);
        Extension nonceReceive = response.getExtension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce);
        if (nonceReceive != null) {
            if(!generatedNonce.equals(nonceReceive)) {
                throw new CertificateRevokedException("Nonce verification missed");
            }
        } else {
            throw new CertificateRevokedException("Received nonce is null");
        }
    }

}
