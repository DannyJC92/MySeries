package com.example.huigsloot.myseriesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huigsloot on 13-10-2016.
 */
public class DataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] seriesAllColumns =
            {
                    MySQLiteHelper.COLUMN_SERIES_ID,
                    MySQLiteHelper.COLUMN_SERIES_NAME,
                    MySQLiteHelper.COLUMN_SERIES_CURRENT_EPS,
                    MySQLiteHelper.COLUMN_SERIES_TOTAL_EPS
            };

    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    // Opens the database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Closes the database
    public void close() {
        dbHelper.close();
    }

    public long createSeries(String series, int curEps, int totEps) {
        // If the database is not yet open, open it
        if (!database.isOpen()) {
            open();
        }

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_SERIES_NAME, series);
        values.put(MySQLiteHelper.COLUMN_SERIES_CURRENT_EPS, curEps);
        values.put(MySQLiteHelper.COLUMN_SERIES_TOTAL_EPS, totEps);
        long insertId = database.insert(MySQLiteHelper.TABLE_SERIES, null, values);

        if (database.isOpen())
            close();

        return insertId;
    }

    public void deleteSeries(Series series) {
        if (!database.isOpen()) {
            open();
        }

        database.delete(MySQLiteHelper.TABLE_SERIES, MySQLiteHelper.COLUMN_SERIES_ID +
                " =?", new String[]{
                Long.toString(series.getId())});

        if (database.isOpen())
            close();
    }

    public void updateSeries(Series series) {
        if (!database.isOpen()) {
            open();
        }

        ContentValues args = new ContentValues();
        args.put(MySQLiteHelper.COLUMN_SERIES_NAME, series.getSeries());
        args.put(MySQLiteHelper.COLUMN_SERIES_CURRENT_EPS, series.getCurrentSeries());
        args.put(MySQLiteHelper.COLUMN_SERIES_TOTAL_EPS, series.getTotalSeries());
        database.update(MySQLiteHelper.TABLE_SERIES, args, MySQLiteHelper.COLUMN_SERIES_ID +
                "=?", new String[]{
                Long.toString(series.getId())});

        if (database.isOpen())
            close();
    }

    public List<Series> getAllSeries() {
        if (!database.isOpen())
            open();

        List<Series> series = new ArrayList<Series>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SERIES, seriesAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Series serie = cursorToSeries(cursor);
            series.add(serie);
            cursor.moveToNext();
        }

        // Remember to close the cursor
        cursor.close();

        if (database.isOpen())
            close();

        return series;
    }

    private Series cursorToSeries(Cursor cursor) {
        try {
            Series serie = new Series();
            serie.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_SERIES_ID)));
            serie.setSeries(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_SERIES_NAME)));
            serie.setCurrentSeries(cursor.getInt(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_SERIES_CURRENT_EPS)));
            serie.setTotalSeries(cursor.getInt(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_SERIES_TOTAL_EPS)));
            return serie;
        } catch (CursorIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Series getSerie(long columnId) {
        if (!database.isOpen())
            open();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SERIES, seriesAllColumns,
                MySQLiteHelper.COLUMN_SERIES_ID + "=?", new String[]{
                        Long.toString(columnId)}, null, null, null);

        cursor.moveToFirst();
        Series serie = cursorToSeries(cursor);
        cursor.close();

        if (database.isOpen())
            close();

        return serie;
    }
}
