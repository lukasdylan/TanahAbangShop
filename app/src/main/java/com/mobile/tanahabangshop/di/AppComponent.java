package com.mobile.tanahabangshop.di;

import com.mobile.tanahabangshop.CoreApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Lukas Dylan Adisurya on 17/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        NetworkModule.class,
        AppContextModule.class,
        BuilderModule.class})

public interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(CoreApplication coreApplication);
        AppComponent build();
    }

    void inject(CoreApplication coreApplication);
}
