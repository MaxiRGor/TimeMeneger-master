package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;


public class Agreements extends AppCompatActivity implements View.OnClickListener {

    Button bt_addagreem, bt_rewriteagreem, bt_deleteagreem, bt_clearagreem, bt_idsordagreem, bt_datesortagreem, bt_arrangementsortagreem, bt_done, bt_statsort;
    EditText day_eText, mount_eText, year_eText, agreement_eText, id_eText;
    TextView output_vText;

    String outputTextString = "", dataDayStartString = "", dataMonthStartString = "", dataYearStartString = "";
    String doneStringOutput = "0";

    DBAgreements dbAgreements;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreements);

        bt_addagreem = (Button) findViewById(R.id.add_agree);
        bt_rewriteagreem = (Button) findViewById(R.id.rewrite_aree);
        bt_deleteagreem = (Button) findViewById(R.id.delete_agree);
        bt_clearagreem = (Button) findViewById(R.id.clear_aree);
        bt_idsordagreem = (Button) findViewById(R.id.idsortagreem);
        bt_datesortagreem = (Button) findViewById(R.id.datesortagreem);
        bt_arrangementsortagreem = (Button) findViewById(R.id.arrangsortagreem);
        bt_done = (Button) findViewById(R.id.done_agree);
        bt_statsort = (Button) findViewById(R.id.statussortagreem);

        day_eText = (EditText) findViewById(R.id.dayText);
        mount_eText = (EditText) findViewById(R.id.mounthText);
        year_eText = (EditText) findViewById(R.id.yearText);
        agreement_eText = (EditText) findViewById(R.id.agreementText);
        id_eText = (EditText) findViewById(R.id.idText);

        output_vText = (TextView) findViewById(R.id.outputText);

        bt_addagreem.setOnClickListener(this);
        bt_rewriteagreem.setOnClickListener(this);
        bt_deleteagreem.setOnClickListener(this);
        bt_clearagreem.setOnClickListener(this);
        bt_idsordagreem.setOnClickListener(this);
        bt_datesortagreem.setOnClickListener(this);
        bt_arrangementsortagreem.setOnClickListener(this);
        bt_done.setOnClickListener(this);
        bt_statsort.setOnClickListener(this);

        long date = System.currentTimeMillis();
        SimpleDateFormat dataDay = new SimpleDateFormat("dd");
        SimpleDateFormat dataMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dataYear = new SimpleDateFormat("yyyy");
        dataDayStartString = dataDay.format(date);
        dataMonthStartString = dataMonth.format(date);
        dataYearStartString = dataYear.format(date);


        dbAgreements = new DBAgreements(this);
        SQLiteDatabase databaseAgreements = dbAgreements.getWritableDatabase();
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        //      String selection = "done == ?";
        //      String [] selectionArgs = new String[] { "1"  };
        String table = "AgreementsT";
        String order = "year*10000+month*1000+day*100";
        output(table, null, null, order);

    }

    public void onClick(View v) {

        String id = id_eText.getText().toString();
        String day = day_eText.getText().toString();
        String mount = mount_eText.getText().toString();
        String year = year_eText.getText().toString();
        String agreement = agreement_eText.getText().toString();

        String dayNull = "";
        String monthNull = "";
        String time = "00";
        String min = "00";
        String relation = "1";
        String done = "0";
        String text = "";
        String timeN = "";
        String minN = "";

        SQLiteDatabase databaseAgreements = dbAgreements.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.add_agree:
                dayNull = "";
                monthNull = "";
                done = "0";
                if (day.equalsIgnoreCase("")) {
                    text = "enter the date";
                    toast(text);
                    break;
                }
                if (mount.equalsIgnoreCase("")) {
                    text = "enter the date";
                    toast(text);
                    break;
                }
                if (year.equalsIgnoreCase("")) {
                    text = "enter the date";
                    toast(text);
                    break;
                }
                if (agreement.equalsIgnoreCase("")) {
                    text = "enter the agreement";
                    toast(text);
                    break;
                }
                if ((parseInt(day) > 31) || (parseInt(day) < 0)) {
                    text = "Invalid date entry";
                    toast(text);
                    break;
                }
                if ((parseInt(mount) > 12) || (parseInt(mount) < 0)) {
                    text = "Invalid date entry";
                    toast(text);
                    break;
                }
                if ((parseInt(year) > 2050) || (parseInt(year) < 2018)) {
                    text = "Invalid date entry";
                    toast(text);
                    break;
                }
//                if ((parseInt(day) < 10)){
//                    dayNull = "0";
//                }
//                if ((parseInt(mount) < 10)){
//                    monthNull = "0";
//                }

                String allDateString = dayNull + day + monthNull + mount + year;

                insertSchedule(allDateString, timeN, minN, time, min, agreement, relation, done);

                insertAgree(dayNull, monthNull, day, mount, year, agreement, done, "0", "0");

                String table = "AgreementsT";
                String order = "year*10000+month*1000+day*100";

                output(table, null, null, order);
                break;

            case R.id.clear_aree:
                databaseAgreements.delete(DBAgreements.TABLE_AGREEMENTS, null, null);
                database.delete(DBHelper.TABLE_SCHEDULE, DBHelper.KEY_RELATION + "= ?", new String[]{relation});
                outputTextString = "Data not found\n";
                output_vText.setText(outputTextString);
                break;

            case R.id.done_agree:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                done = "1";
                contentValues.put(DBAgreements.KEY_DONE, done);
                contentValues.put(DBAgreements.KEY_MONTHSTART, dataMonthStartString);
                contentValues.put(DBAgreements.KEY_DAYSTART, dataDayStartString);
                databaseAgreements.update(DBAgreements.TABLE_AGREEMENTS, contentValues,
                        DBAgreements.KEY_ID + "= ?", new String[]{id});


                database.delete(DBHelper.TABLE_SCHEDULE, DBHelper.KEY_RELATION + "= ?", new String[]{relation});

                table = "AgreementsT";
                order = "year*10000+month*1000+day*100";

                outputWithInsert(table, null, null, order);
                break;

            case R.id.idsortagreem:

                text = "sorting";
                toast(text);

                table = "AgreementsT";
                order = "_id";

                output(table, null, null, order);
                break;

            case R.id.statussortagreem:

                text = "sorting";
                toast(text);

                table = "AgreementsT";
                order = "done";

                output(table, null, null, order);
                break;

            case R.id.datesortagreem:


                text = "sorting";
                toast(text);

                table = "AgreementsT";
                order = "year*10000+month*1000+day*100";

                output(table, null, null, order);
                break;

            case R.id.arrangsortagreem:

                text = "sorting";
                toast(text);

                table = "AgreementsT";
                order = "agreement";

                output(table, null, null, order);
                break;

            case R.id.rewrite_aree:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                dayNull = "";
                monthNull = "";
                if (day.equalsIgnoreCase("")) {
                    text = "enter the date";
                    toast(text);
                    break;
                }
                if (mount.equalsIgnoreCase("")) {
                    text = "enter the date";
                    toast(text);
                    break;
                }
                if (year.equalsIgnoreCase("")) {
                    text = "enter the date";
                    toast(text);
                    break;
                }
                if (agreement.equalsIgnoreCase("")) {
                    text = "enter the agreement";
                    toast(text);
                    break;
                }
                if ((parseInt(day) > 31) || (parseInt(day) < 0)) {
                    text = "Invalid date entry";
                    toast(text);
                    break;
                }
                if ((parseInt(mount) > 12) || (parseInt(mount) < 0)) {
                    text = "Invalid date entry";
                    toast(text);
                    break;
                }
                if ((parseInt(year) > 2050) || (parseInt(year) < 2018)) {
                    text = "Invalid date entry";
                    toast(text);
                    break;
                }
                if ((parseInt(day) < 10)) {
                    dayNull = "0";
                }
                if ((parseInt(mount) < 10)) {
                    monthNull = "0";
                }

                String dayNS = DBAgreements.KEY_NULLDAY;
                String monthNS = DBAgreements.KEY_NULLMONTH;
                String dayS = DBAgreements.KEY_DAY;
                String monthS = DBAgreements.KEY_MONTH;
                String yearS = DBAgreements.KEY_YEAR;
                String agreementS = DBAgreements.KEY_AGREEMENT;

                contentValues.put(dayNS, dayNull);
                contentValues.put(monthNS, monthNull);
                contentValues.put(dayS, day);
                contentValues.put(monthS, mount);
                contentValues.put(yearS, year);
                contentValues.put(agreementS, agreement);

                databaseAgreements.update(DBAgreements.TABLE_AGREEMENTS, contentValues,
                        DBAgreements.KEY_ID + "= ?", new String[]{id});

                database.delete(DBHelper.TABLE_SCHEDULE, DBHelper.KEY_RELATION + "= ?", new String[]{relation});

                table = "AgreementsT";
                order = "year*10000+month*1000+day*100";

                outputWithInsert(table, null, null, order);
                break;

            case R.id.delete_agree:
                if (id.equalsIgnoreCase("")) {
                    break;
                }

                databaseAgreements.delete(DBAgreements.TABLE_AGREEMENTS,
                        DBAgreements.KEY_ID + "= ?", new String[]{id});

                database.delete(DBHelper.TABLE_SCHEDULE, DBHelper.KEY_RELATION + "= ?", new String[]{relation});

                table = "AgreementsT";
                order = "year*10000+month*1000+day*100";

                outputWithInsert(table, null, null, order);
                break;


        }
        dbAgreements.close();
        dbHelper.close();
    }

    private void toast(String text) {
        Toast.makeText(Agreements.this, text, Toast.LENGTH_LONG).show();
    }

    private void output(String table, String select, String[] selectA, String order) {

        SQLiteDatabase databaseAgreements = dbAgreements.getWritableDatabase();
        String group = "done";
        Cursor cur = databaseAgreements.query(table, null, select, selectA, null, null, order);

        if (cur.moveToFirst()) {

            String idS = DBAgreements.KEY_ID;
            String dayNS = DBAgreements.KEY_NULLDAY;
            String monthNS = DBAgreements.KEY_NULLMONTH;
            String dayS = DBAgreements.KEY_DAY;
            String monthS = DBAgreements.KEY_MONTH;
            String yearS = DBAgreements.KEY_YEAR;
            String agreementS = DBAgreements.KEY_AGREEMENT;
            String doneS = DBAgreements.KEY_DONE;
            String datst = DBAgreements.KEY_DAYSTART;
            String monst = DBAgreements.KEY_MONTHSTART;


            int idIndex = cur.getColumnIndex(idS);
            int dayNullIndex = cur.getColumnIndex(dayNS);
            int monthNullIndex = cur.getColumnIndex(monthNS);
            int dayIndex = cur.getColumnIndex(dayS);
            int monthIndex = cur.getColumnIndex(monthS);
            int yearIndex = cur.getColumnIndex(yearS);
            int agreementIndex = cur.getColumnIndex(agreementS);
            int doneIndex = cur.getColumnIndex(doneS);
            int daystInd = cur.getColumnIndex(datst);
            int monthstInd = cur.getColumnIndex(monst);


            outputTextString = "";

            do {

                String id = cur.getString(idIndex);
                String dayN = cur.getString(dayNullIndex);
                String dayI = cur.getString(dayIndex);
                String monthN = cur.getString(monthNullIndex);
                String monthI = cur.getString(monthIndex);
                String yearI = cur.getString(yearIndex);
                String agreementI = cur.getString(agreementIndex);
                String doneI = cur.getString(doneIndex);

                if ((parseInt(doneI) == 0)) {
                    doneStringOutput = "DO     ";
                } else doneStringOutput = "DONE";

                if ((parseInt(monthI) < 10) && !monthN.equals("0")) {
                    monthN = "0";
                }
                if ((parseInt(dayI) < 10) && !dayN.equals("0")) {
                    dayN = "0";
                }

                outputTextString = outputTextString + id + "  " + dayN + dayI + " " + monthN +
                        monthI + " " + yearI + " " + doneStringOutput + " " + agreementI + "\n";

            } while (cur.moveToNext());

        } else

            outputTextString = "Data not found\n";

        output_vText.setText(outputTextString);

        cur.close();
    }

    private void outputWithInsert(String table, String select, String[] selectA, String order) {

        SQLiteDatabase databaseAgreements = dbAgreements.getWritableDatabase();

        Cursor curdone = databaseAgreements.query(table, null,
                select, selectA, null, null, order);
        if (curdone.moveToFirst()) {

            String idS = DBAgreements.KEY_ID;
            String dayNS = DBAgreements.KEY_NULLDAY;
            String monthNS = DBAgreements.KEY_NULLMONTH;
            String dayS = DBAgreements.KEY_DAY;
            String monthS = DBAgreements.KEY_MONTH;
            String yearS = DBAgreements.KEY_YEAR;
            String agreementS = DBAgreements.KEY_AGREEMENT;
            String doneS = DBAgreements.KEY_DONE;

            int idIndex = curdone.getColumnIndex(idS);
            int dayNullIndex = curdone.getColumnIndex(dayNS);
            int monthNullIndex = curdone.getColumnIndex(monthNS);
            int dayIndex = curdone.getColumnIndex(dayS);
            int monthIndex = curdone.getColumnIndex(monthS);
            int yearIndex = curdone.getColumnIndex(yearS);
            int agreementIndex = curdone.getColumnIndex(agreementS);
            int doneIndex = curdone.getColumnIndex(doneS);

            outputTextString = "";
            do {

                String idSt = curdone.getString(idIndex);
                String dayN = curdone.getString(dayNullIndex);
                String dayI = curdone.getString(dayIndex);
                String monthN = curdone.getString(monthNullIndex);
                String monthI = curdone.getString(monthIndex);
                String yearI = curdone.getString(yearIndex);
                String agreementI = curdone.getString(agreementIndex);
                String doneI = curdone.getString(doneIndex);

                if ((parseInt(doneI) == 0)) {
                    doneStringOutput = "DO     ";
                } else doneStringOutput = "DONE";

                outputTextString = outputTextString + idSt + "  " + dayN + dayI + " " + monthN +
                        monthI + " " + yearI + " " + doneStringOutput + " " + agreementI + "\n";

                String allDateString = dayN + dayI + monthN + monthI + yearI;

                String time = "00";
                String min = "00";
                String relation = "1";
                String timeN = "";
                String minN = "";

                insertSchedule(allDateString, timeN, minN, time, min, agreementI, relation, doneI);

            } while (curdone.moveToNext());
        } else
            outputTextString = "Data not found\n";
        output_vText.setText(outputTextString);
        curdone.close();
    }

    private void insertSchedule(String Date, String timeN, String minN, String time, String min, String event, String relation, String doneS) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String dateS = DBHelper.KEY_DATE;
        String timeNS = DBHelper.KEY_NULLTIME;
        String minNS = DBHelper.KEY_NULLMIN;
        String timeS = DBHelper.KEY_TIME;
        String minS = DBHelper.KEY_MIN;
        String eventS = DBHelper.KEY_EVENT;
        String doneSS = DBHelper.KEY_DONE;
        String relationS = DBHelper.KEY_RELATION;

        contentValues.put(dateS, Date);
        contentValues.put(timeNS, timeN);
        contentValues.put(minNS, minN);
        contentValues.put(timeS, time);
        contentValues.put(minS, min);
        contentValues.put(eventS, event);
        contentValues.put(doneSS, doneS);
        contentValues.put(relationS, relation);
        database.insert(DBHelper.TABLE_SCHEDULE, null, contentValues);
    }

    private void insertAgree(String dayNull, String monthNull, String day, String mount,
                             String year, String agreement, String done, String ds, String ms) {
        SQLiteDatabase database = dbAgreements.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String dayNS = DBAgreements.KEY_NULLDAY;
        String monthNS = DBAgreements.KEY_NULLMONTH;
        String dayS = DBAgreements.KEY_DAY;
        String monthS = DBAgreements.KEY_MONTH;
        String yearS = DBAgreements.KEY_YEAR;
        String agreementS = DBAgreements.KEY_AGREEMENT;
        String doneS = DBAgreements.KEY_DONE;
        String datst = DBAgreements.KEY_DAYSTART;
        String monst = DBAgreements.KEY_MONTHSTART;


        contentValues.put(dayNS, dayNull);
        contentValues.put(monthNS, monthNull);
        contentValues.put(dayS, day);
        contentValues.put(monthS, mount);
        contentValues.put(yearS, year);
        contentValues.put(agreementS, agreement);
        contentValues.put(doneS, done);
        contentValues.put(datst, ds);
        contentValues.put(monst, ms);

        database.insert(DBAgreements.TABLE_AGREEMENTS, null, contentValues);
    }
}
