package com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.logic;

import com.github.mstavares.jkyc.logic.ptcc.utilities.Utilities;
import com.github.mstavares.jkyc.logic.ptcc.utilities.X509CertificateUtilities;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.exceptions.WalletAddressVerifierException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.messages.WalletMessages;
import com.github.mstavares.jkyc.models.ptcc.DataIDRoot;
import com.github.mstavares.jkyc.utilities.Logger;

import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * This class is responsible for verify user's wallet address
 * @author Miguel Tavares
 */
public class WalletVerifierImpl implements WalletVerifier {

    /**
     * This method is used to verify user's wallet address
     * @param data user's dataID
     * @throws WalletAddressVerifierException
     */
    @Override
    public void verify(DataIDRoot data) throws WalletAddressVerifierException {
        try {
            byte[] walletAddressData = Utilities.decodeHexString(data.getWalletAddress());
            byte[] signCertificateData = Utilities.decodeHexString(data.getCertificate());
            X509Certificate signCertificate = X509CertificateUtilities.buildX509Certificate(signCertificateData);
            byte[] signature = Utilities.decodeHexString(data.getWalletAddressSignature());
            Signature sig = Signature.getInstance("SHA256withRSA", "BC");
            /* verifying signature with user's public key */
            sig.initVerify(signCertificate.getPublicKey());
            /* data to be verified with public key */
            sig.update(walletAddressData);
            /* signature verification */
            if(!sig.verify(signature)) {
                throw new WalletAddressVerifierException(WalletMessages.SIGNATURE_ERROR_MSG.toString());
            }
            Logger.logInfo(WalletVerifierImpl.class.getSimpleName(), "Wallet verification successful");
        } catch (NoSuchAlgorithmException | CertificateException| InvalidKeyException | SignatureException | NoSuchProviderException ex) {
            throw new WalletAddressVerifierException(ex.getMessage());
        }
    }

}
