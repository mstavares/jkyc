package pt.mstavares.jkyc.logic.ptcc.verifiers.sod.logic;

import pt.mstavares.jkyc.logic.ptcc.verifiers.sod.models.SODSignedData;
import pt.mstavares.jkyc.logic.ptcc.verifiers.sod.exceptions.SODVerifierException;
import pt.mstavares.jkyc.models.ptcc.DataIDRoot;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * This interface is used by Guice to inject SODVerifierImpl objects
 * @author Miguel Tavares
 */
public interface SODVerifier {

    /**
     * This method verifies the SOD file, the identity and address attributes of a given dataID
     * @param data dataID to be verified
     * @param signedData SOD's signed data
     * @throws SODVerifierException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    void verify(DataIDRoot data, SODSignedData signedData) throws SODVerifierException, IOException, NoSuchAlgorithmException;
}
