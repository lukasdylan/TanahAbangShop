package com.mobile.tanahabangshop.ui.register;

import com.google.gson.JsonObject;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 21/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public interface RegisterImplementer {
    interface View {
        void showErrorName(String message);

        void showErrorPhoneNumber(String message);

        void showErrorPassword(String message);

        void showLoading();

        void showSuccessDialog();

        void showFailedDialog(String message);

        void hideDialog();

        void showConfirmationDialog(JsonObject params);

        Observable<Boolean> checkInternetConnection();

        String getDeviceID();
    }

    interface Presenter {
        void initView();

        void validateRegisterRequest(String name, String phoneNumber, String password);

        void sendRequest(JsonObject params);

        void destroyView();
    }

    interface Model {

    }
}
