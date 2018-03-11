package com.mobile.tanahabangshop.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.ui.profile.UserProfileFragment;
import com.mobile.tanahabangshop.utility.DialogUtils;
import com.mobile.tanahabangshop.utility.UiUtils;
import com.mobile.tanahabangshop.view.MenuBottomSheetDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements MainImplementer.View,
        MenuBottomSheetDialog.MenuBottomSheetCallback, HasSupportFragmentInjector {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;

    @Inject
    MainImplementer.Presenter presenter;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private boolean showMenuItem = true;
    private boolean backToFinish = true;
    ImageView notificationIcon;
    View notificationBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        presenter.initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_more);
        menuItem.setVisible(showMenuItem);

        MenuItem notificationMenuItem = menu.findItem(R.id.menu_notification);
        View actionView = notificationMenuItem.getActionView();
        actionView.setOnClickListener(v -> onOptionsItemSelected(notificationMenuItem));
        notificationIcon = actionView.findViewById(R.id.notificationIcon);
        notificationBadge = actionView.findViewById(R.id.notificationBadge);
        notificationMenuItem.setVisible(showMenuItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                UiUtils.hideKeyboard(frameContainer, this);
                onBackPressed();
                return true;
            case R.id.menu_more:
                MenuBottomSheetDialog menuBottomSheetDialog = DialogUtils.showBottomSheetDialog(this, this);
                menuBottomSheetDialog.show();
                return true;
            case R.id.menu_notification:
                notificationIcon.setRotation(30f);
                notificationBadge.setVisibility(View.VISIBLE);
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public void showFragment(android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(frameContainer.getId(), fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void isShowMenuItem(boolean show) {
        showMenuItem = show;
        invalidateOptionsMenu();
    }

    @Override
    public void setupToolbar(boolean show, String title) {
        backToFinish = !show;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(show);
            getSupportActionBar().setDisplayHomeAsUpEnabled(show);
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (backToFinish) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        super.onDestroy();
    }

    @Override
    public void openProfile() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.fade_out, android.R.anim.fade_in, R.anim.slide_out_down)
                .replace(frameContainer.getId(), new UserProfileFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openSetting() {
        Toast.makeText(this, "Open Setting", Toast.LENGTH_SHORT).show();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
