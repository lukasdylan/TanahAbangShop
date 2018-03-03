package com.mobile.tanahabangshop.ui.main.shippingcost;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.ui.administrative.AdministrativeActivity;
import com.mobile.tanahabangshop.ui.main.MainImplementer;
import com.mobile.tanahabangshop.utility.DialogUtils;
import com.mobile.tanahabangshop.view.CourierBottomSheetDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingCostFragment extends Fragment implements CourierBottomSheetDialog.CourierCallback{

    private static final int PROVINCE_REQUEST = 0;
    private static final int CITY_REQUEST = 1;
    private Unbinder unbinder;
    private MainImplementer.View mainView;
    private int provinceCode = 0;
    private int cityCode = 0;
    private String courierName = "";

    @BindView(R.id.provinceTV)
    TextView provinceTV;
    @BindView(R.id.cityTV)
    TextView cityTV;
    @BindView(R.id.courierNameTV)
    TextView courierNameTV;
    @BindView(R.id.calculateBtn)
    Button calculateBtn;
    @BindView(R.id.weightLayout)
    LinearLayout weightLayout;

    public ShippingCostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PROVINCE_REQUEST && data != null) {
                provinceCode = data.getIntExtra("province_code", 0);
                provinceTV.setText(String.format("Provinsi %s", data.getStringExtra("province_name")));
                cityTV.setVisibility(View.VISIBLE);
                if (cityTV.getText().length() > 1) {
                    cityTV.setText(null);
                }
                if (calculateBtn.getVisibility() == View.VISIBLE) {
                    calculateBtn.setVisibility(View.INVISIBLE);
                }
            } else if (requestCode == CITY_REQUEST && data != null) {
                cityCode = data.getIntExtra("city_code", 0);
                cityTV.setText(data.getStringExtra("city_name"));
                courierNameTV.setVisibility(View.VISIBLE);
                if (calculateBtn.getVisibility() == View.VISIBLE) {
                    calculateBtn.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mainView = null;
        super.onDetach();
    }

    @OnClick({R.id.provinceTV, R.id.cityTV, R.id.courierNameTV, R.id.calculateBtn})
    void click(View view) {
        switch (view.getId()) {
            case R.id.provinceTV:
                Intent provinceIntent = new Intent(getContext(), AdministrativeActivity.class);
                provinceIntent.putExtra("request_code", PROVINCE_REQUEST);
                startActivityForResult(provinceIntent, PROVINCE_REQUEST);
                break;
            case R.id.cityTV:
                Intent cityIntent = new Intent(getContext(), AdministrativeActivity.class);
                cityIntent.putExtra("request_code", CITY_REQUEST);
                cityIntent.putExtra("province_code", provinceCode);
                startActivityForResult(cityIntent, CITY_REQUEST);
                break;
            case R.id.courierNameTV:
                CourierBottomSheetDialog courierBottomSheetDialog = DialogUtils.showCourierDialog(getContext(), this);
                courierBottomSheetDialog.show();
                break;
            case R.id.calculateBtn:
                break;
        }
    }

    @Override
    public void onSelectedCourier(String name) {
        courierName = name;
        courierNameTV.setText(courierName);
        weightLayout.setVisibility(View.VISIBLE);
        calculateBtn.setVisibility(View.VISIBLE);
    }
}
