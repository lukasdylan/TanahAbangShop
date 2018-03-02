package com.mobile.tanahabangshop.ui.register;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.utility.DialogUtils;
import com.mobile.tanahabangshop.view.CustomDialog;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class RegisterActivity extends AppCompatActivity implements RegisterImplementer.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.inputNameLayout)
    TextInputLayout inputNameLayout;
    @BindView(R.id.inputLayoutPhone)
    TextInputLayout inputLayoutPhone;
    @BindView(R.id.inputLayoutPassword)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.nameET)
    EditText nameET;
    @BindView(R.id.phoneET)
    EditText phoneET;
    @BindView(R.id.passwordET)
    EditText passwordET;

    @BindColor(android.R.color.white)
    int white;

    @Inject
    RegisterPresenter presenter;

    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Daftar Baru");
            Drawable drawable = toolbar.getNavigationIcon();
            if (drawable != null) {
                drawable.setColorFilter(white, PorterDuff.Mode.SRC_ATOP);
            }
        }

        presenter.initView(this);
    }

    @OnClick(R.id.registerBtn)
    void register() {
        if (inputNameLayout.isErrorEnabled()) {
            inputNameLayout.setErrorEnabled(false);
            inputNameLayout.setError("");
        }
        if (inputLayoutPhone.isErrorEnabled()) {
            inputLayoutPhone.setErrorEnabled(false);
            inputLayoutPhone.setError("");
        }
        if (inputLayoutPassword.isErrorEnabled()) {
            inputLayoutPassword.setErrorEnabled(false);
            inputLayoutPassword.setError("");
        }
        presenter.validateRegisterRequest(
                nameET.getText().toString(),
                phoneET.getText().toString(),
                passwordET.getText().toString()
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        super.onDestroy();
    }

    @Override
    public void showErrorName(String message) {
        inputNameLayout.setErrorEnabled(true);
        inputNameLayout.setError(message);
    }

    @Override
    public void showErrorPhoneNumber(String message) {
        inputLayoutPhone.setErrorEnabled(true);
        inputLayoutPhone.setError(message);
    }

    @Override
    public void showErrorPassword(String message) {
        inputLayoutPassword.setErrorEnabled(true);
        inputLayoutPassword.setError(message);
    }

    @Override
    public void showLoading() {
        customDialog = DialogUtils.getLoadingDialog(this);
        customDialog.show();
    }

    @Override
    public void showSuccessDialog() {
        CustomDialog.PositiveCallback positiveCallback = customDialog1 -> {
            customDialog1.dismiss();
            RegisterActivity.this.finish();
        };
        String message = "Pendaftaran anda sukses, silahkan menunggu konfirmasi dari pihak toko";
        customDialog = DialogUtils.getSuccessDialog(this, positiveCallback, message);
        customDialog.show();
    }

    @Override
    public void showFailedDialog(String message) {
        CustomDialog.PositiveCallback positiveCallback = Dialog::dismiss;
        customDialog = DialogUtils.getFailedDialog(this, positiveCallback, message);
        customDialog.show();
    }

    @Override
    public void hideDialog() {
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    @Override
    public void showConfirmationDialog(JSONObject params) {
        CustomDialog.PositiveCallback positiveCallback = customDialog -> {
            customDialog.dismiss();
            presenter.sendRequest(params);
        };

        CustomDialog.NegativeCallback negativeCallback = Dialog::dismiss;

        String message = "Mohon cek kembali data anda\n\nNama lengkap: " + nameET.getText().toString()
                + "\nNomor Handphone: " + phoneET.getText().toString() + "\n\nApakah data anda sudah benar?";

        customDialog = DialogUtils.getConfirmationDialog(this, positiveCallback, negativeCallback, message);
        customDialog.show();
    }
}
