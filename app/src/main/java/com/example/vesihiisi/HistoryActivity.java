package com.example.vesihiisi;

import static com.example.vesihiisi.Utilities.dateToFinnishLocaleString;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Activity that displays stored hydration history as a basic list view
 */
public class HistoryActivity extends NavigationBarActivity {

    public static final String DAY_DATA_DATE_STRING = "com.example.vesihiisi.DAY_DATA_DATE_STRING";
    public static final String DAY_DATA_DATE = "com.example.vesihiisi.DAY_DATA_DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView dayDataHistoryListView = findViewById(R.id.listViewDayData);
        dayDataHistoryListView.setAdapter(new ArrayAdapter<DayData>(
                this,
                R.layout.history_item_layout,
                Global.readDayDataList())
        );

        dayDataHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<DayData> adapter = (ArrayAdapter) adapterView.getAdapter();
                DayData dayData = adapter.getItem(i);
                Intent dayDataDetails = new Intent(HistoryActivity.this, DayDataActivity.class);
                dayDataDetails.putExtra(DAY_DATA_DATE_STRING, dateToFinnishLocaleString(dayData.getDate()));
                dayDataDetails.putExtra(DAY_DATA_DATE, dayData.getDate().toString());
                startActivity(dayDataDetails);
            }
        });
    }
}