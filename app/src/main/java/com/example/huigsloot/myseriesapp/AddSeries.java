package com.example.huigsloot.myseriesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddSeries extends AppCompatActivity {

    private EditText newSeries, curEps, totEps;
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newSeries = (EditText) findViewById(R.id.new_series);
        curEps = (EditText) findViewById(R.id.new_current_episode);
        totEps = (EditText) findViewById(R.id.new_total_episodes);
        datasource = new DataSource(this);

        //TODO Current Season + Total Seasons

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if some data is entered
                if (!TextUtils.isEmpty(newSeries.getText())
                        && !TextUtils.isEmpty(curEps.getText())
                        && !TextUtils.isEmpty(totEps.getText())
                        ) {

                    long seriesId = datasource.createSeries(newSeries.getText().toString(),
                            Integer.parseInt(curEps.getText().toString()),
                            Integer.parseInt(totEps.getText().toString())
                            );

                    Intent resultIntent = new Intent();

                    // Send the results back to the MainActivity
                    resultIntent.putExtra(MainActivity.EXTRA_SERIES_ID, seriesId);
                    setResult(Activity.RESULT_OK, resultIntent);

                    // Finish the current activity
                    finish();

                } else {
                    Toast.makeText(AddSeries.this, "Please enter some text in the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
