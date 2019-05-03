package com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.logic;

import com.github.mstavares.jkyc.logic.ptcc.verifiers.wallet.exceptions.WalletAddressVerifierException;
import com.github.mstavares.jkyc.models.ptcc.DataIDRoot;

/**
 * This interface is used by Guice to inject WalletVerifierImpl objects
 * @author Miguel Tavares
 */
public interface WalletVerifier {

    /**
     * This method is used to verify user's wallet address
     * @param data user's dataID
     * @throws WalletAddressVerifierException
     */
    void verify(DataIDRoot data) throws WalletAddressVerifierException;
}
