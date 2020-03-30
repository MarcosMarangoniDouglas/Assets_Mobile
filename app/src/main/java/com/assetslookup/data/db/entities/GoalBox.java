package com.assetslookup.data.db.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GoalBox implements Serializable {
    @SerializedName("_id")
    private  String id;
    String description;
    String periodicity;
    double value;
    String dateStart;
    String dateEnd;
    double interestRate;
    boolean useIRR;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public boolean isUseIRR() {
        return useIRR;
    }

    public void setUseIRR(boolean useIRR) {
        this.useIRR = useIRR;
    }
}
