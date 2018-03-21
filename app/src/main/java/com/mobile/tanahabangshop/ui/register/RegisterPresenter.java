package com.mobile.tanahabangshop.ui.register;

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
 * Created by Lukas Dylan Adisurya on 21/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class RegisterPresenter implements RegisterImplementer.Presenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final RegisterImplementer.View view;
    private final RegisterImplementer.Model model;

    RegisterPresenter(RegisterImplementer.Model model, RegisterImplementer.View view) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void validateRegisterRequest(String name, String phoneNumber, String password) {
        if (isValidData(name, phoneNumber, password)) {
            compositeDisposable.add(view.isConnectedInternet()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            String deviceID = view.getDeviceID();
                            view.showConfirmationDialog(name, phoneNumber, password, deviceID);
                        } else {
                            view.showFailedDialog("No Internet Connection");
                        }
                    }));
        }
    }

    private boolean isValidData(String name, String phoneNumber, String password) {
        boolean valid = true;
        if (name.isEmpty() || name.length() <= 2) {
            view.showErrorName("Cek kembali nama anda");
            valid = false;
        }
        if (phoneNumber.length() <= 10 || phoneNumber.length() > 12){
            view.showErrorPhoneNumber("Nomor handphone anda tidak valid");
            valid = false;
        }
        if (password.length() < 6){
            view.showErrorPassword("Minimal jumlah karakter untuk kata sandi adalah 6");
            valid = false;
        }
        return valid;
    }

    @Override
    public void sendRegisterRequest(String name, String phoneNumber, String password, String deviceID) {
        compositeDisposable.add(model.sendRegisterRequest(name, phoneNumber, password, deviceID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RxRetryWithDelay(3, 3000))
                .doOnSubscribe(disposable -> view.showLoading())
                .subscribe(responseBody -> {
                    view.hideDialog();
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) jsonParser.parse(responseBody.string());
                    int statusCode = jsonObject.get("success").getAsInt();
                    if (statusCode == 1) {
                        model.savePhoneNumber(phoneNumber);
                        view.showSuccessDialog();
                    } else {
                        String message = jsonObject.get("message").getAsString();
                        view.showFailedDialog(message);
                    }
                }, throwable -> {
                    view.hideDialog();
                    if (throwable instanceof SocketException || throwable instanceof SocketTimeoutException) {
                        view.showFailedDialog("Gagal dalam mengirim data\nPeriksa kembali koneksi anda");
                    } else if(throwable instanceof UnknownHostException || throwable instanceof EOFException){
                        view.showFailedDialog("Ada gangguan dari server\nSilahkan coba kembali");
                    }
                }));
    }

    @Override
    public void destroyView() {
        compositeDisposable.dispose();
    }
}
