package com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.logic;

import com.github.mstavares.jkyc.logic.ptcc.utilities.Utilities;
import com.github.mstavares.jkyc.models.ptcc.Address;
import com.github.mstavares.jkyc.models.ptcc.DataIDRoot;
import com.github.mstavares.jkyc.models.ptcc.Identity;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.models.SODSignedData;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.exceptions.SODVerifierException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.messages.SODMessages;
import com.github.mstavares.jkyc.utilities.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for SOD's file verification
 * @author Miguel Tavares
 */
public class SODVerifierImpl implements SODVerifier {

    /** This attribute stores the index related with the identity hash */
    private static final int SEQ_IDENTITY_INDEX = 0;

    /** This attribute stores the index related with the address hash */
    private static final int SEQ_ADDRESS_INDEX = 1;

    /**
     * This method does the SOD, identity and address attributes
     * @param data dataID to be verified
     * @param signedData
     * @throws SODVerifierException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public void verify(DataIDRoot data, SODSignedData signedData) throws SODVerifierException, IOException, NoSuchAlgorithmException {
        verifySignature(signedData);
        verifyIdentityHash(signedData, data.getIdentity());
        verifyAddressHash(signedData, data.getAddress());
    }

    /**
     * This method verifies the the signature which wraps the hashes. Without this
     * signature verification we are not able to trust on these hashes.
     * @param signedData SOD's signed data
     * @throws SODVerifierException
     */
    private void verifySignature(SODSignedData signedData) throws SODVerifierException {
        try {
            CMSSignedData cmsSignedData = signedData.getCmsSignedData();
            List<X509CertificateHolder> certs = new ArrayList(cmsSignedData.getCertificates().getMatches(null));
            X509Certificate signer = new JcaX509CertificateConverter().setProvider("BC").getCertificate(certs.get(0));
            if (!cmsSignedData.verifySignatures(signerId -> new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(signer))) {
                throw new SODVerifierException("SOD CMS Signature Verification Failed");
            }
            Logger.logInfo(SODVerifierImpl.class.getSimpleName(), "SOD signature verification successful");
        } catch (CMSException | CertificateException ex) {
            throw new SODVerifierException(ex.getMessage());
        }
    }

    /**
     * This method verifies the generated hash with the one which is within the SOD's file.
     * If the hashes are different a SODVerifierException is thrown.
     * @param signedData SOD's signed data
     * @param identity identity attributes
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws SODVerifierException
     */
    private void verifyIdentityHash(SODSignedData signedData, Identity identity) throws NoSuchAlgorithmException, IOException, SODVerifierException {
        byte[] identityHash = Utilities.generateSHA256(getAppendedIdAttributes(identity));
        if (!Arrays.equals(identityHash, getSODIdentityHash(signedData)))
            throw new SODVerifierException(SODMessages.IDT_VERIFICATION_ERROR.toString());
        Logger.logInfo(SODVerifierImpl.class.getSimpleName(), "Identity verification successful");

    }

    /**
     * Since the address can be optionally be stored in WalliD, this method only executes the address
     * verification if the user imported his address. This method verifies the generated hash with
     * the one which is within the SOD's file. If the hashes are different a SODVerifierException is thrown.
     * @param signedData SOD's signed data
     * @param address address attributes
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws SODVerifierException
     */
    private void verifyAddressHash(SODSignedData signedData, Address address) throws NoSuchAlgorithmException, IOException, SODVerifierException {
        if(address != null) {
            byte[] addressHash = Utilities.generateSHA256(getAppendedAddressAttributes(address));
            if (!Arrays.equals(addressHash, getSODAddressHash(signedData)))
                throw new SODVerifierException(SODMessages.ADDRS_VERIFICATION_ERROR.toString());
            Logger.logInfo(SODVerifierImpl.class.getSimpleName(), "Address verification successful");
        }
    }

    /**
     * This method generates the identity attributes hash.
     * @param signedData SOD's signed data
     * @return byte array containing the generated identity hash
     * @throws IOException
     */
    private byte[] getSODIdentityHash(SODSignedData signedData) throws IOException {
        ASN1Sequence hashes = signedData.getMRTDData();
        ASN1Encodable asn1Identity = ((ASN1Sequence)((ASN1Sequence)hashes.getObjectAt(2)).getObjectAt(SEQ_IDENTITY_INDEX)).getObjectAt(1);
        return ASN1OctetString.getInstance(asn1Identity).getOctets();
    }

    /**
     * This method generates the address attributes hash.
     * @param signedData SOD's signed data
     * @return byte array containing the generated address hash
     * @throws IOException
     */
    private byte[] getSODAddressHash(SODSignedData signedData) throws IOException {
        ASN1Sequence hashes = signedData.getMRTDData();
        ASN1Encodable asn1Address = ((ASN1Sequence)((ASN1Sequence)hashes.getObjectAt(2)).getObjectAt(SEQ_ADDRESS_INDEX)).getObjectAt(1);
        return ASN1OctetString.getInstance(asn1Address).getOctets();
    }

    /**
     * This method appends the identity attributes and returns them in a String. This specific
     * order is mandatory to generate the hash that will be compared with the one within the SOD file
     * @param identity identity attributes
     * @return appended identity attributes
     */
    private String getAppendedIdAttributes(Identity identity) {
        StringBuilder sb = new StringBuilder();
        sb.append(identity.getIssuingEntity());
        sb.append(identity.getCountry());
        sb.append(identity.getDocumentType());
        sb.append(identity.getDocumentNum());
        sb.append(identity.getDocumentPAN());
        sb.append(identity.getDocumentVersion());
        sb.append(identity.getValidityBeginDate());
        sb.append(identity.getPlaceOfRequest());
        sb.append(identity.getValidityEndDate());
        sb.append(identity.getSurname());
        sb.append(identity.getGivenName());
        sb.append(identity.getSex());
        sb.append(identity.getNationality());
        sb.append(identity.getBirthDate());
        sb.append(identity.getHeight());
        sb.append(identity.getCivilianIdNumber());
        sb.append(identity.getSurnameMother());
        sb.append(identity.getGivenNameMother());
        sb.append(identity.getSurnameFather());
        sb.append(identity.getGivenNameFather());
        sb.append(identity.getAccidentalIndications());
        sb.append(identity.getNIF());
        sb.append(identity.getNISS());
        sb.append(identity.getNSNS());
        return sb.toString();
    }

    /**
     * This method appends the address attributes and returns them in a String. This specific
     * order is mandatory to generate the hash that will be compared with the one within the SOD file
     * @param address address attributes
     * @return appended address attributes
     */
    private String getAppendedAddressAttributes(Address address) {
        StringBuilder sb = new StringBuilder();
        sb.append(address.getCountryCode());
        sb.append(address.getDistrictCode());
        sb.append(address.getDistrict());
        sb.append(address.getMunicipalityCode());
        sb.append(address.getMunicipality());
        sb.append(address.getParishCode());
        sb.append(address.getParish());
        sb.append(address.getAbbrStreetType());
        sb.append(address.getStreetType());
        sb.append(address.getStreetName());
        sb.append(address.getAbbrBuildingType());
        sb.append(address.getBuildingType());
        sb.append(address.getDoorNo());
        sb.append(address.getFloor());
        sb.append(address.getSide());
        sb.append(address.getPlace());
        sb.append(address.getLocality());
        sb.append(address.getZip4());
        sb.append(address.getZip3());
        sb.append(address.getPostalLocality());
        sb.append(address.getGeneratedAddressCode());
        return sb.toString();
    }

}
