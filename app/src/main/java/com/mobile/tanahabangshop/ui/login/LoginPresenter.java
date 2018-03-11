package com.mobile.tanahabangshop.ui.login;

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
    private final LoginImplementer.Model model;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final LoginImplementer.View view;
    private String phoneNumber;
    private String password;

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
            this.phoneNumber = phoneNumber;
            this.password = password;
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
        compositeDisposable.add(model.fetchLogin(phoneNumber, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showLoading())
                .doAfterTerminate(view::hideLoading)
                .subscribe(responseBody -> {
                    String result = responseBody.string();
                    Timber.d(result);
                }));
    }

    @Override
    public void destroyView() {
        compositeDisposable.dispose();
    }
}
