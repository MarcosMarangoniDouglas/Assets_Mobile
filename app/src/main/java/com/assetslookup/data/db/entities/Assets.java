package com.assetslookup.data.db.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Assets {
    List<Asset> assets;
    @SerializedName("asset_total")
    private Asset assetTotal;

    public Assets(List<Asset> assets, Asset assetTotal) {
        this.assets = assets;
        this.assetTotal = assetTotal;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public Asset getAssetTotal() {
        return assetTotal;
    }

    public void setAssetTotal(Asset assetTotal) {
        this.assetTotal = assetTotal;
    }
}
