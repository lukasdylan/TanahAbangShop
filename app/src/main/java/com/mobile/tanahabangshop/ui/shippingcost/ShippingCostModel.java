package com.mobile.tanahabangshop.ui.shippingcost;

import com.mobile.tanahabangshop.data.constant.GlobalConstant;
import com.mobile.tanahabangshop.data.network.ShippingCostResponse;
import com.mobile.tanahabangshop.service.TrackingAPIService;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 3/4/2018.
 */

public class ShippingCostModel implements ShippingCostImplementer.Model {

    private final TrackingAPIService trackingAPIService;

    ShippingCostModel(TrackingAPIService trackingAPIService) {
        this.trackingAPIService = trackingAPIService;
    }

    @Override
    public Observable<ShippingCostResponse> fetchShippingCost(String destinationCode, int weightTotal,
                                                              String courierName) {
        return trackingAPIService.getShippingCostResponse(GlobalConstant.TRACKING_API_KEY,
                GlobalConstant.TRACKING_API_ORIGIN_CODE, destinationCode, weightTotal, courierName);
    }
}
