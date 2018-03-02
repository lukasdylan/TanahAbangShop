package com.mobile.tanahabangshop.ui.splash;

import android.content.Intent;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 18/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public interface SplashImplementer {
    interface View {
        void openLoginActivity();
        void openMainActivity();
    }

    interface Presenter {
        void initView(SplashImplementer.View viewListener);

        void destroyView();
    }

    interface Model {
        boolean checkLoggedAccount();
        Observable<Boolean> checkInternetConnection();
    }
}
