package com.mobile.tanahabangshop.ui.register;

import android.content.Context;

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
    RegisterModel provideModel(Context context){
        return new RegisterModel(context);
    }

    @Provides
    RegisterPresenter providePresenter(RegisterModel registerModel){
        return new RegisterPresenter(registerModel);
    }
}
