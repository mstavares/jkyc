package pt.mstavares.jkyc.logic.ptcc.verifiers.x509certificate.revocation.gateway.messages;

/**
 * This class provides messages related with revocation gateway module.
 * @author Miguel Tavares
 */
public enum RevocationMessages {

    CRT_REVOKED_ERROR("End user's certificate is revoked.");

    private final String description;

    RevocationMessages(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
