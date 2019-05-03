package com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.models;

import com.github.mstavares.jkyc.logic.ptcc.utilities.Utilities;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.Store;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.exceptions.SODVerifierException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;

/**
 * This class wraps SOD's signed data and provides it
 * @author Miguel Tavares
 */
public class SODSignedData {

    /** SOD's signed data */
    private CMSSignedData cmsSignedData;

    /**
     * SODSignedData's constructor
     * @param sodRaw SOD file string encoded as hex
     * @throws SODVerifierException
     */
    public SODSignedData(final String sodRaw) throws SODVerifierException {
        try {
            byte[] sodData = Utilities.decodeHexString(sodRaw);
            cmsSignedData = getCMSSignedDataFromSOD(sodData);
        } catch (CMSException ex) {
            ex.printStackTrace();
            throw new SODVerifierException(ex.getMessage());
        }
    }

    /**
     * This getter returns the SOD's signed data
     * @return signed data
     */
    public CMSSignedData getCmsSignedData() {
        return cmsSignedData;
    }

    /**
     * This method returns the signed data from SOD's file
     * @param sodData SOD's file
     * @return signed data
     * @throws CMSException
     */
    private CMSSignedData getCMSSignedDataFromSOD(final byte[] sodData) throws CMSException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sodData);
        /* Skips the first 4 bytes. Those bytes defines the size of SOD file */
        byteArrayInputStream.skip(4);
        return new CMSSignedData(byteArrayInputStream);
    }

    /**
     * This method returns the X.509 certificate within SOD's file
     * @return SOD's X.509 certificate
     * @throws CertificateException
     */
    public X509Certificate getCertificateFromSOD() throws CertificateException {
        Store certStore = cmsSignedData.getCertificates();
        SignerInformationStore signerInfos = cmsSignedData.getSignerInfos();
        Collection<SignerInformation> signers = signerInfos.getSigners();
        for (SignerInformation signer : signers) {
            Collection<X509CertificateHolder> matches = certStore.getMatches(signer.getSID());
            for (X509CertificateHolder holder : matches) {
                return new JcaX509CertificateConverter().setProvider("BC").getCertificate(holder);
            }
        }
        return null;
    }

    /**
     * This method returns the Machine-Readable Passport (MRT) data from SOD.
     * @return ASN1Sequence containing Machine-Readable Passport
     * @throws IOException
     */
    public ASN1Sequence getMRTDData() throws IOException {
        byte[] data = (byte[]) cmsSignedData.getSignedContent().getContent();
        ASN1InputStream bIn = new ASN1InputStream(new ByteArrayInputStream(data));
        ASN1Primitive obj = bIn.readObject();
        return ASN1Sequence.getInstance(obj);
    }

}
