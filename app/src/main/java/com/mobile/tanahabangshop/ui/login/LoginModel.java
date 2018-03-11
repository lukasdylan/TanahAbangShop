package com.mobile.tanahabangshop.ui.login;

import com.google.gson.JsonObject;
import com.mobile.tanahabangshop.service.APIService;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Lukas Dylan Adisurya on 19/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class LoginModel implements LoginImplementer.Model {

    private final APIService apiService;

    public LoginModel(APIService apiService){
        this.apiService = apiService;
    }


    @Override
    public Observable<ResponseBody> fetchLogin(String noHp, String password) {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("nohp", noHp);
//        jsonObject.addProperty("password", password);
        return apiService.login(noHp, password);
    }
}
