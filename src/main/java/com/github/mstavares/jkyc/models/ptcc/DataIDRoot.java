package com.github.mstavares.jkyc.models.ptcc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class parses dataID from json format
 * @author Miguel Tavares
 */
public class DataIDRoot {

    @SerializedName("dataID")
    @Expose
    private DataID dataID;

    public DataID getDataID() {
        return dataID;
    }

    public void setDataID(DataID dataID) {
        this.dataID = dataID;
    }

    public String getCertificate() {
        return getDataID().getData().getVerifyID().getCertificate();
    }

    public String getWalletAddress() {
        return getDataID().getData().getWa();
    }

    public String getWalletAddressSignature() {
        return getDataID().getData().getVerifyID().getWalletSignature();
    }

    public String getSOD() {
        return getDataID().getData().getVerifyID().getSod();
    }

    public Identity getIdentity() {
        return getDataID().getData().getIdentityID().getIdentity();
    }

    public Address getAddress() {
        return getDataID().getData().getIdentityID().getAddress();
    }

}
