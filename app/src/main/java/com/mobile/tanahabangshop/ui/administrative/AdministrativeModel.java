package com.mobile.tanahabangshop.ui.administrative;

import com.mobile.tanahabangshop.data.constant.GlobalConstant;
import com.mobile.tanahabangshop.data.network.CityResponse;
import com.mobile.tanahabangshop.data.network.ProvinceResponse;
import com.mobile.tanahabangshop.data.network.TrackingAPIService;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public class AdministrativeModel implements AdministrativeImplementer.Model {

    private final TrackingAPIService trackingAPIService;

    public AdministrativeModel(TrackingAPIService trackingAPIService) {
        this.trackingAPIService = trackingAPIService;
    }

    @Override
    public Observable<ProvinceResponse> fetchProvinceList() {
        return trackingAPIService.getProvinceResponse(GlobalConstant.TRACKING_API_KEY);
    }

    @Override
    public Observable<CityResponse> fetchCityRegionList(String provinceCode) {
        return trackingAPIService.getCityResponse(GlobalConstant.TRACKING_API_KEY, provinceCode);
    }
}
