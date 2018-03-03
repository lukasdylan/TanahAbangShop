package com.mobile.tanahabangshop.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public class ProvinceResponse {
    @SerializedName("rajaongkir")
    @Expose
    private RajaOngkirProvince rajaongkir;

    public RajaOngkirProvince getRajaongkir() {
        return rajaongkir;
    }

    public void setRajaongkir(RajaOngkirProvince rajaongkir) {
        this.rajaongkir = rajaongkir;
    }
}
