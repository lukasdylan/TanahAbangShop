package com.mobile.tanahabangshop.ui.administrative;

import com.mobile.tanahabangshop.service.TrackingAPIService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

@Module
public class AdministrativeModule {

    @Provides
    AdministrativeImplementer.View provideView(AdministrativeActivity administrativeActivity){
        return administrativeActivity;
    }

    @Provides
    AdministrativeImplementer.Model provideModel(TrackingAPIService trackingAPIService){
        return new AdministrativeModel(trackingAPIService);
    }

    @Provides
    AdministrativeImplementer.Presenter providePresenter(AdministrativeImplementer.View view,
                                                         AdministrativeImplementer.Model model){
        return new AdministrativePresenter(view, model);
    }
}
