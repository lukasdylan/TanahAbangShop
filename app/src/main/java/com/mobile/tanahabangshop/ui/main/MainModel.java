package com.mobile.tanahabangshop.ui.main;

import com.mobile.tanahabangshop.data.constant.GlobalConstant;
import com.mobile.tanahabangshop.data.local.AppPreferences;
import com.mobile.tanahabangshop.utility.StringUtils;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class MainModel implements MainImplementer.Model{
    private final AppPreferences appPreferences;

    public MainModel(AppPreferences appPreferences){
        this.appPreferences = appPreferences;
    }

    @Override
    public String getUserName() {
        String fullName = appPreferences.getStringValue(GlobalConstant.PREFERENCES_USERS_NAME,"Lukas Dylan Adisurya");
        return StringUtils.getFirstName(fullName);
    }
}
