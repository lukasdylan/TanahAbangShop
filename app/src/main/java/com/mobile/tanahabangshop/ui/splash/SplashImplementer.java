package com.mobile.tanahabangshop.ui.splash;

/**
 * Created by Lukas Dylan Adisurya on 18/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public interface SplashImplementer {
    interface View {
        void openLoginActivity();

        void openMainActivity();

        boolean isConnectedInternet();
    }

    interface Presenter {
        void initView();

        void destroyView();
    }

    interface Model {
        boolean checkLoggedAccount();
    }
}
