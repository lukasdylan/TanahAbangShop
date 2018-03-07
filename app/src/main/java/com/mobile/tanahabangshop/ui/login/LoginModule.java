package com.mobile.tanahabangshop.ui.login;

import android.content.Context;

import com.mobile.tanahabangshop.data.network.APIService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 19/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

@Module
public class LoginModule {

    @Provides
    LoginImplementer.View provideView(LoginActivity loginActivity){
        return loginActivity;
    }

    @Provides
    LoginImplementer.Model provideModel(APIService apiService){
        return new LoginModel(apiService);
    }

    @Provides
    LoginImplementer.Presenter providePresenter(LoginImplementer.Model loginModel, LoginImplementer.View view){
        return new LoginPresenter(loginModel, view);
    }
}
