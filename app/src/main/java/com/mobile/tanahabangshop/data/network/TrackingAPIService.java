package com.mobile.tanahabangshop.data.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Lukas Dylan Adisurya on 18/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public interface TrackingAPIService {
    @GET("province")
    Observable<ProvinceResponse> getProvinceResponse(@Header("key") String apiKey);

    @GET("city")
    Observable<CityResponse> getCityResponse(@Header("key") String apiKey, @Query("province") String provinceCode);
}
