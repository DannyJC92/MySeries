package com.example.huigsloot.myseriesapp;

import android.content.Intent;
import android.widget.EditText;

/**
 * Created by Huigsloot on 17-10-2016.
 */
public class Series {
    private long id;
    private String series;
    private int currentSeries;
    private int totalSeries;

    // Getters
    public long getId(){
        return id;
    }

    public String getSeries(){
        return series;
    }

    public int getCurrentSeries (){
        return currentSeries;
    }

    public int getTotalSeries(){
        return totalSeries;
    }

    // Setters
    public void setId(long id){
        this.id = id;
    }

    public void setSeries(String series){
        this.series = series;
    }

    public void setCurrentSeries (int currentSeries){
        this.currentSeries = currentSeries;
    }

    public void setTotalSeries(int totalSeries){
        this.totalSeries = totalSeries;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString(){
        return series;
    }

    public void addOne(EditText et){
        // Get the integer from the EditText
        int i = Integer.parseInt(et.getText().toString());

        // Set the integer back to the EditText
        i++;
        et.setText(Integer.toString(i));
    }
}
