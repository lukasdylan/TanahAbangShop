package com.mobile.tanahabangshop.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobile.tanahabangshop.CoreApplication;
import com.mobile.tanahabangshop.data.constant.GlobalConstant;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lukas Dylan Adisurya on 17/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

@Module
public class AppContextModule {

    @Provides
    Context provideContext(CoreApplication coreApplication){
        return coreApplication.getApplicationContext();
    }

    @Provides
    SharedPreferences provideSharedPreferences(Context context){
        return context.getSharedPreferences(GlobalConstant.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
