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
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        datasource = new DataSource(this);
        final long seriesId = getIntent().getLongExtra(MainActivity.EXTRA_SERIES_ID, -1);
        series = datasource.getSerie(seriesId);

        String name = series.getSeries().toString();
        String cur = Integer.toString(series.getCurrentSeries());
        String tot = Integer.toString(series.getTotalSeries());

        // Showing the series name
        TextView title = (TextView) findViewById(R.id.series_title);
        title.setText(name);

        // Field for editing the series name
        newName = (EditText) findViewById(R.id.update_series);
        newName.setText(name);

        // Field for editing the progress watching the series
        newCurEps = (EditText) findViewById(R.id.update_current_episode);
        newCurEps.setText(cur);

        // Field for editing the total of episodes of the series
        newTotEps = (EditText) findViewById(R.id.update_total_episodes);
        newTotEps.setText(tot);

        // Button for adding 1 to current episodes
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

                    int cur = Integer.parseInt(newCurEps.getText().toString());
                    int tot = Integer.parseInt(newTotEps.getText().toString());

                    if (tot >= cur){

                    // Return the values from the fields to Strings
                    series.setSeries(newName.getText().toString());
                    series.setCurrentSeries(cur);
                    series.setTotalSeries(tot);

                    datasource.updateSeries(series);
                    Toast.makeText(DetailsActivity.this, R.string.series_updated, Toast.LENGTH_SHORT).show();

                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                    } else {
                        Toast.makeText(DetailsActivity.this, R.string.curEps_totEps, Toast.LENGTH_LONG).show();
                    }

                } else {
                    // In case the fields aren't filled, show a message
                    Toast.makeText(DetailsActivity.this, R.string.empty_field, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
