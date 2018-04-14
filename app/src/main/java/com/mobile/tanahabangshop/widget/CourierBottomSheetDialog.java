package com.mobile.tanahabangshop.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;

import com.mobile.tanahabangshop.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public class CourierBottomSheetDialog extends BottomSheetDialog {

    public final PublishSubject<String> publishSubject = PublishSubject.create();

    public CourierBottomSheetDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.courier_bottom_sheet_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.jneLayout)
    void clickJne(){
        dismiss();
        publishSubject.onNext("JNE");
    }

    @OnClick(R.id.posIndonesiaLayout)
    void clickPosIndonesia(){
        dismiss();
        publishSubject.onNext("Pos Indonesia");
    }

    @OnClick(R.id.tikiLayout)
    void clickTiki(){
        dismiss();
        publishSubject.onNext("TIKI");
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
