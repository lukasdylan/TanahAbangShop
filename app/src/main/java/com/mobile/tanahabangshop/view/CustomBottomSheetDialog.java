package com.mobile.tanahabangshop.view;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class CustomBottomSheetDialog extends BottomSheetDialog {

    @BindView(R.id.appVersionTV)
    TextView appVersionTV;

    private BottomSheetCallback callback;

    public CustomBottomSheetDialog(@NonNull Context context, BottomSheetCallback callback) {
        super(context);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.custom_bottom_sheet_dialog);
        ButterKnife.bind(this);
        this.callback = callback;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersionTV.setText(String.format("Versi Aplikasi - %s (%s)", packageInfo.versionName, packageInfo.versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e);
            appVersionTV.setText("Versi Aplikasi (unknown)");
        }
    }

    @OnClick(R.id.profileBtn)
    void toProfileActivity() {
        dismiss();
        callback.openProfile();
    }

    @OnClick(R.id.settingBtn)
    void toSettingActivity() {
        dismiss();
        callback.openSetting();
    }

    @OnClick(R.id.closeBtn)
    void closeDialog() {
        dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public interface BottomSheetCallback {
        void openProfile();

        void openSetting();
    }
}
