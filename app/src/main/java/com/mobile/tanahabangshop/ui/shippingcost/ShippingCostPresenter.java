package com.mobile.tanahabangshop.ui.shippingcost;

import android.support.annotation.NonNull;

import com.mobile.tanahabangshop.data.network.RajaOngkirCost;

import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lukas Dylan Adisurya on 3/4/2018.
 */

public class ShippingCostPresenter implements ShippingCostImplementer.Presenter {

    private ShippingCostImplementer.View view;
    private ShippingCostImplementer.Model model;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    ShippingCostPresenter(ShippingCostImplementer.View view, ShippingCostImplementer.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void calculateShippingCost(int destinationCode, double weightTotal, String courierName) {
        if (validateWeight(weightTotal)) {
            String paramCourierName = validateCourierName(courierName);
            int paramWeightTotal = (int) (weightTotal * 1000);
            compositeDisposable.add(
                    model.fetchShippingCost(String.valueOf(destinationCode), paramWeightTotal, paramCourierName)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(disposable -> view.setUiEnable(false))
                            .doAfterTerminate(() -> view.setUiEnable(true))
                            .subscribe(shippingCostResponse -> {
                                RajaOngkirCost rajaOngkirCost = shippingCostResponse.getRajaongkir();
                                if (rajaOngkirCost.getStatus().getCode() == 200) {
                                    view.showShippingCostList(rajaOngkirCost.getResults().get(0).getCosts());
                                } else {
                                    view.showShippingCostList(Collections.emptyList());
                                }
                            }));
        } else {
            view.showWeightError("Maksimal pengecekan berat barang 30kg");
        }
    }

    @NonNull
    private String validateCourierName(String courierName) {
        if (courierName.equalsIgnoreCase("JNE")) {
            return "jne";
        } else if (courierName.equalsIgnoreCase("Pos Indonesia")) {
            return "pos";
        } else {
            return "tiki";
        }
    }

    private boolean validateWeight(double weightTotal) {
        return weightTotal <= 30.0;
    }

    @Override
    public void destroy() {
        compositeDisposable.dispose();
    }
}
