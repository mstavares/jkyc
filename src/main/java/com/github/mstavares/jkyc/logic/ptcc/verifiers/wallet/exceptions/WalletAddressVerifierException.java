package com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.exceptions;

/**
 * This exception is thrown when the wallet address verification fails
 * @author Miguel Tavares
 */
public class WalletAddressVerifierException extends Exception {

    public WalletAddressVerifierException(final String message) {
        super(message);
    }

}
