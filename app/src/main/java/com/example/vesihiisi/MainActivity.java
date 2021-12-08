package com.example.vesihiisi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends NavigationBarActivity {
    Counter waterCounter;
    TextView targetConsumption, consumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize global preferences
        Global.initialize(getApplicationContext());

        // Redirect the user to settings if the settings are invalid or unset
        tryRedirectToSettings();

        waterCounter = new Counter(Global.read("weight", 0), Global.read("age", 0));
        targetConsumption = findViewById(R.id.textView2);

        targetConsumption.setText(Double.toString(waterCounter.getTargetConsumption()) + "ml");

    }

    public void updateValue() {
        consumption = findViewById(R.id.textView);
        consumption.setText(Double.toString(waterCounter.getConsume()) + "ml");

        Global.write("consumption", (int) waterCounter.getConsume());
    }

    public void glass(View view) {
        waterCounter.consume(250);
        updateValue();
    }

    public void bottle(View view) {
        waterCounter.consume(500);
        updateValue();
    }

    public void jug(View view) {
        waterCounter.consume(1000);
        updateValue();
    }


    @Override
    protected void onResume() {
        super.onResume();
        tryRedirectToSettings();
        waterCounter.setConsumption(Global.read("consumption", 0));
        updateValue();
    }

    private void tryRedirectToSettings() {
        if (!Global.isValidAge(Global.read("age", -1)) || !Global.isValidWeight(Global.read("weight", -1)) || !Global.isValidGender(Global.read("gender", "invalid"))) {
            Intent newActivity = new Intent(this, SettingsActivity.class);
            startActivity(newActivity);
        }
    }
}