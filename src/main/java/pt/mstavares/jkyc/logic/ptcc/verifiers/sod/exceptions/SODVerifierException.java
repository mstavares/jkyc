package pt.mstavares.jkyc.logic.ptcc.verifiers.sod.exceptions;

/**
 * This exception is thrown when the SOD's file verification fails
 * @author Miguel Tavares
 */
public class SODVerifierException extends Exception {

    public SODVerifierException(final String message) {
        super(message);
    }
}
