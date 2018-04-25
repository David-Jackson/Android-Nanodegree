package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.udacity.sandwichclub.handlers.BottomSheetHandler;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Switch detailSwitch;
    BottomSheetHandler bottomSheetHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sandwiches);

        detailSwitch = findViewById(R.id.switch_detail);
        detailSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setText(isChecked ?
                        R.string.detail_switch_on_text : R.string.detail_switch_off_text);
            }
        });

        bottomSheetHandler = new BottomSheetHandler(this);

        // Simplification: Using a ListView instead of a RecyclerView
        ListView listView = findViewById(R.id.sandwiches_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (detailSwitch.isChecked()) {
                    bottomSheetHandler.openBottomSheet(position);
                } else {
                    launchDetailActivity(position);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetHandler.isBottomSheetOpen()) {
            bottomSheetHandler.closeBottomSheet();
        } else {
            super.onBackPressed();
        }
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }
}
