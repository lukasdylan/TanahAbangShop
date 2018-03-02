package com.mobile.tanahabangshop.ui.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.model.MainMenu;
import com.mobile.tanahabangshop.utility.DialogUtils;
import com.mobile.tanahabangshop.view.CustomBottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainImplementer.View,
        MainImplementer.MainAdapter, CustomBottomSheetDialog.BottomSheetCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.welcomeUserTV)
    TextView welcomeUserTV;
    @BindView(R.id.mainMenuRV)
    RecyclerView mainMenuRV;

    @BindDrawable(R.drawable.icon_main_product)
    Drawable iconMainProduct;
    @BindDrawable(R.drawable.icon_invoice)
    Drawable iconInvoice;
    @BindDrawable(R.drawable.icon_shipping_cost)
    Drawable iconShippingCost;
    @BindDrawable(R.drawable.icon_store)
    Drawable iconStore;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        presenter.initView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_more:
                CustomBottomSheetDialog customBottomSheetDialog = DialogUtils.showBottomSheetDialog(this, this);
                customBottomSheetDialog.show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refreshView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
    }

    @Override
    public void onSelectedMenu(int position) {
        Timber.d(String.valueOf(position));
    }

    @Override
    public void showMenu() {
        List<MainMenu> mainMenuList = new ArrayList<>();
        mainMenuList.add(new MainMenu("Lihat Produk Toko", iconMainProduct));
        mainMenuList.add(new MainMenu("Cek Pesanan Saya", iconInvoice));
        mainMenuList.add(new MainMenu("Cek Biaya Pengiriman", iconShippingCost));
        mainMenuList.add(new MainMenu("Cek Informasi Toko", iconStore));

        MainAdapter mainAdapter = new MainAdapter(mainMenuList, this);
        mainMenuRV.setHasFixedSize(true);
        mainMenuRV.setLayoutManager(new GridLayoutManager(this, 2));
        mainMenuRV.setAdapter(mainAdapter);
        mainMenuRV.setNestedScrollingEnabled(false);
    }

    @Override
    public void setWelcomeText(String welcomeTime, String name) {
        welcomeUserTV.setText(String.format("%s, %s", welcomeTime, name));
    }

    @Override
    public void showFavoriteItems() {

    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        super.onDestroy();
    }

    @Override
    public void openProfile() {
        Toast.makeText(this, "Open Profile", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openSetting() {
        Toast.makeText(this, "Open Setting", Toast.LENGTH_SHORT).show();
    }
}
