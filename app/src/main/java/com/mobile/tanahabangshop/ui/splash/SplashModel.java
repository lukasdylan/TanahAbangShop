package com.mobile.tanahabangshop.ui.splash;

import com.mobile.tanahabangshop.data.constant.GlobalConstant;
import com.mobile.tanahabangshop.data.local.AppPreferences;

/**
 * Created by Lukas Dylan Adisurya on 18/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class SplashModel implements SplashImplementer.Model {

    private final AppPreferences appPreferences;

    SplashModel(AppPreferences appPreferences){
        this.appPreferences = appPreferences;
    }

    @Override
    public boolean checkLoggedAccount() {
        return appPreferences.getBooleanValue(GlobalConstant.PREFERENCES_LOGGED_IN_KEY, false);
    }
}
