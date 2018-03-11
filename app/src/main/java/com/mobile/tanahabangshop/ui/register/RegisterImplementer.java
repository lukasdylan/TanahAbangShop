package com.mobile.tanahabangshop.ui.register;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

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

        void showConfirmationDialog(String name, String phoneNumber, String password, String deviceID);

        Observable<Boolean> isConnectedInternet();

        String getDeviceID();
    }

    interface Presenter {
        void validateRegisterRequest(String name, String phoneNumber, String password);

        void sendRegisterRequest(String name, String phoneNumber, String password, String deviceID);

        void destroyView();
    }

    interface Model {
        Observable<ResponseBody> sendRegisterRequest(String name, String phoneNumber, String password, String deviceID);

        void savePhoneNumber(String phoneNumber);
    }
}
