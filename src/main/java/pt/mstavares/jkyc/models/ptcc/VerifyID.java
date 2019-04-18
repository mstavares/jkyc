package pt.mstavares.jkyc.models.ptcc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyID {

    @SerializedName("walletSignature")
    @Expose
    private String walletSignature;
    @SerializedName("sod")
    @Expose
    private String sod;
    @SerializedName("certificate")
    @Expose
    private String certificate;

    public String getWalletSignature() {
        return walletSignature;
    }

    public void setWalletSignature(String walletSignature) {
        this.walletSignature = walletSignature;
    }

    public String getSod() {
        return sod;
    }

    public void setSod(String sod) {
        this.sod = sod;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

}
