package com.mobile.tanahabangshop.ui.main;

import android.content.res.Resources;

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
    MainImplementer.View provideView(MainActivity mainActivity){
        return mainActivity;
    }

    @Provides
    MainImplementer.Model provideModel(Resources resources, AppPreferences appPreferences){
        return new MainModel(resources, appPreferences);
    }

    @Provides
    MainImplementer.Presenter providePresenter(MainImplementer.Model model, MainImplementer.View view){
        return new MainPresenter(model, view);
    }
}
