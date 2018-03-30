package com.mobile.tanahabangshop.ui.login;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Lukas Dylan Adisurya on 19/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public interface LoginImplementer {
    interface View {
        void showErrorPhoneNumber(String message);

        void showErrorPassword(String message);

        void showFailedLogin(String message);

        void showLoading();

        void hideLoading();

        void toMainScreen();

        boolean isConnectedInternet();
    }

    interface Presenter {
        void requestLogin(String phoneNumber, String password);

        void destroy();
    }

    interface Model {
        Observable<ResponseBody> fetchLogin(String noHp, String password);

        void setAccountLogged();
    }
}
