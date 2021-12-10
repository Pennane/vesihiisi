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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Handles notifications. Receives alerts from MainActivity
 * and turns them into notifications in createNotification.
 * <p>
 * Base notification logic modified from stackoverflow
 *
 * @author Arttu Pennanen
 * @see <a href="https://stackoverflow.com/a/55910596/11212780">How to show a notification everyday at a certain time even when the app is closed?</a>
 */
public class NotificationHandler extends BroadcastReceiver {
    public static final String NOTIFICATION_TYPE = "com.example.vesihiisi.NOTIFICATION_TYPE";
    private static final String NOTIFICATION_CHANNEL_ID = "10002";
    private static final String NOTIFICATION_CHANNEL_NAME = "Veden nauttiminen";

    /**
     * Alarm handler. Reads type of notification from intent extra NOTIFICATION_TYPE
     * <p>
     * Ignores alarms generated between 22:00 and 08:00
     *
     * @param context of an activity
     * @param intent  should have stringExtra NOTIFICATION_TYPE
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Date now = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(now);

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        if (currentHour < 8 || currentHour > 22) {
            return;
        }

        String notificationType = intent.getStringExtra(NOTIFICATION_TYPE);

        if (notificationType.equals("CONSUME")) {
            createConsumptionNotification(context);
        }
    }

    /**
     * Creates a notification that tells how much more water user should drink that day.
     *
     * @param context should be passed from onReceive
     */
    public void createConsumptionNotification(Context context) {
        Date now = new Date();
        DayData todayDayData = Global.readSpecificDayData(now);

        if (todayDayData.getConsumption() >= todayDayData.getTargetConsumption()) {
            return;
        }

        String message = "Sinun tulisi juoda tänään vielä " +
                (todayDayData.getTargetConsumption() - todayDayData.getConsumption())
                + "ml vettä!";

        createNotification(context, message);
    }

    /**
     * Creates and serves a notification.
     * <p>
     * Has a custom notification sound and an icon.
     * <p>
     * Has notificationChannel features for newer android versions.
     *
     * @param context should be passed from onReceive or another notification method
     * @param message for the notification body
     */
    private void createNotification(Context context, String message) {
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
                .setSmallIcon(R.drawable.vesihiisi_notification)
                .setContentTitle("Vesihiisi huomauttaa:")
                .setContentText(message)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.siuunotification))
                .setContentIntent(afterOpenIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // If current SDK version is too low, exit early and send without newer notification channel features.
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            assert notificationManager != null;
            notificationManager.notify(0, builder.build());
            return;
        }

        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.enableVibration(true);

        builder.setChannelId(NOTIFICATION_CHANNEL_ID);

        notificationManager.createNotificationChannel(notificationChannel);

        notificationManager.notify(0, builder.build());
    }
}