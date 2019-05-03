package com.github.mstavares.jkyc.logic.ptcc.verifiers.sod.messages;

/**
 * This class provides messages regarding the SOD verifier module.
 * @author Miguel Tavares
 */
public enum SODMessages {

    IDT_VERIFICATION_ERROR("Identity verification failed."),
    ADDRS_VERIFICATION_ERROR("Address verification failed.");

    private final String description;

    SODMessages(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
