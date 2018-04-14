package com.mobile.tanahabangshop.ui.detailproduct;

import com.mobile.tanahabangshop.data.model.DummyProduct;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Lukas Dylan Adisurya <lukas.adisurya@ovo.id}>
 * on 4/7/2018.
 */

public class DetailProductPresenter implements DetailProductImplementer.Presenter {

    private DummyProduct dummyProduct;
    private final DetailProductImplementer.View view;
    private final DetailProductImplementer.Model model;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    DetailProductPresenter(DetailProductImplementer.View view, DetailProductImplementer.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void setProductAsFavorite(boolean isFavorite) {
        view.showFavoriteMessage(isFavorite);
        dummyProduct.setFavorite(isFavorite);
    }

    @Override
    public void loadProductData(DummyProduct dummyProduct) {
        this.dummyProduct = dummyProduct;
        view.initView(dummyProduct);
    }

    @Override
    public DummyProduct getDummyProduct() {
        return dummyProduct;
    }

    @Override
    public void destroy() {
        dummyProduct = null;
        compositeDisposable.dispose();
    }
}
