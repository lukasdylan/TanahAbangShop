package com.mobile.tanahabangshop.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public class CityResponse {
    @SerializedName("rajaongkir")
    @Expose
    private RajaOngkirCity rajaongkir;

    public RajaOngkirCity getRajaongkir() {
        return rajaongkir;
    }

    public void setRajaongkir(RajaOngkirCity rajaongkir) {
        this.rajaongkir = rajaongkir;
    }
}
