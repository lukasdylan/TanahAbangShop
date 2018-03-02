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
    private final RegisterImplementer.View view;
    private final RegisterImplementer.Model model;

    public RegisterPresenter(RegisterImplementer.Model model, RegisterImplementer.View view) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void initView() {

    }

    @Override
    public void validateRegisterRequest(String name, String phoneNumber, String password) {
        String deviceID = model.getDeviceID();
        JSONObject params = new JSONObject();
        try {
            params.put("name", name);
            params.put("phone_number", phoneNumber);
            params.put("password", password);
            params.put("device_id", deviceID);
            view.showConfirmationDialog(params);
        } catch (JSONException e) {
            Timber.e(e);
        }
    }

    @Override
    public void sendRequest(JSONObject jsonObject) {
        view.showLoading();
        new Handler().postDelayed(() -> {
            view.hideDialog();
            view.showFailedDialog("Oops..\nSepertinya ada masalah dengan server kami");
        }, 5000);
    }

    @Override
    public void destroyView() {
        compositeDisposable.dispose();
    }
}
