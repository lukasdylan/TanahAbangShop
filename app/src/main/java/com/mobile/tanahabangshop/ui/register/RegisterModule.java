package com.mobile.tanahabangshop.ui.register;

import com.mobile.tanahabangshop.data.local.AppPreferences;
import com.mobile.tanahabangshop.service.APIService;

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
    RegisterImplementer.Model provideModel(APIService apiService, AppPreferences appPreferences){
        return new RegisterModel(apiService, appPreferences);
    }

    @Provides
    RegisterImplementer.Presenter providePresenter(RegisterImplementer.Model model, RegisterImplementer.View view){
        return new RegisterPresenter(model, view);
    }
}
