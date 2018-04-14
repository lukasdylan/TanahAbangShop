package com.mobile.tanahabangshop.ui.detailproduct;

import com.mobile.tanahabangshop.data.model.DummyProduct;

/**
 * Created by Lukas Dylan Adisurya <lukas.adisurya@ovo.id}>
 * on 4/7/2018.
 */

public interface DetailProductImplementer {
    interface View {
        void initView(DummyProduct dummyProduct);

        void showFavoriteMessage(boolean isFavorite);
    }

    interface Presenter {
        void setProductAsFavorite(boolean isFavorite);

        void loadProductData(DummyProduct dummyProduct);

        DummyProduct getDummyProduct();

        void destroy();
    }

    interface Model {

    }
}
