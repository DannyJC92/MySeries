package com.example.huigsloot.myseriesapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    private DataSource datasource;
    private Series series;
    private EditText newName, newCurEps, newTotEps;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        datasource = new DataSource(this);
        final long seriesId = getIntent().getLongExtra(MainActivity.EXTRA_SERIES_ID, -1);
        series = datasource.getSerie(seriesId);

        // Showing the series name
        TextView title = (TextView) findViewById(R.id.series_title);
        title.setText(series.getSeries().toString());

        // Editing the series name
        newName = (EditText) findViewById(R.id.update_series);
        newName.setText(series.getSeries().toString());

        // Editing the progress watching the series
        newCurEps = (EditText) findViewById(R.id.update_current_episode);
        newCurEps.setText(Integer.toString(series.getCurrentSeries()));

        // Editing the total of episodes of the series
        newTotEps = (EditText) findViewById(R.id.update_total_episodes);
        newTotEps.setText(Integer.toString(series.getTotalSeries()));

        // Add button for adding 1 to current episodes
        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @ Override
            public void onClick(View v){
                series.addOne(newCurEps);
            }
        });

        // Set the Progress Bar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(series.getCurrentSeries());
        progressBar.setMax(series.getTotalSeries());

        Button updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the fields are filled
                if (!TextUtils.isEmpty(newName.getText())
                        && !TextUtils.isEmpty(newCurEps.getText())
                        && !TextUtils.isEmpty(newTotEps.getText())) {

                    // Return the values from the fields to Strings
                    series.setSeries(newName.getText().toString());
                    series.setCurrentSeries(Integer.parseInt(newCurEps.getText().toString()));
                    series.setTotalSeries(Integer.parseInt(newTotEps.getText().toString()));

                    datasource.updateSeries(series);
                    Toast.makeText(DetailsActivity.this, "Series updated", Toast.LENGTH_SHORT).show();

                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    // In case the fields aren't filled, show a message
                    Toast.makeText(DetailsActivity.this, "Please enter some text in the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
