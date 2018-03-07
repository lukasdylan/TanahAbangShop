package com.mobile.tanahabangshop.ui.login;

import android.os.Handler;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Created by Lukas Dylan Adisurya on 19/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class LoginPresenter implements LoginImplementer.Presenter {
    private final LoginImplementer.Model model;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final LoginImplementer.View view;

    LoginPresenter(LoginImplementer.Model model, LoginImplementer.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void validateLoginRequest(String phoneNumber, String password) {
        boolean valid = true;
        if (phoneNumber.trim().length() < 11 || phoneNumber.trim().length() > 15) {
            view.showErrorPhoneNumber("Mohon cek kembali nomor handphone anda");
            valid = false;
        }
        if (password.trim().length() < 6 || password.trim().length() > 10) {
            view.showErrorPassword("Mohon cek kembali password anda");
            valid = false;
        }
        if (valid) {
            checkInternetConnection();
        }
    }

    private void checkInternetConnection() {
        compositeDisposable.add(view.isConnectedInternet()
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        loginRequest();
                    } else {
                        view.showFailedLogin("Tidak ada koneksi internet!");
                    }
                }, Timber::e)
        );
    }

    private void loginRequest() {
        view.showLoading();
        new Handler().postDelayed(() -> {
            view.hideLoading();
            view.toMainScreen();
        },5000);
    }

    @Override
    public void destroyView() {
        compositeDisposable.dispose();
    }
}
