package com.mobile.tanahabangshop.ui.detailproduct;

import com.mobile.tanahabangshop.service.APIService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya <lukas.adisurya@ovo.id}>
 * on 4/7/2018.
 */

@Module
public class DetailProductModule {

    @Provides
    DetailProductImplementer.View provideView(DetailProductActivity detailProductActivity){
        return detailProductActivity;
    }

    @Provides
    DetailProductImplementer.Model provideModel(APIService apiService){
        return new DetailProductModel(apiService);
    }

    @Provides
    DetailProductImplementer.Presenter providePresenter(DetailProductImplementer.View view, DetailProductImplementer.Model model){
        return new DetailProductPresenter(view, model);
    }

}
