package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.exceptions;

import pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.crl.data.messages.CRLMessages;

/**
 * This exception is thrown when a certain CRL is not valid anymore
 * @author Miguel Tavares
 */
public class CRLInvalidCacheException extends Exception {

    public CRLInvalidCacheException() {
        super(CRLMessages.CRL_INVALID_CACHE.toString());
    }
}
