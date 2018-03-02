package com.mobile.tanahabangshop.ui.login;

import android.content.Context;

import com.mobile.tanahabangshop.data.network.APIService;
import com.mobile.tanahabangshop.utility.NetworkUtils;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 19/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class LoginModel implements LoginImplementer.Model {

    private final Context context;
    private final APIService apiService;

    public LoginModel(Context context, APIService apiService){
        this.context = context;
        this.apiService = apiService;
    }

    @Override
    public Observable<Boolean> checkInternetConnection() {
        return NetworkUtils.isNetworkAvailableObservable(context);
    }
}
