package com.mobile.tanahabangshop.ui.main;

import android.content.res.Resources;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.constant.GlobalConstant;
import com.mobile.tanahabangshop.data.local.AppPreferences;
import com.mobile.tanahabangshop.utility.StringUtils;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class MainModel implements MainImplementer.Model {

    static final int HOME_TYPE = 0;
    static final int PROFILE_TYPE = 1;
    private final AppPreferences appPreferences;
    private final Resources resources;

    public MainModel(Resources resources, AppPreferences appPreferences) {
        this.resources = resources;
        this.appPreferences = appPreferences;
    }

    @Override
    public String getTitle(int type) {
        switch (type){
            case HOME_TYPE:
                return resources.getString(R.string.app_name);
            case PROFILE_TYPE:
                return "Profile";
        }
        return resources.getString(R.string.app_name);
    }
}
