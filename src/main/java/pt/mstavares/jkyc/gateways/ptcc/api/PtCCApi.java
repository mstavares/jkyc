package pt.mstavares.jkyc.gateways.ptcc.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Guice;
import com.google.inject.Injector;
import pt.mstavares.jkyc.gateways.ptcc.config.ConfigurationModule;
import pt.mstavares.jkyc.gateways.ptcc.logic.PortugueseCC;
import pt.mstavares.jkyc.logic.ptcc.verifiers.sod.exceptions.SODVerifierException;
import pt.mstavares.jkyc.logic.ptcc.verifiers.wallet.exceptions.WalletAddressVerifierException;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateChainException;
import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;
import pt.mstavares.jkyc.models.ptcc.DataIDRoot;
import pt.mstavares.jkyc.utilities.Logger;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * This class is used as interface to the KYC library.
 * @author Miguel Tavares
 */
public class PtCCApi {

    /** This attribute is used to invoke portuguese citizenship card KYC service */
    private PortugueseCC portugueseCC;

    /**
     * PtCCApi's constructor. Guice creates an instance of PortugueseCCImpl and makes it accessible through its interface.
     * @throws IOException this exception is thrown if there is no config file, it must be set by KYC_CONFIG env variable.
     */
    public PtCCApi() throws IOException {
        Injector injector = Guice.createInjector(new ConfigurationModule(System.getenv("KYC_CONFIG")));
        portugueseCC = injector.getInstance(PortugueseCC.class);
    }

    /**
     * This method receives the dataID, and send it to verification. If something goes wrong throws an exception.
     * @param dataID dataID to be verified.
     * @throws CertificateException
     * @throws WalletAddressVerifierException
     * @throws CertificateRevokedException
     * @throws NoSuchAlgorithmException
     * @throws SODVerifierException
     * @throws CertificateChainException
     * @throws KeyStoreException
     * @throws IOException
     */
    public void verify(String dataID) throws CertificateException, WalletAddressVerifierException, CertificateRevokedException, NoSuchAlgorithmException, SODVerifierException, CertificateChainException, KeyStoreException, IOException {
        Logger.logInfo(PtCCApi.class.getSimpleName(), dataID);
        JsonObject jsonObject = new Gson().fromJson(dataID, JsonObject.class);
        DataIDRoot data = new Gson().fromJson(jsonObject.toString(), DataIDRoot.class);
        portugueseCC.verify(data);
    }

}
