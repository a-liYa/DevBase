package com.aliya.base.sample.common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.aliya.base.AppUtils;
import com.aliya.base.sample.R;

/**
 * NotificationHelper
 *
 * @author a_liYa
 * @date 2020/4/28 10:57.
 */
public final class NotificationHelper {

    private static int notify_id = 0;
    private static long mLastNotifyTime;
    private static boolean sNotificationChannelCreated;

    private static final long NO_DISTURBING = 1500; // 免打扰间隔时间
    private static final String CHANNEL_ID = "default";

    public static void notify(String title, String content, Intent intent, Component type) {
        Context context = AppUtils.getContext();
        createNotificationChannel(context);
        PendingIntent pendingIntent = buildContentIntent(context, intent, type);
        Notification notification = buildNotification(context, title, content, pendingIntent);
        getManager(context).notify(notify_id++, notification);
    }

    private static Notification buildNotification(Context context, String title, String content,
                                                  PendingIntent intent) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentIntent(intent)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setSmallIcon(R.mipmap.ic_small_status_bar)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(content);
        builder.setStyle(new NotificationCompat.BigTextStyle());
        if (SystemClock.uptimeMillis() - mLastNotifyTime < NO_DISTURBING) {
            builder.setDefaults(Notification.DEFAULT_LIGHTS);
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }
        mLastNotifyTime = SystemClock.uptimeMillis();
        return builder.build();
    }

    private static void createNotificationChannel(Context context) {
        if (sNotificationChannelCreated) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sNotificationChannelCreated = true;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "消息推送", importance);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager(context).createNotificationChannel(channel);
        }
    }

    private static PendingIntent buildContentIntent(Context context, Intent intent, Component type) {
        int flags = PendingIntent.FLAG_CANCEL_CURRENT;
        switch (type) {
            case SERVICE:
                return PendingIntent.getService(context, notify_id, intent, flags);
            case BROADCAST:
                return PendingIntent.getBroadcast(context, notify_id, intent, flags);
            default:
                // 第二个参数不能重复，否则点击事件会没有响应
            return PendingIntent.getActivity(context, notify_id, intent, flags);
        }
    }

    private static NotificationManager getManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public enum Component {
        ACTIVITY(),
        SERVICE(),
        BROADCAST();
    }
}
