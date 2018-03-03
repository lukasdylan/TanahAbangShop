package com.mobile.tanahabangshop.ui.administrative;

import com.mobile.tanahabangshop.data.network.CityResponse;
import com.mobile.tanahabangshop.data.network.CityResult;
import com.mobile.tanahabangshop.data.network.ProvinceResponse;
import com.mobile.tanahabangshop.data.network.ProvinceResult;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Lukas Dylan Adisurya on 3/3/2018.
 */

public interface AdministrativeImplementer {
    interface View {
        void showProvinceList(List<ProvinceResult> provinceResultList);

        void showCityList(List<CityResult> cityResultList);

        void hideLoading();

        void showFailed();
    }

    interface Presenter {
        void loadProvince();

        void loadCity(int provinceCode);

        void destroy();
    }

    interface Model {
        Observable<ProvinceResponse> fetchProvinceList();

        Observable<CityResponse> fetchCityRegionList(String provinceCode);
    }

    interface ProvinceAdapter {
        void onSelectedProvince(int provinceCode, String provinceName);
    }

    interface CityRegionAdapter {
        void onSelectedCityRegion(int code, String type, String name);
    }
}
