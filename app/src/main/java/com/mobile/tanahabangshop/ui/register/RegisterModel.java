package com.mobile.tanahabangshop.ui.register;

import com.mobile.tanahabangshop.data.constant.GlobalConstant;
import com.mobile.tanahabangshop.data.local.AppPreferences;
import com.mobile.tanahabangshop.service.APIService;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Lukas Dylan Adisurya on 21/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class RegisterModel implements RegisterImplementer.Model {

    private final APIService apiService;
    private final AppPreferences appPreferences;

    RegisterModel(APIService apiService, AppPreferences appPreferences){
        this.apiService = apiService;
        this.appPreferences = appPreferences;
    }

    @Override
    public Observable<ResponseBody> sendRegisterRequest(String name, String phoneNumber, String password, String deviceID) {
        return apiService.register(name, phoneNumber, password, deviceID);
    }

    @Override
    public void savePhoneNumber(String phoneNumber) {
        appPreferences.setValue(GlobalConstant.PREFERENCES_PHONE_NUMBER, phoneNumber);
    }
}
