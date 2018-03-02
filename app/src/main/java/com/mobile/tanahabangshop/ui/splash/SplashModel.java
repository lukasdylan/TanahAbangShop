package com.mobile.tanahabangshop.ui.splash;

import android.content.Context;

import com.mobile.tanahabangshop.data.local.AppPreferences;
import com.mobile.tanahabangshop.data.constant.GlobalConstant;
import com.mobile.tanahabangshop.utility.NetworkUtils;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 18/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class SplashModel implements SplashImplementer.Model {

    private final Context context;
    private final AppPreferences appPreferences;

    SplashModel(Context context, AppPreferences appPreferences){
        this.context = context;
        this.appPreferences = appPreferences;
    }

    @Override
    public boolean checkLoggedAccount() {
        return appPreferences.getBooleanValue(GlobalConstant.PREFERENCES_LOGGED_IN_KEY, false);
    }

    @Override
    public Observable<Boolean> checkInternetConnection() {
        return NetworkUtils.isNetworkAvailableObservable(context);
    }
}
