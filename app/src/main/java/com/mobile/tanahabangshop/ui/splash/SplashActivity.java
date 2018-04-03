package com.mobile.tanahabangshop.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.service.backgroundreceiver.BackgroundReceiver;
import com.mobile.tanahabangshop.ui.base.BaseActivity;
import com.mobile.tanahabangshop.ui.login.LoginActivity;
import com.mobile.tanahabangshop.ui.main.MainActivity;
import com.mobile.tanahabangshop.utility.NetworkUtils;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class SplashActivity extends BaseActivity implements SplashImplementer.View {

    @BindView(R.id.animation_view)
    LottieAnimationView lottieAnimationView;

    @Inject
    SplashImplementer.Presenter presenter;
    @Inject
    BackgroundReceiver backgroundReceiver;

    private Intent intent;
    private static final long DELAY_TIME = 3000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
//        intent = new Intent(getApplicationContext(), backgroundReceiver.getClass());
//        startService(intent);
        presenter.initView();
    }

    @Override
    public void openLoginActivity() {
        startHandler(new SplashRunnable(lottieAnimationView, this, LoginActivity.class), DELAY_TIME);
    }

    @Override
    public void openMainActivity() {
        startHandler(new SplashRunnable(lottieAnimationView, this, MainActivity.class), DELAY_TIME);
    }

    @Override
    public boolean isConnectedInternet() {
        return NetworkUtils.isNetworkAvailable(this);
    }

    @Override
    protected void onDestroy() {
//        stopService(intent);
        stopHandler();
        presenter.destroyView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    private static class SplashRunnable implements Runnable {

        private final WeakReference<LottieAnimationView> lottieAnimationViewWeakReference;
        private final WeakReference<Activity> activityWeakReference;
        private final WeakReference<Class<?>> classWeakReference;

        SplashRunnable(LottieAnimationView lottieAnimationView, Activity activity, Class<?> clazz) {
            this.lottieAnimationViewWeakReference = new WeakReference<>(lottieAnimationView);
            this.activityWeakReference = new WeakReference<>(activity);
            this.classWeakReference = new WeakReference<>(clazz);
        }

        @Override
        public void run() {
            lottieAnimationViewWeakReference.get().cancelAnimation();
            lottieAnimationViewWeakReference.get().setVisibility(View.GONE);
            Intent intent = new Intent(activityWeakReference.get(), classWeakReference.get());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activityWeakReference.get().startActivity(intent);
            activityWeakReference.get().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            activityWeakReference.get().finish();
        }
    }
}
