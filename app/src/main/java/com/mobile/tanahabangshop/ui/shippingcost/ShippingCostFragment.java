package com.mobile.tanahabangshop.ui.shippingcost;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.network.CostResult;
import com.mobile.tanahabangshop.ui.administrative.AdministrativeActivity;
import com.mobile.tanahabangshop.ui.base.BaseFragment;
import com.mobile.tanahabangshop.ui.main.MainImplementer;
import com.mobile.tanahabangshop.utility.DialogUtils;
import com.mobile.tanahabangshop.utility.UiUtils;
import com.mobile.tanahabangshop.widget.CourierBottomSheetDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingCostFragment extends BaseFragment implements ShippingCostImplementer.View{

    private static final int PROVINCE_REQUEST = 0;
    private static final int CITY_REQUEST = 1;
    private Unbinder unbinder;
    private MainImplementer.View mainView;

    @BindView(R.id.provinceET)
    EditText provinceET;
    @BindView(R.id.inputProvinceLayout)
    TextInputLayout inputProvinceLayout;
    @BindView(R.id.cityET)
    EditText cityET;
    @BindView(R.id.inputCityLayout)
    TextInputLayout inputCityLayout;
    @BindView(R.id.courierNameET)
    EditText courierNameET;
    @BindView(R.id.inputCourierLayout)
    TextInputLayout inputCourierLayout;
    @BindView(R.id.weightET)
    EditText weightET;
    @BindView(R.id.inputLayoutWeight)
    TextInputLayout inputLayoutWeight;
    @BindView(R.id.calculateBtn)
    Button calculateBtn;
    @BindView(R.id.weightLayout)
    LinearLayout weightLayout;
    @BindView(R.id.shippingCostRV)
    RecyclerView shippingCostRV;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.scrollNavigationCV)
    CardView navigationCV;

    @Inject
    ShippingCostImplementer.Presenter presenter;

    public ShippingCostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof MainImplementer.View) {
            try {
                mainView = (MainImplementer.View) context;
            } catch (ClassCastException ex) {
                throw new ClassCastException(context.toString() + "must implement MainImplementer.View");
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shipping_cost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        mainView.isShowMenuItem(false);
        mainView.setupToolbar(true, "Biaya Pengiriman");
        shippingCostRV.setHasFixedSize(true);
        shippingCostRV.setLayoutManager(new LinearLayoutManager(getContext()));
        shippingCostRV.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        shippingCostRV.setNestedScrollingEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PROVINCE_REQUEST && data != null) {
                presenter.setProvinceCode(data.getIntExtra("province_code", 0));
                provinceET.setText(String.format("Provinsi %s", data.getStringExtra("province_name")));
                inputCityLayout.setVisibility(View.VISIBLE);
                if (cityET.getText().length() > 1) {
                    cityET.setText(null);
                }
                if (calculateBtn.getVisibility() == View.VISIBLE) {
                    calculateBtn.setVisibility(View.INVISIBLE);
                }
                if (shippingCostRV.getVisibility() == View.VISIBLE) {
                    shippingCostRV.setVisibility(View.GONE);
                }
                navigationCV.setVisibility(View.GONE);
            } else if (requestCode == CITY_REQUEST && data != null) {
                presenter.setCityCode(data.getIntExtra("city_code", 0));
                cityET.setText(data.getStringExtra("city_name"));
                if (inputCourierLayout.getVisibility() == View.VISIBLE && calculateBtn.getVisibility() == View.INVISIBLE) {
                    calculateBtn.setVisibility(View.VISIBLE);
                }
                inputCourierLayout.setVisibility(View.VISIBLE);
                navigationCV.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        presenter.destroy();
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mainView = null;
        super.onDetach();
    }

    @OnClick(R.id.scrollNavigationCV)
    void navigateToBottom(){
        navigationCV.setVisibility(View.GONE);
        scrollView.post(() -> scrollView.smoothScrollBy(0, shippingCostRV.getBottom()));
    }

    @OnClick({R.id.provinceET, R.id.cityET, R.id.courierNameET, R.id.calculateBtn})
    void click(View view) {
        switch (view.getId()) {
            case R.id.provinceET:
                openActivityWithResult(new AdministrativeActivity(), PROVINCE_REQUEST);
                break;
            case R.id.cityET:
                Bundle bundle = new Bundle();
                bundle.putInt("request_code", CITY_REQUEST);
                bundle.putInt("province_code", presenter.getProvinceCode());
                openActivityWithResult(new AdministrativeActivity(), CITY_REQUEST, bundle);
                break;
            case R.id.courierNameET:
                CourierBottomSheetDialog courierBottomSheetDialog = DialogUtils.showCourierDialog(getContext());
                courierBottomSheetDialog.publishSubject
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(s -> {
                            courierNameET.setText(s);
                            weightLayout.setVisibility(View.VISIBLE);
                            calculateBtn.setVisibility(View.VISIBLE);
                        })
                        .subscribe(s -> presenter.setCourierName(s));
                courierBottomSheetDialog.show();
                break;
            case R.id.calculateBtn:
                clearInputLayout(inputLayoutWeight);
                navigationCV.setVisibility(View.GONE);
                if (shippingCostRV.getVisibility() == View.VISIBLE) {
                    shippingCostRV.setVisibility(View.GONE);
                }
                if (weightET.getText().length() != 0 && !weightET.getText().toString().startsWith(".")) {
                    presenter.calculateShippingCost(Double.parseDouble(weightET.getText().toString()));
                } else if (weightET.getText().length() != 0 && weightET.getText().toString().startsWith(".")) {
                    if (weightET.getText().length() >= 2) {
                        String textAfterDot = weightET.getText().toString().substring(1);
                        weightET.setText(String.format("0.%s", textAfterDot));
                    } else {
                        weightET.setText("0.0");
                    }
                    presenter.calculateShippingCost(Double.parseDouble(weightET.getText().toString()));
                } else {
                    showWeightError("Masukkan berat barang terlebih dahulu");
                }
                break;
        }
    }

    @Override
    public void showShippingCostList(List<CostResult> costResultList) {
        shippingCostRV.setVisibility(View.VISIBLE);
        navigationCV.setVisibility(View.VISIBLE);
        ShippingCostAdapter shippingCostAdapter = new ShippingCostAdapter(costResultList, presenter.getCourierName());
        shippingCostRV.setAdapter(shippingCostAdapter);
    }

    @Override
    public void setUiEnable(boolean enabled) {
        inputProvinceLayout.setEnabled(enabled);
        inputCityLayout.setEnabled(enabled);
        inputCourierLayout.setEnabled(enabled);
        inputLayoutWeight.setEnabled(enabled);
        calculateBtn.setEnabled(enabled);
    }

    @Override
    public void showWeightError(String message) {
        inputLayoutWeight.setError(message);
        inputLayoutWeight.setErrorEnabled(true);
    }

    @Override
    public void showErrorConnection() {
        if (getContext() != null) {
            UiUtils.showSnackBar(coordinatorLayout,
                    "Gagal koneksi ke server. Mohon cek kembali koneksi anda",
                    ContextCompat.getColor(getContext(), android.R.color.holo_red_light),
                    ContextCompat.getColor(getContext(), android.R.color.white));
        }
    }
}
