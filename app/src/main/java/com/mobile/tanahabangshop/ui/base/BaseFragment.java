package com.mobile.tanahabangshop.ui.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.mobile.tanahabangshop.utility.UiUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    protected boolean isFragmentAlive() {
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

    protected void clearInputLayout(TextInputLayout... textInputLayouts) {
        if (isFragmentAlive()) {
            for (TextInputLayout textInputLayout : textInputLayouts) {
                textInputLayout.setErrorEnabled(false);
                textInputLayout.setError(null);
            }
        }
    }
}
