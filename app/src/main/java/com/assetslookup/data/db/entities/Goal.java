package com.assetslookup.data.db.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Goal implements Serializable {

    @SerializedName("_id")
    private  String id;
    private String name;
    boolean insertAssets;
    boolean returnWithIrr;
    boolean useAssetIrrOnResult;
    double irrOnResult;
    @SerializedName("user_id")
    private String userId;
    ArrayList<GoalBox> boxes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInsertAssets() {
        return insertAssets;
    }

    public void setInsertAssets(boolean insertAssets) {
        this.insertAssets = insertAssets;
    }

    public boolean isReturnWithIrr() {
        return returnWithIrr;
    }

    public void setReturnWithIrr(boolean returnWithIrr) {
        this.returnWithIrr = returnWithIrr;
    }

    public boolean isUseAssetIrrOnResult() {
        return useAssetIrrOnResult;
    }

    public void setUseAssetIrrOnResult(boolean useAssetIrrOnResult) {
        this.useAssetIrrOnResult = useAssetIrrOnResult;
    }

    public double getIrrOnResult() {
        return irrOnResult;
    }

    public void setIrrOnResult(double irrOnResult) {
        this.irrOnResult = irrOnResult;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<GoalBox> getBoxes() {
        return boxes;
    }

    public void setBoxes(ArrayList<GoalBox> boxes) {
        this.boxes = boxes;
    }
}
