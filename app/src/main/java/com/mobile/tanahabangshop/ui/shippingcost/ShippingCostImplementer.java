package com.mobile.tanahabangshop.ui.shippingcost;

import com.mobile.tanahabangshop.data.network.CostResult;
import com.mobile.tanahabangshop.data.network.ShippingCostResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 3/4/2018.
 */

public interface ShippingCostImplementer {
    interface View {
        void showShippingCostList(List<CostResult> costResultList);

        void setUiEnable(boolean enabled);

        void showWeightError(String message);

        void showErrorConnection();
    }

    interface Presenter {
        void setProvinceCode(int provinceCode);

        void setCityCode(int cityCode);

        void setCourierName(String courierName);

        int getProvinceCode();

        String getCourierName();

        void calculateShippingCost(double weightTotal);

        void destroy();
    }

    interface Model {
        Observable<ShippingCostResponse> fetchShippingCost(String destinationCode, int weightTotal, String courierName);
    }
}
