package com.mobile.tanahabangshop.ui.login;

import android.os.Handler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Lukas Dylan Adisurya on 19/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class LoginPresenter implements LoginImplementer.Presenter {
    private final LoginModel loginModel;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LoginImplementer.View viewListener;

    LoginPresenter(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    @Override
    public void initView(LoginImplementer.View viewListener) {
        this.viewListener = viewListener;
    }

    @Override
    public void validateLoginRequest(String phoneNumber, String password) {
        boolean valid = true;
        if (phoneNumber.trim().length() < 11 || phoneNumber.trim().length() > 15) {
            viewListener.showErrorPhoneNumber("Mohon cek kembali nomor handphone anda");
            valid = false;
        }
        if (password.trim().length() < 6 || password.trim().length() > 10) {
            viewListener.showErrorPassword("Mohon cek kembali password anda");
            valid = false;
        }
        if (valid) {
            checkInternetConnection();
        }
    }

    private void checkInternetConnection() {
        compositeDisposable.add(loginModel.checkInternetConnection()
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        loginRequest();
                    } else {
                        viewListener.showFailedLogin("Tidak ada koneksi internet!");
                    }
                }, Timber::e)
        );
    }

    private void loginRequest() {
        viewListener.showLoading();
        new Handler().postDelayed(() -> {
            viewListener.hideLoading();
            viewListener.toMainScreen();
        },5000);
    }

    @Override
    public void destroyView() {
        compositeDisposable.dispose();
    }
}
