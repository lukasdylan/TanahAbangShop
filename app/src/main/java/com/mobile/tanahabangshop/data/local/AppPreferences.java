package com.mobile.tanahabangshop.data.local;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Lukas Dylan Adisurya on 18/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class AppPreferences {
    private final SharedPreferences sharedPreferences;

    @Inject
    public AppPreferences(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public void setValue(String key, Object value) {
        removeData(key);
        if (value.getClass().isAssignableFrom(String.class)) {
            sharedPreferences.edit().putString(key, (String) value).apply();
        } else if (value.getClass().isAssignableFrom(Boolean.class)) {
            sharedPreferences.edit().putBoolean(key, (Boolean) value).apply();
        } else if (value.getClass().isAssignableFrom(Integer.class)) {
            sharedPreferences.edit().putInt(key, (Integer) value).apply();
        } else if (value.getClass().isAssignableFrom(Long.class)) {
            sharedPreferences.edit().putLong(key, (Long) value).apply();
        } else if (value.getClass().isAssignableFrom(Float.class)) {
            sharedPreferences.edit().putFloat(key, (Float) value).apply();
        } else if (value.getClass().isAssignableFrom(Double.class)) {
            sharedPreferences.edit().putLong(key, Double.doubleToLongBits((Double)value)).apply();
        } else {
            Timber.e(Thread.currentThread().getStackTrace()[2].getMethodName() + " ] " + "None");
        }
    }

    public <T> void setListValue(String key, List<T> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        removeData(key);
        sharedPreferences.edit().putString(key, json).apply();
    }

    public int getIntegerValue(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public String getStringValue(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public long getLongValue(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public float getFloatValue(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public double getDoubleValue(String key, double defaultValue){
        return Double.longBitsToDouble(sharedPreferences.getLong(key,Double.doubleToLongBits(defaultValue)));
    }

    private void removeData(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
