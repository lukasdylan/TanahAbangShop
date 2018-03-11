package com.mobile.tanahabangshop.service.backgroundreceiver;

import com.mobile.tanahabangshop.data.network.ProvinceResponse;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 3/8/2018.
 */

public interface BackgroundReceiverImplementer {
    interface Service {
        void showNotification(String message);

        void showNotificationOreo(String message);
    }

    interface Presenter {
        void testLoadProvinceList();
    }

    interface Model {
        Observable<ProvinceResponse> fetchProvinceList();
    }
}
