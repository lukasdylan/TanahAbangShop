package com.mobile.tanahabangshop.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public class RajaOngkirCity {
    @SerializedName("status")
    @Expose
    private TrackingStatusResponse status;
    @SerializedName("results")
    @Expose
    private List<CityResult> results = new ArrayList<>();

    public TrackingStatusResponse getStatus() {
        return status;
    }

    public void setStatus(TrackingStatusResponse status) {
        this.status = status;
    }

    public List<CityResult> getResults() {
        return results;
    }

    public void setResults(List<CityResult> results) {
        this.results = results;
    }
}
