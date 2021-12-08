package com.example.vesihiisi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

/**
 * Default Activity, the main screen of the application
 */
public class MainActivity extends NavigationBarActivity {
    DayData dayData;
    TextView targetConsumption, consumption, message;
    MotivationMessages motivationMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize global preferences
        Global.initialize(getApplicationContext());

        // Schedule notifications
        consumeWaterAlarmSchedule();

        // Redirect the user to settings if the settings are invalid or unset
        if (tryRedirectToSettings()) {
            return;
        }

        // Set a random motivational message
        message = findViewById(R.id.textView4);
        motivationMessages = new MotivationMessages();
        message.setText(motivationMessages.getRandomMessage());


        dayData = Global.readSpecificDayData(new Date());
        targetConsumption = findViewById(R.id.textView2);
        targetConsumption.setText(Double.toString(dayData.getTargetConsumption()) + "ml");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (tryRedirectToSettings()) {
            return;
        }
        dayData = Global.readSpecificDayData(new Date());
        updateConsumptionValue();
    }

    /**
     * Display current water consumption amount on the activity
     */
    public void updateConsumptionValue() {
        consumption = findViewById(R.id.textView);
        consumption.setText(Double.toString(dayData.getConsumption()) + "ml");
        Global.writeSpecificDayData(dayData, new Date());
    }

    public void onGlassButtonClick(View view) {
        dayData.consume(250);
        updateConsumptionValue();
    }

    public void onBottleButtonClick(View view) {
        dayData.consume(500);
        updateConsumptionValue();
    }

    public void onJugButtonClick(View view) {
        dayData.consume(1000);
        updateConsumptionValue();
    }

    /**
     * Redirect the current activity to settings, if saved preferences are invalid
     *
     * @return boolean
     */
    private boolean tryRedirectToSettings() {
        if (!Global.isValidAge(Global.readPreference("age", -1)) || !Global.isValidWeight(Global.readPreference("weight", -1)) || !Global.isValidGender(Global.readPreference("gender", "invalid"))) {
            Intent newActivity = new Intent(this, SettingsActivity.class);
            startActivity(newActivity);
            return true;
        }
        return false;
    }

    /**
     * Schedule alarm for water consumption notifications
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