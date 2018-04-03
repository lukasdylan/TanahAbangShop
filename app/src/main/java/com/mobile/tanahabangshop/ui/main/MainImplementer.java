package com.mobile.tanahabangshop.ui.main;

import android.support.v4.app.Fragment;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public interface MainImplementer {
    interface View {
        void showFragment(Fragment fragment);

        void isShowMenuItem(boolean showMenuIcon);

        void setupToolbar(boolean showNavigationIcon, String title);

        void showHomeFragment();
    }

    interface Presenter {
        void initView();

        void destroy();
    }

    interface Model {
        String getTitle(int type);
    }
}
