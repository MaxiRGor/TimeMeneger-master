package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBPassword extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AdminDB";
    public static final String TABLE_ADMIN = "adminT";

    public static final String KEY_ID = "_id";
    public static final String KEY_PASSWORD = "password";

    public DBPassword(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_ADMIN + " (" + KEY_ID + " integer primary key,"
                 + KEY_PASSWORD + " text" + ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ADMIN);
        onCreate(db);
    }
}
