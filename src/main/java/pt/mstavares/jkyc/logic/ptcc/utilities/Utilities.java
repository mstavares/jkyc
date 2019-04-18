package pt.mstavares.jkyc.logic.ptcc.utilities;

import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class provides some utilities that are needed by some classes
 */
public final class Utilities {

    /**
     * This method decodes an hex string to a byte array
     * @param encodedData encoded hex string
     * @return decoded hex byte array
     */
    public static byte[] decodeHexString(String encodedData) {
        if(encodedData.contains("0x") || encodedData.contains("0X")) {
            return Hex.decode(encodedData.substring(2, encodedData.length()));
        }
        return Hex.decode(encodedData);
    }

    /**
     * This method generates SHA-256 hashes
     * @param input string to be hashed
     * @return hash in form of byte array
     * @throws NoSuchAlgorithmException
     */
    public static byte[] generateSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input.getBytes(StandardCharsets.UTF_8));
    }

}
