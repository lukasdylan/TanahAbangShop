package com.mobile.tanahabangshop.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobile.tanahabangshop.BuildConfig;
import com.mobile.tanahabangshop.data.constant.GlobalConstant;
import com.mobile.tanahabangshop.data.network.APIService;
import com.mobile.tanahabangshop.data.network.TrackingAPIService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lukas Dylan Adisurya on 17/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

@Module
public class NetworkModule {
    @Provides
    @Singleton
    OkHttpClient provideHttpClient(HttpLoggingInterceptor logger, Cache cache) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(logger);
        builder.cache(cache);
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        return builder.build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    Cache provideCache(File file) {
        return new Cache(file, 10 * 1024 * 1024);
    }

    @Provides
    @Singleton
    File provideCacheFile(Context context) {
        return context.getFilesDir();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonClient(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxAdapter() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    APIService provideApiService(OkHttpClient client, GsonConverterFactory gson, RxJava2CallAdapterFactory rxAdapter) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(GlobalConstant.API_BASE_URL)
                .addConverterFactory(gson)
                .addCallAdapterFactory(rxAdapter)
                .build();

        return retrofit.create(APIService.class);
    }

    @Provides
    TrackingAPIService provideTrackingApiService(OkHttpClient client, GsonConverterFactory gson, RxJava2CallAdapterFactory rxAdapter) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(GlobalConstant.TRACKING_API_BASE_URL)
                .addConverterFactory(gson)
                .addCallAdapterFactory(rxAdapter)
                .build();

        return retrofit.create(TrackingAPIService.class);
    }
}
