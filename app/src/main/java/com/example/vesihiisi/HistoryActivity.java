package com.example.vesihiisi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryActivity extends NavigationBarActivity {

    public static final String DAY_DATA_DATE = "com.example.vesihiisi.DAY_DATA_DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ListView presidentListView = findViewById(R.id.listViewDayData);

        presidentListView.setAdapter(new ArrayAdapter<DayData>(
                this,
                android.R.layout.simple_list_item_1,
                Global.readDayDataList())
        );

        presidentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent dayDataDetails = new Intent(HistoryActivity.this, DayDataActivity.class);
                dayDataDetails.putExtra(DAY_DATA_DATE, ((TextView) view).getText());
                startActivity(dayDataDetails);
            }
        });
    }
}