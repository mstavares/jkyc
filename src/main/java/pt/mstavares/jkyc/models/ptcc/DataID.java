package pt.mstavares.jkyc.models.ptcc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataID {

    @SerializedName("storeIDProvider")
    @Expose
    private StoreIDProvider storeIDProvider;
    @SerializedName("data")
    @Expose
    private Data data;

    public StoreIDProvider getStoreIDProvider() {
        return storeIDProvider;
    }

    public void setStoreIDProvider(StoreIDProvider storeIDProvider) {
        this.storeIDProvider = storeIDProvider;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
