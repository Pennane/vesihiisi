package com.example.vesihiisi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends NavigationBarActivity {
    Counter waterCounter;
    TextView targetConsumption, consumption, message;
    MotivationMessages motivationMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize global preferences
        Global.initialize(getApplicationContext());

        // Redirect the user to settings if the settings are invalid or unset
        tryRedirectToSettings();
        consumeWaterAlarmSchedule();

        waterCounter = new Counter(Global.read("weight", 0), Global.read("age", 0));
        targetConsumption = findViewById(R.id.textView2);
        targetConsumption.setText(Double.toString(waterCounter.getTargetConsumption()) + "ml");
        message = findViewById(R.id.textView4);
        motivationMessages = new MotivationMessages();
        message.setText(motivationMessages.getRandomMessage());
    }


    @Override
    protected void onResume() {
        super.onResume();
        tryRedirectToSettings();
        waterCounter.setConsumption(Global.read("consumption", 0));
        updateConsumptionViewValue();
    }
    protected void onPause() {

        super.onPause();
        message.setText(motivationMessages.getRandomMessage());
    }


    public void updateConsumptionViewValue() {
        consumption = findViewById(R.id.textView);
        consumption.setText(Double.toString(waterCounter.getConsume()) + "ml");
        Global.write("consumption", (int) waterCounter.getConsume());
    }

    public void onGlassButtonClick(View view) {
        waterCounter.consume(250);
        updateConsumptionViewValue();
    }

    public void onBottleButtonClick(View view) {
        waterCounter.consume(500);
        updateConsumptionViewValue();
    }

    public void onJugButtonClick(View view) {
        waterCounter.consume(1000);
        updateConsumptionViewValue();
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