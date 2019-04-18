package pt.mstavares.jkyc.models.ptcc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreIDProvider {

    @SerializedName("wa")
    @Expose
    private String wa;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;

    public String getWa() {
        return wa;
    }

    public void setWa(String wa) {
        this.wa = wa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
