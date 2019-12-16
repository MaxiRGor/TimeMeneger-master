package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {



    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ScheduleDB";
    public static final String TABLE_SCHEDULE = "schedule";

    public static final String KEY_ID = "_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_MIN = "min";
    public static final String KEY_NULLTIME = "nulltime";
    public static final String KEY_NULLMIN = "nullmin";
    public static final String KEY_DATE = "date";
    public static final String KEY_DONE = "done";
    public static final String KEY_EVENT = "event";
    public static final String KEY_RELATION = "relation";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_SCHEDULE + " (" + KEY_ID + " integer primary key,"
                + KEY_DATE + " integer," + KEY_NULLTIME + " text,"  + KEY_TIME + " integer,"
                + KEY_NULLMIN + " text," + KEY_MIN + " integer," + KEY_DONE + " integer," +
                KEY_RELATION + " integer," + KEY_EVENT + " text" +  ") ");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_SCHEDULE);

        onCreate(db);
    }
}
