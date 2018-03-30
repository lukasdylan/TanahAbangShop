package com.mobile.tanahabangshop.ui.administrative;

import com.mobile.tanahabangshop.data.network.RajaOngkirCity;
import com.mobile.tanahabangshop.data.network.RajaOngkirProvince;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public class AdministrativePresenter implements AdministrativeImplementer.Presenter {

    private final AdministrativeImplementer.View view;
    private final AdministrativeImplementer.Model model;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    AdministrativePresenter(AdministrativeImplementer.View view, AdministrativeImplementer.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadProvince() {
        compositeDisposable.add(model.fetchProvinceList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(view::hideLoading)
                .subscribe(provinceResponse -> {
                    RajaOngkirProvince rajaOngkirProvince = provinceResponse.getRajaongkir();
                    if (rajaOngkirProvince.getStatus().getCode() == 200) {
                        view.showProvinceList(rajaOngkirProvince.getResults());
                    } else {
                        view.showProvinceList(Collections.emptyList());
                    }
                }, throwable -> {
                    if (throwable instanceof UnknownHostException
                            || throwable instanceof SocketTimeoutException) {
                        view.showFailed();
                    }
                }));
    }

    @Override
    public void loadCity(int provinceCode) {
        compositeDisposable.add(model.fetchCityRegionList(String.valueOf(provinceCode))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(view::hideLoading)
                .subscribe(cityResponse -> {
                    RajaOngkirCity rajaOngkirCity = cityResponse.getRajaongkir();
                    if (rajaOngkirCity.getStatus().getCode() == 200) {
                        view.showCityList(rajaOngkirCity.getResults());
                    } else {
                        view.showCityList(Collections.emptyList());
                    }
                }, throwable -> {
                    if (throwable instanceof UnknownHostException
                            || throwable instanceof SocketTimeoutException) {
                        view.showFailed();
                    }
                }));
    }

    @Override
    public void destroy() {
        compositeDisposable.dispose();
    }
}
