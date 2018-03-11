package com.mobile.tanahabangshop.ui.splash;

import com.mobile.tanahabangshop.data.local.AppPreferences;
import com.mobile.tanahabangshop.service.backgroundreceiver.BackgroundReceiver;

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
    BackgroundReceiver provideBackgroundReceiver(){
        return new BackgroundReceiver();
    }

    @Provides
    SplashImplementer.View provideView(SplashActivity splashActivity){
        return splashActivity;
    }

    @Provides
    SplashImplementer.Model provideModel(AppPreferences appPreferences){
        return new SplashModel(appPreferences);
    }

    @Provides
    SplashImplementer.Presenter providePresenter(SplashImplementer.Model splashModel, SplashImplementer.View view){
        return new SplashPresenter(splashModel, view);
    }
}
