package com.mobile.tanahabangshop.service.backgroundreceiver;

import com.mobile.tanahabangshop.service.TrackingAPIService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 3/8/2018.
 */

@Module
public class BackgroundReceiverModule {

    @Provides
    BackgroundReceiverImplementer.Service provideService(BackgroundReceiver backgroundReceiver){
        return backgroundReceiver;
    }

    @Provides
    BackgroundReceiverImplementer.Presenter providePresenter(BackgroundReceiverImplementer.Service service,
                                                             BackgroundReceiverImplementer.Model model){
        return new BackgroundReceiverPresenter(service, model);
    }

    @Provides
    BackgroundReceiverImplementer.Model provideModel(TrackingAPIService trackingAPIService){
        return new BackgroundReceiverModel(trackingAPIService);
    }
}
