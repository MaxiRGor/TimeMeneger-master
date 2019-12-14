package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBWishes extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WishesDB";
    public static final String TABLE_WISHES = "wishesT";

    public static final String KEY_ID = "_id";
    public static final String KEY_DAY = "day";
    public static final String KEY_MONTH = "month";
    public static final String KEY_NULLDAY = "nullday";
    public static final String KEY_NULLMONTH = "nullmonth";
    public static final String KEY_YEAR = "year";
    public static final String KEY_WISH = "wish";

    public DBWishes(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_WISHES + " (" + KEY_ID + " integer primary key,"
                + KEY_NULLDAY + " text," + KEY_DAY + " integer," + KEY_NULLMONTH + " text," + KEY_MONTH + " integer,"
                + KEY_YEAR + " integer," + KEY_WISH + " text" + ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_WISHES);
        onCreate(db);
    }
}

