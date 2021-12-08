package com.example.vesihiisi;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends NavigationBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize global preferences
        Global.initialize(getApplicationContext());

        // Redirect the user to settings if the settings are invalid or unset
        tryRedirectToSettings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tryRedirectToSettings();
    }

    private void tryRedirectToSettings() {
        if (!Global.isValidAge(Global.read("age", -1)) || !Global.isValidWeight(Global.read("weight", -1)) || !Global.isValidGender(Global.read("gender", "invalid")) ) {
            Intent newActivity = new Intent(this, SettingsActivity.class);
            startActivity(newActivity);
        }
    }
}