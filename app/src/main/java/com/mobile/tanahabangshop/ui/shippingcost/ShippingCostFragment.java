package com.mobile.tanahabangshop.ui.shippingcost;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.network.CostResult;
import com.mobile.tanahabangshop.ui.administrative.AdministrativeActivity;
import com.mobile.tanahabangshop.ui.main.MainImplementer;
import com.mobile.tanahabangshop.utility.DialogUtils;
import com.mobile.tanahabangshop.view.CourierBottomSheetDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingCostFragment extends Fragment implements ShippingCostImplementer.View,
        CourierBottomSheetDialog.CourierCallback {

    private static final int PROVINCE_REQUEST = 0;
    private static final int CITY_REQUEST = 1;
    private Unbinder unbinder;
    private MainImplementer.View mainView;
    private int provinceCode = 0;
    private int cityCode = 0;
    private String courierName = "";

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
        shippingCostRV.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        shippingCostRV.setNestedScrollingEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PROVINCE_REQUEST && data != null) {
                provinceCode = data.getIntExtra("province_code", 0);
                provinceET.setText(String.format("Provinsi %s", data.getStringExtra("province_name")));
                inputCityLayout.setVisibility(View.VISIBLE);
                if (cityET.getText().length() > 1) {
                    cityET.setText(null);
                }
                if (calculateBtn.getVisibility() == View.VISIBLE) {
                    calculateBtn.setVisibility(View.INVISIBLE);
                }
                if(shippingCostRV.getVisibility() == View.VISIBLE){
                    shippingCostRV.setVisibility(View.GONE);
                }
            } else if (requestCode == CITY_REQUEST && data != null) {
                cityCode = data.getIntExtra("city_code", 0);
                cityET.setText(data.getStringExtra("city_name"));
                if (inputCourierLayout.getVisibility() == View.VISIBLE && calculateBtn.getVisibility() == View.INVISIBLE) {
                    calculateBtn.setVisibility(View.VISIBLE);
                }
                inputCourierLayout.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.provinceET, R.id.cityET, R.id.courierNameET, R.id.calculateBtn})
    void click(View view) {
        switch (view.getId()) {
            case R.id.provinceET:
                Intent provinceIntent = new Intent(getContext(), AdministrativeActivity.class);
                provinceIntent.putExtra("request_code", PROVINCE_REQUEST);
                startActivityForResult(provinceIntent, PROVINCE_REQUEST);
                break;
            case R.id.cityET:
                Intent cityIntent = new Intent(getContext(), AdministrativeActivity.class);
                cityIntent.putExtra("request_code", CITY_REQUEST);
                cityIntent.putExtra("province_code", provinceCode);
                startActivityForResult(cityIntent, CITY_REQUEST);
                break;
            case R.id.courierNameET:
                CourierBottomSheetDialog courierBottomSheetDialog = DialogUtils.showCourierDialog(getContext(), this);
                courierBottomSheetDialog.show();
                break;
            case R.id.calculateBtn:
                if (inputLayoutWeight.isErrorEnabled()) {
                    inputLayoutWeight.setErrorEnabled(false);
                    inputLayoutWeight.setError(null);
                }
                if (shippingCostRV.getVisibility() == View.VISIBLE) {
                    shippingCostRV.setVisibility(View.GONE);
                }
                if (weightET.getText().length() != 0 && !weightET.getText().toString().startsWith(".")) {
                    presenter.calculateShippingCost(cityCode, Double.parseDouble(weightET.getText().toString()), courierName);
                } else if (weightET.getText().length() != 0 && weightET.getText().toString().startsWith(".")) {
                    if(weightET.getText().length() >= 2){
                        String textAfterDot = weightET.getText().toString().substring(1);
                        weightET.setText(String.format("0.%s", textAfterDot));
                    } else {
                        weightET.setText("0.0");
                    }
                    presenter.calculateShippingCost(cityCode, Double.parseDouble(weightET.getText().toString()), courierName);
                } else {
                    showWeightError("Masukkan berat barang terlebih dahulu");
                }
                break;
        }
    }

    @Override
    public void onSelectedCourier(String name) {
        courierName = name;
        courierNameET.setText(courierName);
        weightLayout.setVisibility(View.VISIBLE);
        calculateBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showShippingCostList(List<CostResult> costResultList) {
        shippingCostRV.setVisibility(View.VISIBLE);
        ShippingCostAdapter shippingCostAdapter = new ShippingCostAdapter(costResultList, courierName);
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
}
