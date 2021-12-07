package com.example.vesihiisi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * An abstract class that adds the custom navigation bar to an activity.
 * "navigation_bar.xml" also needs to be manually included into the
 * activity layout for the navigation bar to appear successfully.
 */
public abstract class NavigationBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Hides the navigation bar from current activity.
     * The space taken by the bar remains.
     */
    public void hideNavigationBar() {
        View view = findViewById(R.id.navigationBar);
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Shows the navigation bar in current activity if hidden before
     */
    public void showNavigationBar() {
        View view = findViewById(R.id.navigationBar);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Handle navigation link clicks.
     * The views must have correct navigation bar id's.
     * @param view
     */
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
