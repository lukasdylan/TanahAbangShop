package com.mobile.tanahabangshop.ui.login;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.ui.base.BaseActivity;
import com.mobile.tanahabangshop.ui.main.MainActivity;
import com.mobile.tanahabangshop.ui.register.RegisterActivity;
import com.mobile.tanahabangshop.utility.DialogUtils;
import com.mobile.tanahabangshop.utility.NetworkUtils;
import com.mobile.tanahabangshop.utility.UiUtils;
import com.mobile.tanahabangshop.view.CustomDialog;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class LoginActivity extends BaseActivity implements LoginImplementer.View {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.inputLayoutPhone)
    TextInputLayout inputLayoutPhone;
    @BindView(R.id.inputLayoutPassword)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.phoneET)
    EditText phoneET;
    @BindView(R.id.passwordET)
    EditText passwordET;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.registerBtn)
    TextView registerBtn;

    @BindColor(android.R.color.holo_red_dark)
    int redDark;
    @BindColor(android.R.color.white)
    int white;

    @Inject
    LoginImplementer.Presenter presenter;

    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginBtn)
    void login(View view) {
        hideKeyboard(view);
        clearInputLayout(inputLayoutPhone, inputLayoutPassword);
        presenter.requestLogin(phoneET.getText().toString(), passwordET.getText().toString());
    }

    @OnClick(R.id.registerBtn)
    void register(View view) {
        hideKeyboard(view);
        openActivity(new RegisterActivity());
    }

    @Override
    public void showErrorPhoneNumber(String message) {
        inputLayoutPhone.setError(message);
        inputLayoutPhone.setErrorEnabled(true);
    }

    @Override
    public void showErrorPassword(String message) {
        inputLayoutPassword.setError(message);
        inputLayoutPassword.setErrorEnabled(true);
    }

    @Override
    public void showFailedLogin(String message) {
        UiUtils.showSnackBar(coordinatorLayout, message, redDark, white);
    }

    @Override
    public void showLoading() {
        customDialog = DialogUtils.getLoadingDialog(this);
        if (!customDialog.isShowing()) {
            customDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    @Override
    public void toMainScreen() {
        openActivity(new MainActivity());
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean isConnectedInternet() {
        return NetworkUtils.isNetworkAvailable(this);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }
}
