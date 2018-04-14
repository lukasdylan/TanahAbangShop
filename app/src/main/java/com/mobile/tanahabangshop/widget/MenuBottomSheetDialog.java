package com.mobile.tanahabangshop.widget;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.ui.profile.UserProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class MenuBottomSheetDialog extends BottomSheetDialog {

    @BindView(R.id.appVersionTV)
    TextView appVersionTV;

    private final PublishSubject<Fragment> fragmentPublishSubject = PublishSubject.create();

    public MenuBottomSheetDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.custom_bottom_sheet_dialog);
        ButterKnife.bind(this);
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersionTV.setText(String.format("Versi Aplikasi - %s (%s)", packageInfo.versionName, packageInfo.versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e);
            appVersionTV.setText("Versi Aplikasi (unknown)");
        }
    }

    public PublishSubject<Fragment> getFragmentPublishSubject() {
        return fragmentPublishSubject;
    }

    @OnClick(R.id.profileBtn)
    void openProfile() {
        dismiss();
        fragmentPublishSubject.onNext(new UserProfileFragment());
    }

    @OnClick(R.id.settingBtn)
    void openSetting() {
        dismiss();
        fragmentPublishSubject.onNext(new UserProfileFragment());
    }

    @OnClick(R.id.closeBtn)
    void closeDialog() {
        dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
