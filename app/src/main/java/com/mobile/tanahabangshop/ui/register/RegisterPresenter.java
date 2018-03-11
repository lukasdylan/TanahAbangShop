package com.mobile.tanahabangshop.ui.register;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
        String deviceID = view.getDeviceID();
        compositeDisposable.add(view.isConnectedInternet()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        view.showConfirmationDialog(name, phoneNumber, password, deviceID);
                    } else {
                        view.showFailedDialog("No Internet Connection");
                    }
                }));
    }

    @Override
    public void sendRegisterRequest(String name, String phoneNumber, String password, String deviceID) {
        compositeDisposable.add(model.sendRegisterRequest(name, phoneNumber, password, deviceID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showLoading())
                .subscribe(responseBody -> {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) jsonParser.parse(responseBody.string());
                    int statusCode = jsonObject.get("success").getAsInt();
                    if (statusCode == 1) {
                        model.savePhoneNumber(phoneNumber);
                        view.hideDialog();
                        view.showSuccessDialog();
                    } else {
                        String message = jsonObject.get("message").getAsString();
                        view.hideDialog();
                        view.showFailedDialog(message);
                    }
                }, throwable -> {
                    Timber.e(throwable);
                    if(throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException){
                        view.showFailedDialog("Ada gangguan dari server\nSilahkan coba kembali");
                    }
                }));
    }

    @Override
    public void destroyView() {
        compositeDisposable.dispose();
    }
}
