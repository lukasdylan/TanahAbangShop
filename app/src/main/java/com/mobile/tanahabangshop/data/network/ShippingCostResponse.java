package com.mobile.tanahabangshop.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lukas Dylan Adisurya on 3/4/2018.
 */

public class ShippingCostResponse {
    @SerializedName("rajaongkir")
    @Expose
    private RajaOngkirCost rajaongkir;

    public RajaOngkirCost getRajaongkir() {
        return rajaongkir;
    }

    public void setRajaongkir(RajaOngkirCost rajaongkir) {
        this.rajaongkir = rajaongkir;
    }
}
