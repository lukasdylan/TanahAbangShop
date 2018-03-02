package com.mobile.tanahabangshop.ui.main;

import com.mobile.tanahabangshop.data.local.AppPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

@Module
public class MainModule {

    @Provides
    MainModel provideModel(AppPreferences appPreferences){
        return new MainModel(appPreferences);
    }

    @Provides
    MainPresenter providePresenter(MainModel mainModel){
        return new MainPresenter(mainModel);
    }
}
