package com.example.vesihiisi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class MainActivity extends NavigationBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize global preferences
        Global.initialize(getApplicationContext());

        // Redirect the user to settings if the settings are invalid or unset
        tryRedirectToSettings();
        consumeWaterAlarmSchedule();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tryRedirectToSettings();
    }

    /**
     * Method that redirects the current activity to settings if saved preferences are invalid
     */
    private void tryRedirectToSettings() {
        if (!Global.isValidAge(Global.read("age", -1)) || !Global.isValidWeight(Global.read("weight", -1)) || !Global.isValidGender(Global.read("gender", "invalid"))) {
            Intent newActivity = new Intent(this, SettingsActivity.class);
            startActivity(newActivity);
        }
    }

    /**
     * Schedule alarm for water consumption
     */
    public void consumeWaterAlarmSchedule() {
        Intent intent = new Intent(getApplicationContext(), NotificationHandler.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager == null) {
            return;
        }

        // Schedule alarm avery 3 minutes for testing purposes
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                60 * 1000 * 3,
                pendingIntent);
    }
}