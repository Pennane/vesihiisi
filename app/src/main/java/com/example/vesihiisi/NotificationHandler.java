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

import java.util.Date;

/**
 * Handles notifications. Receives alerts from MainActivity
 * and turns them into notifications in createNotification.
 * <p>
 * Base notification logic modified from stackoverflow
 *
 * @author Arttu Pennanen
 * @author Jimale Abdi (stack overflow)
 * @see <a href="https://stackoverflow.com/a/55910596/11212780">How to show a notification everyday at a certain time even when the app is closed?</a>
 */
public class NotificationHandler extends BroadcastReceiver {
    private static final String NOTIFICATION_CHANNEL_ID = "10002";
    private static final String NOTIFICATION_CHANNEL_NAME = "Veden nauttiminen";

    /**
     * Alarm handler. If daily consumption is not filled, it creates a notification.
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Date now = new Date();
        DayData todayDayData = Global.readSpecificDayData(now);

        if (todayDayData.getConsumption() < todayDayData.getTargetConsumption()) {
            createHydrationNotification(context, todayDayData);
        }
    }

    /**
     * Creates and serves a hydration notification.
     * The notification show how much water you still should drink that day.
     * <p>
     * Has a custom notification sound and icon.
     * <p>
     * Has different features for newer android versions.
     *
     * @param context
     * @param dayData for the current day
     */
    private void createHydrationNotification(Context context, DayData dayData) {
        Intent targetIntent = new Intent(context, MainActivity.class);
        targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent afterOpenIntent = PendingIntent.getActivity(
                context,
                0, targetIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);

        // Build the notification
        builder
                .setSmallIcon(R.mipmap.vesihiisi_launcher)
                .setContentTitle("Vesihiisi huomauttaa")
                .setContentText("Sinun pitäisi juoda tänään vielä " +
                        Integer.toString(
                                dayData.getTargetConsumption() - dayData.getConsumption()
                        ) + "ml vettä!")
                .setAutoCancel(false)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.siuunotification))
                .setContentIntent(afterOpenIntent);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        // If current SDK version is too low, exit early and send without newer features.
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            assert notificationManager != null;
            notificationManager.notify(0, builder.build());
            return;
        }

        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.enableVibration(true);
        assert notificationManager != null;
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        notificationManager.createNotificationChannel(notificationChannel);

        assert notificationManager != null;
        notificationManager.notify(0, builder.build());
    }
}