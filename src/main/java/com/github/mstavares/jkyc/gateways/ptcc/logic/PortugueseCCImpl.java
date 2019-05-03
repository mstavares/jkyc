package com.github.mstavares.jkyc.gateways.ptcc.logic;

import com.github.mstavares.jkyc.gateways.ptcc.config.Configuration;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.exceptions.WalletAddressVerifierException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateChainException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.logic.CertificateVerifier;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.models.CertificationPath;
import com.github.mstavares.jkyc.models.ptcc.DataIDRoot;
import com.google.inject.Inject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.models.SODSignedData;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.exceptions.SODVerifierException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.logic.SODVerifier;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.logic.WalletVerifier;
import com.github.mstavares.jkyc.utilities.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;

/**
 * This class handles the identity's verification process.
 * @author Miguel Tavares
 */
public class PortugueseCCImpl implements PortugueseCC {

    /** This attribute does the ptcc certificate verification */
    @Inject
    private CertificateVerifier certificateVerifier;

    /** This attribute does the wallet address verification */
    @Inject
    private WalletVerifier walletVerifier;

    /** This attribute does the sod verification */
    @Inject
    private SODVerifier sodVerifier;

    /** This attributes contains the configurations passed on KYC_CONFIG file */
    @Inject
    private Configuration configuration;

    /**
     * This is the constructor of PortugueseCCImpl class
     */
    @Inject
    public PortugueseCCImpl() {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * This method does the identity verification.
     * @param data user's data to be verified
     * @return
     * @throws CertificateException
     * @throws WalletAddressVerifierException
     * @throws SODVerifierException
     * @throws CertificateChainException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public boolean verify(DataIDRoot data) throws CertificateException, WalletAddressVerifierException, SODVerifierException,
            CertificateChainException, IOException, NoSuchAlgorithmException, CertificateRevokedException {
        Logger.logInfo(PortugueseCCImpl.class.getSimpleName(), "Verifying user's identity");
        verifyCertificate(data);
        verifyWalletAddress(data);
        verifySOD(data);
        Logger.logInfo(PortugueseCCImpl.class.getSimpleName(), "User's identity verification successful");
        return true;
    }

    /**
     * This method requests the certificate verifier to verify the end-entity certificate
     * @param data user's data do be verified
     * @throws CertificateException
     * @throws CertificateChainException
     */
    private void verifyCertificate(DataIDRoot data) throws CertificateException, CertificateChainException, CertificateRevokedException {
        Logger.logInfo(PortugueseCCImpl.class.getSimpleName(), "Verifying user's certificate");
        CertificationPath certificationPath = new CertificationPath.Builder()
                .addCertificateChain(configuration.getX509Certificates())
                .addEndCertificate(data.getCertificate())
                .build();
        certificateVerifier.verify(certificationPath, true);
        Logger.logInfo(PortugueseCCImpl.class.getSimpleName(), "User's certificate verification successful");
    }

    /**
     * This method requests the wallet address verifier to verify the user's wallet address
     * @param data user's data do be verified
     * @throws WalletAddressVerifierException
     */
    private void verifyWalletAddress(DataIDRoot data) throws WalletAddressVerifierException {
        Logger.logInfo(PortugueseCCImpl.class.getSimpleName(), "Verifying user's wallet address");
        walletVerifier.verify(data);
        Logger.logInfo(PortugueseCCImpl.class.getSimpleName(), "User's wallet address verification successful");
    }

    /**
     * This method requests the sod verifier to verify user's identity and address attributes
     * @param data user's data do be verified
     * @throws CertificateException
     * @throws CertificateChainException
     * @throws SODVerifierException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private void verifySOD(DataIDRoot data) throws CertificateException, CertificateChainException, SODVerifierException,
            IOException, NoSuchAlgorithmException, CertificateRevokedException {
        Logger.logInfo(PortugueseCCImpl.class.getSimpleName(), "Verifying user's SOD file");
        SODSignedData sodDto = new SODSignedData(data.getSOD());
        CertificationPath certificationPath = new CertificationPath.Builder()
                .addCertificateChain(configuration.getX509Certificates())
                .addEndCertificate(sodDto.getCertificateFromSOD())
                .build();
        certificateVerifier.verify(certificationPath, false);
        sodVerifier.verify(data, sodDto);
        Logger.logInfo(PortugueseCCImpl.class.getSimpleName(), "User's SOD file verification successful");
    }

}
