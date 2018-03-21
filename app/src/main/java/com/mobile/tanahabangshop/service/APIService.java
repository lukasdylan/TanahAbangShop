package com.mobile.tanahabangshop.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Lukas Dylan Adisurya on 17/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public interface APIService {
    @POST("login.php")
    @FormUrlEncoded
    Observable<ResponseBody> login(@Field("nohp") String nohp,
                                   @Field("password") String password);

    @POST("register.php")
    @FormUrlEncoded
    Observable<ResponseBody> register(@Field("name") String name,
                                      @Field("phone_number") String nohp,
                                      @Field("password") String password,
                                      @Field("device_id") String device_id);
}
