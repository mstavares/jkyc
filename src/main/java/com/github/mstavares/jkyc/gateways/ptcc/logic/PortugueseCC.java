package com.github.mstavares.jkyc.gateways.ptcc.logic;

import com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.exceptions.WalletAddressVerifierException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateChainException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;
import com.github.mstavares.jkyc.models.ptcc.DataIDRoot;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.exceptions.SODVerifierException;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * This interface is used by Guice to inject PortugueseCCImpl objects
 * @author Miguel Tavares
 */
public interface PortugueseCC {

    /**
     * This method does the user's identity verification
     * @param data user's data to be verified
     * @return
     * @throws CertificateException
     * @throws WalletAddressVerifierException
     * @throws SODVerifierException
     * @throws CertificateChainException
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    boolean verify(DataIDRoot data) throws CertificateException, WalletAddressVerifierException, SODVerifierException,
            CertificateChainException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateRevokedException;
}
