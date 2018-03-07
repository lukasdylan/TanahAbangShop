package com.mobile.tanahabangshop.ui.register;

import android.content.Context;

import com.mobile.tanahabangshop.data.network.APIService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 21/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

@Module
public class RegisterModule {

    @Provides
    RegisterImplementer.View provideView(RegisterActivity registerActivity){
        return registerActivity;
    }

    @Provides
    RegisterImplementer.Model provideModel(APIService apiService){
        return new RegisterModel(apiService);
    }

    @Provides
    RegisterImplementer.Presenter providePresenter(RegisterImplementer.Model model, RegisterImplementer.View view){
        return new RegisterPresenter(model, view);
    }
}
