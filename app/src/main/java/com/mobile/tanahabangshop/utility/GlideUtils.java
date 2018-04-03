package com.mobile.tanahabangshop.utility;

import android.annotation.SuppressLint;

import com.bumptech.glide.Priority;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.tanahabangshop.R;

/**
 * Created by Lukas Dylan Adisurya <lukas.adisurya@ovo.id}>
 * on 4/1/2018.
 */

@SuppressLint("CheckResult")
@GlideExtension
public class GlideUtils {

    private GlideUtils() {

    }

    @GlideOption
    public static void withListOptions(RequestOptions requestOptions) {
        requestOptions.centerCrop()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .format(DecodeFormat.PREFER_RGB_565)
                .placeholder(R.color.lightGray)
                .error(android.R.color.holo_red_light);
    }

    @GlideOption
    public static void withDetailOptions(RequestOptions requestOptions) {
        requestOptions.priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .format(DecodeFormat.PREFER_RGB_565)
                .placeholder(R.color.lightGray)
                .error(android.R.color.holo_red_light);
    }
}
