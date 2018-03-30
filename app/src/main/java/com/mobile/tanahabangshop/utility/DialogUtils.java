package com.mobile.tanahabangshop.utility;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mobile.tanahabangshop.view.CourierBottomSheetDialog;
import com.mobile.tanahabangshop.view.MenuBottomSheetDialog;
import com.mobile.tanahabangshop.view.CustomDialog;

/**
 * Created by Lukas Dylan Adisurya on 19/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class DialogUtils {

    public static CustomDialog getLoadingDialog(Context context) {
        return CustomDialog.withContext(context)
                .setMessage("Loading...")
                .asLoadingScreen();
    }

    public static CustomDialog getConfirmationDialog(Context context,
                                                     CustomDialog.PositiveCallback positiveCallback,
                                                     CustomDialog.NegativeCallback negativeCallback,
                                                     String message) {
        return CustomDialog
                .withContext(context)
                .setMessage(message)
                .setPositiveCallback(positiveCallback)
                .setNegativeCallback(negativeCallback)
                .asConfirmScreen();
    }

    public static CustomDialog getSuccessDialog(Context context,
                                                CustomDialog.PositiveCallback positiveCallback,
                                                String message) {
        return CustomDialog
                .withContext(context)
                .setMessage(message)
                .setPositiveCallback(positiveCallback)
                .asSuccessScreen();
    }

    public static CustomDialog getFailedDialog(Context context,
                                               CustomDialog.PositiveCallback positiveCallback,
                                               String message) {
        return CustomDialog
                .withContext(context)
                .setMessage(message)
                .setPositiveCallback(positiveCallback)
                .asFailedScreen();
    }

    @NonNull
    public static MenuBottomSheetDialog showBottomSheetDialog(Context context) {
        return new MenuBottomSheetDialog(context);
    }

    @NonNull
    public static CourierBottomSheetDialog showCourierDialog(Context context) {
        return new CourierBottomSheetDialog(context);
    }
}
