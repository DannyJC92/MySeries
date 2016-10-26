package com.example.huigsloot.myseriesapp;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Series> adapter;
    private DataSource datasource;
    public static final String EXTRA_SERIES_ID = "extraSeriesId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the list view
        listView = (ListView) findViewById(R.id.main_list);

        // Create the list
        datasource = new DataSource(this);
        List<Series> series = datasource.getAllSeries();

        // Create the adapter
        adapter = new ArrayAdapter<Series>(this, android.R.layout.simple_list_item_1, series);

        // Set the adapter to the list view
        listView.setAdapter(adapter);

        // Set the empty list view
        TextView emptyView = (TextView) findViewById(R.id.main_list_empty);
        listView.setEmptyView(emptyView);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(EXTRA_SERIES_ID, adapter.getItem(position).getId());
                startActivityForResult(intent, 2);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSeries.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                // Handle the data
                long seriesId = data.getLongExtra(EXTRA_SERIES_ID, -1);

                if(seriesId != -1){
                    Series serie = datasource.getSerie(seriesId);
                    adapter.add(serie);
                    updateSerieListView();
                }
            }
        }
        if(requestCode == 2){
            if (resultCode == RESULT_OK){
                updateSerieListView();
            }
        }
    }

    public void updateSerieListView(){
        List<Series> series = datasource.getAllSeries();
        adapter = new ArrayAdapter<Series>(this, android.R.layout.simple_list_item_1, series);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select the action");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == "Delete") {
            Toast.makeText(getApplicationContext(), "Series deleted", Toast.LENGTH_LONG).show();
            Series series = adapter.getItem(info.position);
            adapter.remove(series);
            datasource.deleteSeries(series);

            updateSerieListView();
        } else {
            return false;
        }
        return true;
    }
}
