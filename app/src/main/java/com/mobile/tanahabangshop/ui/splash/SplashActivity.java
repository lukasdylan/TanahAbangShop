package com.mobile.tanahabangshop.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.service.backgroundreceiver.BackgroundReceiver;
import com.mobile.tanahabangshop.ui.login.LoginActivity;
import com.mobile.tanahabangshop.ui.main.MainActivity;
import com.mobile.tanahabangshop.utility.NetworkUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class SplashActivity extends AppCompatActivity implements SplashImplementer.View {

    @BindView(R.id.animation_view)
    LottieAnimationView lottieAnimationView;

    @Inject
    SplashImplementer.Presenter presenter;
    @Inject
    BackgroundReceiver backgroundReceiver;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        intent = new Intent(getApplicationContext(), backgroundReceiver.getClass());
        startService(intent);
        presenter.initView();
    }

    @Override
    public void openLoginActivity() {
        new Handler().postDelayed(() -> {
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setVisibility(View.GONE);
            SplashActivity.this.finish();
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, 5000);

    }

    @Override
    public void openMainActivity() {
        new Handler().postDelayed(() -> {
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setVisibility(View.GONE);
            SplashActivity.this.finish();
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, 5000);
    }

    @Override
    public boolean isConnectedInternet() {
        return NetworkUtils.isNetworkAvailable(this);
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        presenter.destroyView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
