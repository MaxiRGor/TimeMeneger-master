package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBAgreements extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AgreementsDB";
    public static final String TABLE_AGREEMENTS = "AgreementsT";

    public static final String KEY_ID = "_id";
    public static final String KEY_DAY = "day";
    public static final String KEY_MONTH = "month";
    public static final String KEY_NULLDAY = "nullday";
    public static final String KEY_NULLMONTH = "nullmonth";
    public static final String KEY_YEAR = "year";
    public static final String KEY_DONE = "done";
    public static final String KEY_MONTHSTART = "monthstart";
    public static final String KEY_DAYSTART = "daystart";
    public static final String KEY_AGREEMENT = "agreement";

    public DBAgreements(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_AGREEMENTS + " (" + KEY_ID + " integer primary key,"
                + KEY_NULLDAY + " text," + KEY_DAY + " integer," + KEY_NULLMONTH + " text,"
                + KEY_MONTH + " integer," + KEY_MONTHSTART + " integer," + KEY_DAYSTART + " integer,"
                + KEY_YEAR + " integer," + KEY_DONE + " integer," + KEY_AGREEMENT + " text" + ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_AGREEMENTS);
        onCreate(db);
    }



}
