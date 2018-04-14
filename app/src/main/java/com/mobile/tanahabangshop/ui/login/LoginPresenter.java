package com.mobile.tanahabangshop.ui.login;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mobile.tanahabangshop.utility.RxRetryWithDelay;

import java.io.EOFException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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

    LoginPresenter(LoginImplementer.Model model, LoginImplementer.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestLogin(String phoneNumber, String password) {
        if (validate(phoneNumber, password)) {
            sendLoginRequest(phoneNumber, password);
        }
    }

    private boolean validate(String phoneNumber, String password) {
        boolean valid = true;
        boolean isConnected = view.isConnectedInternet();
        if (isConnected) {
            if (phoneNumber.trim().length() < 11 || phoneNumber.trim().length() > 15) {
                view.showErrorPhoneNumber("Mohon cek kembali nomor handphone anda");
                valid = false;
            }
            if (password.trim().length() < 6 || password.trim().length() > 10) {
                view.showErrorPassword("Mohon cek kembali password anda");
                valid = false;
            }
        } else {
            view.showFailedLogin("Ada masalah dengan koneksi anda\nSilahkan coba kembali");
            valid = false;
        }
        return valid;
    }

    private void sendLoginRequest(String phoneNumber, String password) {
        compositeDisposable.add(model.fetchLogin(phoneNumber, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RxRetryWithDelay(3, 3000))
                .doOnSubscribe(disposable -> view.showLoading())
                .subscribe(responseBody -> {
                    view.hideLoading();
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) jsonParser.parse(responseBody.string());
                    int statusCode = jsonObject.get("statusCode").getAsInt();
                    if (statusCode == 1) {
                        view.toMainScreen();
                    } else {
                        String message = jsonObject.get("message").getAsString();
                        view.showFailedLogin(message);
                    }
                }, throwable -> {
                    view.hideLoading();
                    Timber.e(throwable);
                    if (throwable instanceof SocketException || throwable instanceof SocketTimeoutException) {
                        view.showFailedLogin("Ada masalah dengan koneksi anda\nSilahkan coba kembali");
                    } else if (throwable instanceof UnknownHostException || throwable instanceof EOFException) {
                        view.showFailedLogin("Ada masalah dengan server\nSilahkan coba kembali");
                    }
                }));
    }

    @Override
    public void destroy() {
        compositeDisposable.dispose();
    }
}
