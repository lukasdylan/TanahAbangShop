package com.mobile.tanahabangshop.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas Dylan Adisurya on 3/4/2018.
 */

public class CostResult {
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cost")
    @Expose
    private List<ServiceCostResult> cost = new ArrayList<>();

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ServiceCostResult> getCost() {
        return cost;
    }

    public void setCost(List<ServiceCostResult> cost) {
        this.cost = cost;
    }
}
