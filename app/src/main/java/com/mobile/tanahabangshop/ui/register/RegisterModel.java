package com.mobile.tanahabangshop.ui.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import com.mobile.tanahabangshop.data.network.APIService;
import com.mobile.tanahabangshop.utility.NetworkUtils;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 21/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class RegisterModel implements RegisterImplementer.Model {

    private final Context context;

    public RegisterModel(Context context, APIService apiService){
        this.context = context;
    }

    @Override
    public Observable<Boolean> checkInternetConnection() {
        return NetworkUtils.isNetworkAvailableObservable(context);
    }

    @SuppressLint("HardwareIds")
    @Override
    public String getDeviceID() {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
