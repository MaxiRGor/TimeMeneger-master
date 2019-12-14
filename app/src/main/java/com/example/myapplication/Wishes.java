package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Wishes extends AppCompatActivity implements View.OnClickListener, DeleteItemFromDatabase{

    Button addWish, clearWish;
    EditText wishWish;
    String outputTextString = "", dataDayString = "", dataMonthString = "", dataYearString = "", allDateString = "";
    WishesRecyclerAdapter wishesRecyclerAdapter;
    RecyclerView recyclerView;

    DBWishes dbWishes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishes);

        addWish = (Button)findViewById(R.id.wish_add);
        wishWish = (EditText)findViewById(R.id.wishwish);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        wishesRecyclerAdapter = new WishesRecyclerAdapter(this, new ArrayList<Wish>());

        recyclerView.setAdapter(wishesRecyclerAdapter);

        addWish.setOnClickListener(this);

        dbWishes = new DBWishes(this);

        String table = "wishesT";
        String order = "year*100+month*10+day";
        outputTextString = "Data not found\n";

        output (table, order, true);
    }



    private void addWish(Wish wish){
        wishesRecyclerAdapter.addWish(wish);
    }


    public void onClick(View v) {
        String dayNull = "";
        String monthNull = "";
        String wishes = wishWish.getText().toString();

        SQLiteDatabase databaseWishes = dbWishes.getWritableDatabase();

        long date = System.currentTimeMillis();
        SimpleDateFormat dataDay = new SimpleDateFormat("dd");
        SimpleDateFormat dataMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dataYear = new SimpleDateFormat("yyyy");
        dataDayString = dataDay.format(date);
        dataMonthString = dataMonth.format(date);
        dataYearString = dataYear.format(date);
        allDateString = dataDayString+dataMonthString+dataYearString;

        switch (v.getId()) {

            case R.id.wish_add:
                dayNull = "";
                monthNull = "";


                insertWishes(dayNull, monthNull, dataDayString, dataMonthString, dataYearString, wishes);
                String table = "wishesT";
                String order = "year*100+month*10+day";
                output (table,  order, false);
                break;

        }
        dbWishes.close();
    }



    private void output (String table,  String order, boolean isInit) {

        SQLiteDatabase databaseWishes = dbWishes.getWritableDatabase();

        Cursor cur = databaseWishes.query(table, null, null, null, null, null, order);

        if (cur.moveToFirst()){

            String idS = DBWishes.KEY_ID;
            String dayNS = DBWishes.KEY_NULLDAY;
            String monthNS =DBWishes.KEY_NULLMONTH;
            String dayS = DBWishes.KEY_DAY;
            String monthS = DBWishes.KEY_MONTH;
            String yearS = DBWishes.KEY_YEAR;
            String agreementS = DBWishes.KEY_WISH;

            int idIndex = cur.getColumnIndex(idS);
            int dayNullIndex = cur.getColumnIndex(dayNS);
            int monthNullIndex = cur.getColumnIndex(monthNS);
            int dayIndex = cur.getColumnIndex(dayS);
            int monthIndex = cur.getColumnIndex(monthS);
            int yearIndex = cur.getColumnIndex(yearS);
            int agreementIndex = cur.getColumnIndex(agreementS);

            outputTextString = "";

            Wish wish;
            do {
                String id = cur.getString(idIndex);
                String dayN = cur.getString(dayNullIndex);
                String dayI = cur.getString(dayIndex);
                String monthN = cur.getString(monthNullIndex);
                String monthI = cur.getString(monthIndex);
                String yearI = cur.getString(yearIndex);
                String agreementI = cur.getString(agreementIndex);
                wish = new Wish(id, dayN, dayI, monthN, monthI, yearI, agreementI);
                if (isInit) {
                    addWish(wish);
                }

                outputTextString = outputTextString  + id + "  " + dayN + dayI + " " + monthN +
                        monthI + " " + yearI  + " " + agreementI  + "\n";

            } while (cur.moveToNext());
            if (!isInit) {
                addWish(wish);
            }

        } else

            outputTextString = "Data not found\n";

        cur.close();
    }

    private void insertWishes (String dayNull, String monthNull, String day, String mount, String year, String wishes){
        SQLiteDatabase database = dbWishes.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String dayNS = DBWishes.KEY_NULLDAY;
        String monthNS =DBWishes.KEY_NULLMONTH;
        String dayS = DBWishes.KEY_DAY;
        String monthS = DBWishes.KEY_MONTH;
        String yearS = DBWishes.KEY_YEAR;
        String wishesS = DBWishes.KEY_WISH;

        contentValues.put(dayNS, dayNull);
        contentValues.put(monthNS, monthNull);
        contentValues.put(dayS, day);
        contentValues.put(monthS, mount);
        contentValues.put(yearS, year);
        contentValues.put(wishesS, wishes);

        database.insert(DBWishes.TABLE_WISHES,null, contentValues);
    }

    @Override
    public void delete(String id) {
        SQLiteDatabase database = dbWishes.getWritableDatabase();
        database.delete(DBWishes.TABLE_WISHES, DBWishes.KEY_ID + "= ?", new String[]{id});
    }
}
