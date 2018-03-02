package com.mobile.tanahabangshop.ui.splash;

import android.content.Context;

import com.mobile.tanahabangshop.data.local.AppPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 18/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

@Module
public class SplashModule {

    @Provides
    SplashModel provideModel(Context context, AppPreferences appPreferences){
        return new SplashModel(context, appPreferences);
    }

    @Provides
    SplashPresenter providePresenter(SplashModel splashModel){
        return new SplashPresenter(splashModel);
    }
}
