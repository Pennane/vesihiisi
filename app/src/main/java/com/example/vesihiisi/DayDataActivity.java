package com.example.vesihiisi;

import static com.example.vesihiisi.Utilities.dateToFinnishLocaleString;
import static com.example.vesihiisi.Utilities.hideKeyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Activity with the purpose of showing details of one day of hydration history
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

    public void updateTextViews() {
        dayData = Global.readSpecificDayData(date);
        Log.d("test", "Consumption:" + Integer.toString(dayData.getConsumption()));
        targetConsumptionTextView.setText(Integer.toString(dayData.getTargetConsumption()) + "ml");
        consumptionTextView.setText(Integer.toString(dayData.getConsumption()) + "ml");
        dateTextView.setText(dateToFinnishLocaleString(dayData.getDate()));
    }

    public void onUpdateClick(View view) {
        hideKeyboard(this);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        if (updateConsumptionEditText.getText().toString().equals("")) {
            // Show a INVALID toast message
            CharSequence text = "Arvon on oltava 0-5000";
            Toast.makeText(context, text, duration).show();
            return;
        }

        int value = Integer.parseInt(updateConsumptionEditText.getText().toString());
        if (value < 0 || value > 5000) {
            // Show a INVALID toast message
            CharSequence text = "Arvon on oltava 0-5000";
            Toast.makeText(context, text, duration).show();
            return;
        }

        dayData.setConsumption(value);
        Global.writeSpecificDayData(dayData, dayData.getDate());

        // Show a VALID toast message
        CharSequence text = "Arvo tallennettu";
        Toast.makeText(context, text, duration).show();
        updateTextViews();
    }
}