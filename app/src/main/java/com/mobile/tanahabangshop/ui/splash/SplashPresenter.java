package com.mobile.tanahabangshop.ui.splash;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Lukas Dylan Adisurya on 18/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class SplashPresenter implements SplashImplementer.Presenter {
    private final SplashImplementer.Model splashModel;
    private final SplashImplementer.View viewListener;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    SplashPresenter(SplashImplementer.Model splashModel, SplashImplementer.View view) {
        this.splashModel = splashModel;
        viewListener = view;
    }

    private void checkLoggedAccount() {
        boolean isAlreadyLoggedIn = splashModel.checkLoggedAccount();
        if (!isAlreadyLoggedIn) {
            viewListener.openMainActivity();
        } else {
            viewListener.openLoginActivity();
        }
    }

    @Override
    public void initView() {
        checkLoggedAccount();
    }

    @Override
    public void destroyView() {
        compositeDisposable.dispose();
    }
}
