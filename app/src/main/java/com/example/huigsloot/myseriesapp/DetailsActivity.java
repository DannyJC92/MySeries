package com.example.huigsloot.myseriesapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    private DataSource datasource;
    private Series series;
    private EditText newName, newCurEps, newTotEps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        datasource = new DataSource(this);
        final long seriesId = getIntent().getLongExtra(MainActivity.EXTRA_SERIES_ID, -1);
        series = datasource.getSerie(seriesId);

        TextView title = (TextView) findViewById(R.id.series_title);
        title.setText(series.getSeries().toString());

        newName = (EditText) findViewById(R.id.update_series);
        newName.setText(series.getSeries().toString());

        //newCurEps = (EditText) findViewById(R.id.update_current_episode);
        //newCurEps.setText(series.getSeries().toString());

        //newTotEps = (EditText) findViewById(R.id.update_total_episodes);
        //newTotEps.setText(series.getSeries().toString());

        Button updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!TextUtils.isEmpty(newName.getText())) {
                    series.setSeries(newName.getText().toString());
                    datasource.updateSeries(series);
                    Toast.makeText(DetailsActivity.this, "Series updated", Toast.LENGTH_SHORT).show();

                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(DetailsActivity.this, "Please enter some text in the title field", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
