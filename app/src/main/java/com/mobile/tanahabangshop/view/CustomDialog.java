package com.mobile.tanahabangshop.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.mobile.tanahabangshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lukas Dylan Adisurya on 20/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class CustomDialog extends Dialog {

    @Nullable
    private PositiveCallback positiveCallback;
    @Nullable
    private NegativeCallback negativeCallback;

    @BindView(R.id.messageTV)
    TextView messageTV;
    @BindView(R.id.loadingView)
    LottieAnimationView loadingView;
    @BindView(R.id.confirmButton)
    Button confirmButton;
    @BindView(R.id.cancelButton)
    Button cancelButton;
    @BindView(R.id.buttonLayout)
    LinearLayout buttonLayout;

    @NonNull
    public static CustomDialog withContext(Context context) {
        return new CustomDialog(context);
    }

    private CustomDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context.setTheme(R.style.Theme_Dialog);
        setCanceledOnTouchOutside(false);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        setContentView(R.layout.custom_dialog);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {

    }

    @OnClick(R.id.confirmButton)
    void confirm() {
        if (positiveCallback != null) {
            positiveCallback.onConfirm(this);
        }
    }

    @OnClick(R.id.cancelButton)
    void cancelDialog() {
        if (negativeCallback != null) {
            negativeCallback.onCancel(this);
        }
    }

    public CustomDialog setMessage(String message) {
        messageTV.setText(message);
        return this;
    }

    public CustomDialog setPositiveCallback(@Nullable PositiveCallback positiveCallback) {
        confirmButton.setVisibility(View.VISIBLE);
        this.positiveCallback = positiveCallback;
        return this;
    }

    public CustomDialog setNegativeCallback(@Nullable NegativeCallback negativeCallback) {
        cancelButton.setVisibility(View.VISIBLE);
        this.negativeCallback = negativeCallback;
        return this;
    }

    public CustomDialog asLoadingScreen() {
        loadingView.setVisibility(View.VISIBLE);
        loadingView.setAnimation("loading_screen.json", LottieAnimationView.CacheStrategy.Strong);
        loadingView.setRepeatCount(LottieDrawable.INFINITE);
        loadingView.playAnimation();
        return this;
    }

    public CustomDialog asConfirmScreen() {
        buttonLayout.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        return this;
    }

    public CustomDialog asSuccessScreen() {
        buttonLayout.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.setAnimation("success.json", LottieAnimationView.CacheStrategy.Strong);
        loadingView.setRepeatCount(1);
        loadingView.playAnimation();
        return this;
    }

    public CustomDialog asFailedScreen() {
        buttonLayout.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.setAnimation("error.json", LottieAnimationView.CacheStrategy.Strong);
        loadingView.setRepeatCount(1);
        loadingView.playAnimation();
        return this;
    }

    public interface PositiveCallback {
        void onConfirm(CustomDialog customDialog);
    }

    public interface NegativeCallback {
        void onCancel(CustomDialog customDialog);
    }
}
