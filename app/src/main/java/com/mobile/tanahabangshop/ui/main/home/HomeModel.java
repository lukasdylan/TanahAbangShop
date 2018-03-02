package com.mobile.tanahabangshop.ui.main.home;

import com.mobile.tanahabangshop.data.constant.GlobalConstant;
import com.mobile.tanahabangshop.data.local.AppPreferences;
import com.mobile.tanahabangshop.utility.StringUtils;

/**
 * Created by Lukas Dylan Adisurya on 3/2/2018.
 */

public class HomeModel implements HomeImplementer.Model {

    private AppPreferences appPreferences;

    public HomeModel(AppPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    @Override
    public String getUserName() {
        String fullName = appPreferences.getStringValue(GlobalConstant.PREFERENCES_USERS_NAME, "Lukas Dylan Adisurya");
        return StringUtils.getFirstName(fullName);
    }
}
