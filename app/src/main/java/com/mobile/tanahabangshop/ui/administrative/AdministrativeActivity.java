package com.mobile.tanahabangshop.ui.administrative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.network.CityResult;
import com.mobile.tanahabangshop.data.network.ProvinceResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class AdministrativeActivity extends AppCompatActivity implements AdministrativeImplementer.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.administrativeRV)
    RecyclerView administrativeRV;
    @BindView(R.id.loadingLayout)
    LinearLayout loadingLayout;
    @BindView(R.id.animation_view)
    LottieAnimationView animationView;
    @BindView(R.id.failedLayout)
    LinearLayout failedLayout;

    @Inject
    AdministrativeImplementer.Presenter presenter;

    private static final int PROVINCE_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrative);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        int requestCode = getIntent().getIntExtra("request_code", PROVINCE_REQUEST);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            if (requestCode == PROVINCE_REQUEST) {
                getSupportActionBar().setTitle("Pilih Provinsi");
            } else {
                getSupportActionBar().setTitle("Pilih Kota atau Kabupaten");
            }
        }
        administrativeRV.setHasFixedSize(true);
        administrativeRV.setLayoutManager(new LinearLayoutManager(this));
        administrativeRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        if (requestCode == PROVINCE_REQUEST) {
            presenter.loadProvince();
        } else {
            int provinceCode = getIntent().getIntExtra("province_code", 0);
            presenter.loadCity(provinceCode);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showProvinceList(List<ProvinceResult> provinceResultList) {
        administrativeRV.setVisibility(View.VISIBLE);
        ProvinceAdapter provinceAdapter = new ProvinceAdapter(provinceResultList);
        provinceAdapter.provinceResultSubject
                .debounce(250, TimeUnit.MILLISECONDS)
                .subscribe(provinceResult -> {
                    Intent intent = new Intent();
                    intent.putExtra("province_code", Integer.parseInt(provinceResult.getProvinceId()));
                    intent.putExtra("province_name", provinceResult.getProvince());
                    setResult(RESULT_OK, intent);
                    finish();
                });
        administrativeRV.setAdapter(provinceAdapter);
    }

    @Override
    public void showCityList(List<CityResult> cityResultList) {
        administrativeRV.setVisibility(View.VISIBLE);
        CityRegionAdapter cityRegionAdapter = new CityRegionAdapter(cityResultList);
        cityRegionAdapter.cityResultSubject
                .debounce(250, TimeUnit.MILLISECONDS)
                .subscribe(cityResult -> {
                    Intent intent = new Intent();
                    intent.putExtra("city_code", Integer.parseInt(cityResult.getCityId()));
                    intent.putExtra("city_name", cityResult.getType() + " " + cityResult.getCityName());
                    setResult(RESULT_OK, intent);
                    finish();
                });
        administrativeRV.setAdapter(cityRegionAdapter);
    }

    @Override
    public void hideLoading() {
        animationView.cancelAnimation();
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showFailed() {
        failedLayout.setVisibility(View.VISIBLE);
    }
}
