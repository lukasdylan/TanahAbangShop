package com.mobile.tanahabangshop.ui.register;

import android.os.Handler;

import org.json.JSONException;
import org.json.JSONObject;


import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Created by Lukas Dylan Adisurya on 21/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class RegisterPresenter implements RegisterImplementer.Presenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RegisterImplementer.View viewListener;
    private final RegisterModel registerModel;

    public RegisterPresenter(RegisterModel registerModel){
        this.registerModel = registerModel;
    }

    @Override
    public void initView(RegisterImplementer.View viewListener) {
        this.viewListener = viewListener;
    }

    @Override
    public void validateRegisterRequest(String name, String phoneNumber, String password) {
        String deviceID = registerModel.getDeviceID();
        JSONObject params = new JSONObject();
        try {
            params.put("name", name);
            params.put("phone_number", phoneNumber);
            params.put("password", password);
            params.put("device_id", deviceID);
            viewListener.showConfirmationDialog(params);
        } catch (JSONException e) {
            Timber.e(e);
        }
    }

    @Override
    public void sendRequest(JSONObject jsonObject) {
        viewListener.showLoading();
        new Handler().postDelayed(() -> {
            viewListener.hideDialog();
            viewListener.showFailedDialog("Oops..\nSepertinya ada masalah dengan server kami");
        }, 5000);
    }

    @Override
    public void destroyView() {
        compositeDisposable.dispose();
        viewListener = null;
    }
}
