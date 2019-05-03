package com.github.mstavares.jkyc.logic.ptcc;

import com.github.mstavares.jkyc.gateways.ptcc.config.ConfigurationModule;
import com.github.mstavares.jkyc.gateways.ptcc.logic.PortugueseCC;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.exceptions.SODVerifierException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.exceptions.WalletAddressVerifierException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateChainException;
import com.github.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions.CertificateRevokedException;
import com.github.mstavares.jkyc.models.ptcc.DataIDRoot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import java.io.FileReader;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class KYCTests {

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    private PortugueseCC portugueseCC;

    @Before
    public void setup() throws IOException {
        Security.addProvider(new BouncyCastleProvider());
        environmentVariables.set("KYC_CONFIG", "test.properties");
        Injector injector = Guice.createInjector(new ConfigurationModule(System.getenv("KYC_CONFIG")));
        portugueseCC = injector.getInstance(PortugueseCC.class);
    }

    @Test
    public void testOK() throws IOException, CertificateException, WalletAddressVerifierException, CertificateRevokedException, NoSuchAlgorithmException, SODVerifierException, CertificateChainException, KeyStoreException {
        JsonObject jsonObject = new Gson().fromJson(new FileReader(KYCTests.class.getClassLoader().getResource("DataId_Test_Card_OK.json").getFile()), JsonObject.class);
        DataIDRoot data = new Gson().fromJson(jsonObject.toString(), DataIDRoot.class);
        assertThat(portugueseCC.verify(data), is(true));
    }

    @Test(expected = WalletAddressVerifierException.class)
    public void testWalletSignatureFailure() throws IOException, CertificateException, WalletAddressVerifierException, CertificateRevokedException, NoSuchAlgorithmException, SODVerifierException, CertificateChainException, KeyStoreException {
        JsonObject jsonObject = new Gson().fromJson(new FileReader(KYCTests.class.getClassLoader().getResource("DataId_Test_Card_WA.json").getFile()), JsonObject.class);
        DataIDRoot data = new Gson().fromJson(jsonObject.toString(), DataIDRoot.class);
        portugueseCC.verify(data);
    }

    @Test(expected = SODVerifierException.class)
    public void testSODIdentityAttributesFailure() throws IOException, CertificateException, WalletAddressVerifierException, CertificateRevokedException, NoSuchAlgorithmException, SODVerifierException, CertificateChainException, KeyStoreException {
        JsonObject jsonObject = new Gson().fromJson(new FileReader(KYCTests.class.getClassLoader().getResource("DataId_Test_Card_SOD_ID.json").getFile()), JsonObject.class);
        DataIDRoot data = new Gson().fromJson(jsonObject.toString(), DataIDRoot.class);
        portugueseCC.verify(data);
    }

    @Test(expected = SODVerifierException.class)
    public void testSODAddressAttributesFailure() throws IOException, CertificateException, WalletAddressVerifierException, CertificateRevokedException, NoSuchAlgorithmException, SODVerifierException, CertificateChainException, KeyStoreException {
        JsonObject jsonObject = new Gson().fromJson(new FileReader(KYCTests.class.getClassLoader().getResource("DataId_Test_Card_SOD_ADD.json").getFile()), JsonObject.class);
        DataIDRoot data = new Gson().fromJson(jsonObject.toString(), DataIDRoot.class);
        portugueseCC.verify(data);
    }

}
