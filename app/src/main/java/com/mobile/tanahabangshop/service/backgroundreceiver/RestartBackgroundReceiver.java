package com.mobile.tanahabangshop.service.backgroundreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Lukas Dylan Adisurya on 3/8/2018.
 */

public class RestartBackgroundReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, BackgroundReceiver.class));
    }
}
