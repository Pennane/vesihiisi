package com.example.vesihiisi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

/**
 * NavigationActivity adds navigation bar to an activity
 */
public abstract class NavigationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void navigationClick(View view) {
        Intent newActivity;
        switch (view.getId()) {
            case R.id.navigationMain: {
                newActivity = new Intent(this, MainActivity.class);
                break;
            }
            case R.id.navigationHistory: {
                newActivity = new Intent(this, HistoryActivity.class);
                break;
            }
            case R.id.navigationTrophies: {
                newActivity = new Intent(this, TrophiesActivity.class);
                break;
            }
            case R.id.navigationSettings: {
                newActivity = new Intent(this, SettingsActivity.class);
                break;
            }
            default: {
                newActivity = new Intent(this, MainActivity.class);
            }
        }
        startActivity(newActivity);
    }

}
