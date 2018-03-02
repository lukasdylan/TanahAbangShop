package com.mobile.tanahabangshop;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.mobile.tanahabangshop.di.DaggerAppComponent;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

/**
 * Created by Lukas Dylan Adisurya on 17/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class CoreApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            RxJavaPlugins.setErrorHandler(Timber::e);
        }

        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
