package com.mobile.tanahabangshop.service.backgroundreceiver;

import com.mobile.tanahabangshop.data.constant.GlobalConstant;
import com.mobile.tanahabangshop.data.network.ProvinceResponse;
import com.mobile.tanahabangshop.service.TrackingAPIService;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 3/8/2018.
 */

public class BackgroundReceiverModel implements BackgroundReceiverImplementer.Model {
    private final TrackingAPIService trackingAPIService;

    public BackgroundReceiverModel(TrackingAPIService trackingAPIService) {
        this.trackingAPIService = trackingAPIService;
    }

    @Override
    public Observable<ProvinceResponse> fetchProvinceList() {
        return trackingAPIService.getProvinceResponse(GlobalConstant.TRACKING_API_KEY);
    }
}
