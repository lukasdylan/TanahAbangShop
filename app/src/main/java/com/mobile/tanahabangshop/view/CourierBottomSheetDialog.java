package com.mobile.tanahabangshop.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;

import com.mobile.tanahabangshop.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public class CourierBottomSheetDialog extends BottomSheetDialog {

    private CourierCallback courierCallback;

    public CourierBottomSheetDialog(@NonNull Context context, CourierCallback courierCallback) {
        super(context);
        this.courierCallback = courierCallback;
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.courier_bottom_sheet_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.jneLayout)
    void clickJne(){
        dismiss();
        courierCallback.onSelectedCourier("JNE");
    }

    @OnClick(R.id.posIndonesiaLayout)
    void clickPosIndonesia(){
        dismiss();
        courierCallback.onSelectedCourier("Pos Indonesia");
    }

    @OnClick(R.id.tikiLayout)
    void clickTiki(){
        dismiss();
        courierCallback.onSelectedCourier("TIKI");
    }

    @OnClick(R.id.closeBtn)
    void closeDialog() {
        dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public interface CourierCallback {
        void onSelectedCourier(String name);
    }
}
