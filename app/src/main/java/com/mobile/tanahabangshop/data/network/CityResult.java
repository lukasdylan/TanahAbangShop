package com.mobile.tanahabangshop.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public class CityResult {
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}