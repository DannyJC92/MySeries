package com.example.huigsloot.myseriesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Huigsloot on 13-10-2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    // Database info
    private static final String DATABASE_NAME = "myseriesapp.db";
    private static final int DATABASE_VERSION = 1;

    // Table info
    public static final String TABLE_SERIES = "series";
    public static final String COLUMN_SERIES_ID = "series_id";
    public static final String COLUMN_SERIES_NAME = "series_name";
    public static final String COLUMN_SERIES_CURRENT_EPS = "series_current_eps";
    public static final String COLUMN_SERIES_TOTAL_EPS = "series_total_eps";

    // Creating the table
    private static final String DATABASE_CREATE_SERIES =
            "CREATE TABLE " + TABLE_SERIES +
            "(" +
                    COLUMN_SERIES_ID + " integer primary key autoincrement, " +
                    COLUMN_SERIES_NAME + " text not null, " +
                    COLUMN_SERIES_CURRENT_EPS + " integer, " +
                    COLUMN_SERIES_TOTAL_EPS + " integer" +
            ");";

    // Mandatory constructor which passes the context, database name and database version and passes it to the parent
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        // Execute the SQL to create the table
        database.execSQL(DATABASE_CREATE_SERIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion){
        /*
             * When the database gets upgraded you should handle the update to make sure there is no data loss.
             * This is the default code you put in the upgrade method, to delete the table and call the onCreate again.
             */
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion
        + " to version " + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERIES);

        onCreate(db);
    }
}
