package com.mobile.tanahabangshop.ui.shippingcost;

import com.mobile.tanahabangshop.data.network.TrackingAPIService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 3/4/2018.
 */

@Module
public class ShippingCostModule {

    @Provides
    ShippingCostImplementer.View provideView(ShippingCostFragment shippingCostFragment){
        return shippingCostFragment;
    }

    @Provides
    ShippingCostImplementer.Presenter providePresenter(ShippingCostImplementer.View view, ShippingCostImplementer.Model model){
        return new ShippingCostPresenter(view, model);
    }

    @Provides
    ShippingCostImplementer.Model provideModel(TrackingAPIService trackingAPIService){
        return new ShippingCostModel(trackingAPIService);
    }
}
