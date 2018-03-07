package com.mobile.tanahabangshop.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;
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
import io.reactivex.Observable;

public class LoginActivity extends AppCompatActivity implements LoginImplementer.View {

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

    @BindColor(android.R.color.holo_red_light)
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
        presenter.initView();
    }

    @OnClick(R.id.loginBtn)
    void login(View view) {
        UiUtils.hideKeyboard(view, this);
        if (inputLayoutPhone.isErrorEnabled()) {
            inputLayoutPhone.setError("");
            inputLayoutPhone.setErrorEnabled(false);
        }
        if (inputLayoutPassword.isErrorEnabled()) {
            inputLayoutPassword.setError("");
            inputLayoutPassword.setErrorEnabled(false);
        }
        presenter.validateLoginRequest(phoneET.getText().toString(), passwordET.getText().toString());
    }

    @OnClick(R.id.registerBtn)
    void register(View view) {
        UiUtils.hideKeyboard(view, this);
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
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
        customDialog.show();
    }

    @Override
    public void hideLoading() {
        if (customDialog != null) {
            customDialog.dismiss();
        }
    }

    @Override
    public void toMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public Observable<Boolean> isConnectedInternet() {
        return NetworkUtils.isNetworkAvailableObservable(this);
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        super.onDestroy();
    }
}
