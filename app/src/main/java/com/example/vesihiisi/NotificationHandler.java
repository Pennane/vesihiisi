package com.example.vesihiisi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

/**
 * Handles notifications. Receives alerts from MainActivity
 * and turns them into notifications in createNotification.
 *
 * Inspiration from https://stackoverflow.com/a/55910596/11212780
 */
public class NotificationHandler extends BroadcastReceiver {
    private static final String NOTIFICATION_CHANNEL_ID = "10002";
    private static final String NOTIFICATION_CHANNEL_NAME = "WATER_NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context);
    }

    private void createNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context,
                0 , intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Vesihiisi huomauttaa")
                .setContentText("Juo vettä!")
                .setAutoCancel(false)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.siuunotification))
                .setContentIntent(resultPendingIntent);


        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            assert mNotificationManager != null;
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0, builder.build());
    }
}