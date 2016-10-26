package com.example.huigsloot.myseriesapp;

import android.content.Intent;

/**
 * Created by Huigsloot on 17-10-2016.
 */
public class Series {
    private long id;
    private String series;
    private int currentSeries;
    private int totalSeries;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getSeries(){
        return series;
    }

    public void setSeries(String series){
        this.series = series;
    }

    public int getCurrentSeries (){
        return currentSeries;
    }

    public void setCurrentSeries (int currentSeries){
        this.currentSeries = currentSeries;
    }

    public int getTotalSeries(){
        return totalSeries;
    }

    public void setTotalSeries(int totalSeries){
        this.totalSeries = totalSeries;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString(){
        return series;
    }

    /*@Override
    public int toInt(){
        return currentSeries;
    }*/
}
