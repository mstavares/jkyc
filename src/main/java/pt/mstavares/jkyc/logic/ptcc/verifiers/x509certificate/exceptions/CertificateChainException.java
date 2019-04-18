package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.exceptions;

/**
 * This exception is thrown if a certain certificate does not pass the certification chain verification
 * @author Miguel Tavares
 */
public class CertificateChainException extends Exception {

    public CertificateChainException(final String message) {
        super(message);
    }

}
