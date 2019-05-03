package com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.messages;

/**
 * This class provides messages regarding the wallet verifier module.
 * @author Miguel Tavares
 */
public enum WalletMessages {

    SIGNATURE_ERROR_MSG("Wallet address signature verification failed.");

    private final String description;

    WalletMessages(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}

