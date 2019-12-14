package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBTrigger extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TriggerDB";
    public static final String TABLE_TRIGGER = "TriggerT";

    public static final String KEY_ID = "_id";
    public static final String KEY_FIO = "fio";

    public DBTrigger(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_TRIGGER + " (" + KEY_ID + " integer primary key,"
                + KEY_FIO + " text" + ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TRIGGER);
        onCreate(db);
    }
}
