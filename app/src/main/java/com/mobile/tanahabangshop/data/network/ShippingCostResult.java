package com.mobile.tanahabangshop.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas Dylan Adisurya on 3/4/2018.
 */

public class ShippingCostResult {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("costs")
    @Expose
    private List<CostResult> costs = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CostResult> getCosts() {
        return costs;
    }

    public void setCosts(List<CostResult> costs) {
        this.costs = costs;
    }
}
