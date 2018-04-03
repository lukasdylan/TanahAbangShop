package com.mobile.tanahabangshop.ui.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.mobile.tanahabangshop.utility.UiUtils;

import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    private BaseFragmentHandler baseFragmentHandler;

    private boolean isFragmentAlive() {
        return getActivity() != null && isVisible();
    }

    protected void hideKeyboard(View view) {
        if (isFragmentAlive()) {
            UiUtils.hideKeyboard(view, getActivity());
        }
    }

    protected void openActivity(Activity activity) {
        if (isFragmentAlive()) {
            Intent intent = new Intent(getActivity(), activity.getClass());
            startActivity(intent);
        }
    }

    protected void openActivity(Activity activity, Bundle bundle) {
        if (isFragmentAlive()) {
            Intent intent = new Intent(getActivity(), activity.getClass());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    protected void openActivity(Activity activity, int flag) {
        if (isFragmentAlive()) {
            Intent intent = new Intent(getActivity(), activity.getClass());
            intent.setFlags(flag);
            startActivity(intent);
        }
    }

    protected void openActivity(Activity activity, Bundle bundle, int flag) {
        if (isFragmentAlive()) {
            Intent intent = new Intent(getActivity(), activity.getClass());
            intent.setFlags(flag);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    protected void openActivityWithResult(Activity activity, int requestCode) {
        if (isFragmentAlive()) {
            Intent intent = new Intent(getActivity(), activity.getClass());
            startActivityForResult(intent, requestCode);
        }
    }

    protected void openActivityWithResult(Activity activity, int requestCode, Bundle bundle) {
        if (isFragmentAlive()) {
            Intent intent = new Intent(getActivity(), activity.getClass());
            intent.putExtras(bundle);
            startActivityForResult(intent, requestCode);
        }
    }

    protected void clearInputLayout(TextInputLayout... textInputLayouts) {
        if (isFragmentAlive()) {
            for (TextInputLayout textInputLayout : textInputLayouts) {
                textInputLayout.setErrorEnabled(false);
                textInputLayout.setError(null);
            }
        }
    }

    protected void startHandler(Runnable runnable, long delayTime) {
        baseFragmentHandler = new BaseFragmentHandler(runnable, delayTime);
        baseFragmentHandler.run();
    }

    protected void startHandler(Runnable runnable, long delayTime, Looper looper) {
        baseFragmentHandler = new BaseFragmentHandler(runnable, delayTime, looper);
        baseFragmentHandler.run();
    }

    protected void stopHandler() {
        if (baseFragmentHandler != null) {
            baseFragmentHandler.remove();
        }
    }

    private static class BaseFragmentHandler extends Handler {

        private final WeakReference<Runnable> runnableWeakReference;
        private final WeakReference<Long> delayTimeWeakReference;

        BaseFragmentHandler(Runnable runnable, long delayTime) {
            this.runnableWeakReference = new WeakReference<>(runnable);
            this.delayTimeWeakReference = new WeakReference<>(delayTime);
        }

        BaseFragmentHandler(Runnable runnable, long delayTime, Looper looper) {
            super(looper);
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
