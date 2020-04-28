package com.aliya.base.sample.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * NotificationClickReceiver
 *
 * @author a_liYa
 * @date 2020/4/28 14:12.
 */
public class NotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TAG", "onReceive: NotificationClickReceiver@" + hashCode());
    }

}
