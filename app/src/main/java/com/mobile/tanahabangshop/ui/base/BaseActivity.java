package com.mobile.tanahabangshop.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.utility.UiUtils;

import java.lang.ref.WeakReference;

public abstract class BaseActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BaseHandler baseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
    }

    protected void hideKeyboard(View view) {
        UiUtils.hideKeyboard(view, this);
    }

    protected void openActivity(Activity activity) {
        Intent intent = new Intent(this, activity.getClass());
        startActivity(intent);
    }

    protected void openActivity(Activity activity, Bundle bundle) {
        Intent intent = new Intent(this, activity.getClass());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void clearInputLayout(TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts) {
            textInputLayout.setErrorEnabled(false);
            textInputLayout.setError(null);
        }
    }

    protected void openFragmentWithSlide(int containerViewId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(containerViewId, fragment)
                .addToBackStack(null)
                .commit();
    }

    protected void openFragmentWithSlide(int containerViewId, Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(containerViewId, fragment)
                .addToBackStack(tag)
                .commit();
    }

    protected void openFragmentWithFade(int containerViewId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(containerViewId, fragment)
                .addToBackStack(null)
                .commit();
    }

    protected void openFragmentWithFade(int containerViewId, Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(containerViewId, fragment)
                .addToBackStack(tag)
                .commit();
    }

    protected void startHandler(Runnable runnable, long delayTime) {
        baseHandler = new BaseHandler(runnable, delayTime);
        baseHandler.run();
    }

    protected void stopHandler() {
        if (baseHandler != null) {
            baseHandler.remove();
        }
    }

    public static class BaseHandler extends Handler {

        private final WeakReference<Runnable> runnableWeakReference;
        private final WeakReference<Long> delayTimeWeakReference;

        BaseHandler(Runnable runnable, long delayTime) {
            this.runnableWeakReference = new WeakReference<>(runnable);
            this.delayTimeWeakReference = new WeakReference<>(delayTime);
        }

        void run() {
            if (runnableWeakReference.get() != null && delayTimeWeakReference.get() != null) {
                postDelayed(runnableWeakReference.get(), delayTimeWeakReference.get());
            }
        }

        void remove() {
            if (runnableWeakReference.get() != null) {
                removeCallbacks(runnableWeakReference.get());
            }
        }
    }
}