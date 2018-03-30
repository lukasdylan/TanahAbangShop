package com.mobile.tanahabangshop.service.backgroundreceiver;

import android.os.Build;

import com.mobile.tanahabangshop.data.network.RajaOngkirProvince;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lukas Dylan Adisurya on 3/8/2018.
 */

public class BackgroundReceiverPresenter implements BackgroundReceiverImplementer.Presenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final BackgroundReceiverImplementer.Service service;
    private final BackgroundReceiverImplementer.Model model;

    public BackgroundReceiverPresenter(BackgroundReceiverImplementer.Service service,
                                       BackgroundReceiverImplementer.Model model) {
        this.service = service;
        this.model = model;
    }

    @Override
    public void testLoadProvinceList() {
        compositeDisposable.add(model.fetchProvinceList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(provinceResponse -> {
                    RajaOngkirProvince rajaOngkirProvince = provinceResponse.getRajaongkir();
                    if (rajaOngkirProvince.getStatus().getCode() == 200) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            service.showNotificationOreo("Jumlah provinsi di Indonesia adalah " + rajaOngkirProvince.getResults().size());
                        } else {
                            service.showNotification("Jumlah provinsi di Indonesia adalah " + rajaOngkirProvince.getResults().size());
                        }
                    }
                }, throwable -> {
                    if (throwable instanceof UnknownHostException
                            || throwable instanceof SocketTimeoutException) {

                    }
                }));
    }
}
