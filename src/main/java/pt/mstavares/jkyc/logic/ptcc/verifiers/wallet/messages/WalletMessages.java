package pt.mstavares.jkyc.logic.ptcc.verifiers.wallet.messages;

/**
 * This class provides messages regarding the wallet verifier module.
 * @author Miguel Tavares
 */
public enum WalletMessages {

    SIGNATURE_ERROR_MSG("Signature verifiers failed.");

    private final String description;

    WalletMessages(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}

