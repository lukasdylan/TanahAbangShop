package com.mobile.tanahabangshop.ui.register;

import com.mobile.tanahabangshop.service.APIService;

/**
 * Created by Lukas Dylan Adisurya on 21/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class RegisterModel implements RegisterImplementer.Model {

    private final APIService apiService;

    RegisterModel(APIService apiService){
        this.apiService = apiService;
    }
}
