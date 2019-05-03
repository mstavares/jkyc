package com.github.mstavares.jkyc.models.ptcc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("wa")
    @Expose
    private String wa;
    @SerializedName("idt")
    @Expose
    private String idt;
    @SerializedName("idtName")
    @Expose
    private String idtName;
    @SerializedName("identityID")
    @Expose
    private IdentityID identityID;
    @SerializedName("verifyID")
    @Expose
    private VerifyID verifyID;

    public String getWa() {
        return wa;
    }

    public void setWa(String wa) {
        this.wa = wa;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }

    public String getIdtName() {
        return idtName;
    }

    public void setIdtName(String idtName) {
        this.idtName = idtName;
    }

    public IdentityID getIdentityID() {
        return identityID;
    }

    public void setIdentityID(IdentityID identityID) {
        this.identityID = identityID;
    }

    public VerifyID getVerifyID() {
        return verifyID;
    }

    public void setVerifyID(VerifyID verifyID) {
        this.verifyID = verifyID;
    }

}
