package com.mobile.tanahabangshop.ui.main.home;

import com.mobile.tanahabangshop.data.local.AppPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 3/2/2018.
 */

@Module
public class HomeModule {
    @Provides
    HomeImplementer.View provideView(HomeFragment homeFragment){
        return homeFragment;
    }

    @Provides
    HomeImplementer.Presenter providePresenter(HomeImplementer.View view, HomeImplementer.Model model){
        return new HomePresenter(view, model);
    }

    @Provides
    HomeImplementer.Model provideModel(AppPreferences appPreferences){
        return new HomeModel(appPreferences);
    }
}
