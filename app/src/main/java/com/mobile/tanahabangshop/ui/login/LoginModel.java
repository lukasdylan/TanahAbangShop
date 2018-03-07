package com.mobile.tanahabangshop.ui.login;

import com.mobile.tanahabangshop.data.network.APIService;

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
}
