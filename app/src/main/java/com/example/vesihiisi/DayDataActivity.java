package com.example.vesihiisi;

import static com.example.vesihiisi.DayData.maxConsumption;
import static com.example.vesihiisi.DayData.minConsumption;
import static com.example.vesihiisi.Utilities.dateToFinnishLocaleString;
import static com.example.vesihiisi.Utilities.hideKeyboard;
import static com.example.vesihiisi.Utilities.safeParseInteger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Activity with the purpose of showing details of one day of hydration history data
 *
 * @author Arttu Pennanen
 */
public class DayDataActivity extends NavigationBarActivity {
    TextView targetConsumptionTextView, consumptionTextView, dateTextView;
    EditText updateConsumptionEditText;
    DayData dayData;
    String dateString;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_data);

        updateConsumptionEditText = findViewById(R.id.editTextUpdateConsumption);
        targetConsumptionTextView = findViewById(R.id.textViewDayDataTargetConsumption);
        consumptionTextView = findViewById(R.id.textViewDayDataConsumed);
        dateTextView = findViewById(R.id.textViewDayDataDate);

        Intent intent = getIntent();
        dateString = intent.getStringExtra(HistoryActivity.DAY_DATA_DATE_STRING);
        date = new Date(intent.getStringExtra(HistoryActivity.DAY_DATA_DATE));

        updateTextViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTextViews();
    }

    /**
     * Set current dayData values on screen
     */
    public void updateTextViews() {
        dayData = Global.readSpecificDayData(date);
        targetConsumptionTextView.setText(getString(R.string.dynamic_ml, dayData.getTargetConsumption()));
        consumptionTextView.setText(getString(R.string.dynamic_ml, dayData.getConsumption()));
        dateTextView.setText(dateToFinnishLocaleString(dayData.getDate()));
    }

    /**
     * Validate and update new consumption value to the dayData
     *
     * @param view of which the event comes from
     */
    public void onUpdateClick(View view) {
        hideKeyboard(this);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;


        // If editText is empty, return early
        if (updateConsumptionEditText.getText().toString().equals("")) {
            // Show a INVALID toast message
            Toast.makeText(context, R.string.invalid_consumption, duration).show();
            return;
        }

        Integer value = safeParseInteger(updateConsumptionEditText.getText().toString());
        // If value is invalid, return early
        if (value == null || value < minConsumption || value > maxConsumption) {
            // Show a INVALID toast message
            Toast.makeText(context, R.string.invalid_consumption, duration).show();
            return;
        }

        dayData.setConsumption(value);

        // Write to global storage
        Global.writeSpecificDayData(dayData, dayData.getDate());

        // Show a VALID toast message
        Toast.makeText(context, R.string.value_stored, duration).show();
        updateTextViews();
    }
}