package com.example.vesihiisi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

/**
 * Activity with the purpose of showing details of one day of hydration history
 */
public class DayDataActivity extends NavigationBarActivity {
    TextView targetConsumptionTextView, consumptionTextView, dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_data);

        targetConsumptionTextView = findViewById(R.id.textViewDayDataTargetConsumption);
        consumptionTextView = findViewById(R.id.textViewDayDataConsumed);
        dateTextView = findViewById(R.id.textViewDayDataDate);

        Intent intent = getIntent();
        String date = intent.getStringExtra(HistoryActivity.DAY_DATA_DATE);
        DayData dayData = Global.readSpecificDayData(new Date(date));

        targetConsumptionTextView.setText(Integer.toString(dayData.getTargetConsumption()) + "ml");
        consumptionTextView.setText(Integer.toString(dayData.getConsumption()) + "ml");
        dateTextView.setText(dayData.getDate().toString());
    }
}