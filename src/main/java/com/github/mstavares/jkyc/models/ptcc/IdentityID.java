package com.github.mstavares.jkyc.models.ptcc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdentityID {

    @SerializedName("identityAttributes")
    @Expose
    private Identity identity;
    @SerializedName("addressAttributes")
    @Expose
    private Address address;

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
