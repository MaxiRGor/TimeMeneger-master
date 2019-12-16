package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;

public class Schedule extends AppCompatActivity implements View.OnClickListener{

    Button add_btn, clear_btn, delete_btn, update_btn, btn_id, btn_time, btn_event, updateDate_btn, done_btn, stat_btn;
    EditText timeText, minText, eventText, idText, dayText, monthText, yearText;

    TextView output;
    String out = "", dataDayString = "", dataMonthString = "", dataYearString = "", allDateString = "", str = "";
    String doneStringOutput = "0", text = "";
    double allEvent = 0, doneEvent = 0, total = 0;

    DBHelper dbHelper;
    DBAgreements dbAgreements;
    DBReports dbReports;
    DBTrigger dbTrigger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        add_btn = (Button)findViewById(R.id.add_btn);
        clear_btn = (Button)findViewById(R.id.clear_btn);
        delete_btn = (Button)findViewById(R.id.delete_btn);
        update_btn = (Button)findViewById(R.id.update_btn);
        btn_id = (Button)findViewById(R.id.btnid);
        btn_time = (Button)findViewById(R.id.btntime);
        btn_event = (Button)findViewById(R.id.btnevent);
        updateDate_btn = (Button)findViewById(R.id.updateDate);
        done_btn = (Button)findViewById(R.id.done_btn);
        stat_btn = (Button)findViewById(R.id.statsort);


        timeText = (EditText)findViewById(R.id.time);
        minText = (EditText)findViewById(R.id.minuts);
        idText = (EditText)findViewById(R.id.id);
        dayText = (EditText)findViewById(R.id.day);
        monthText = (EditText)findViewById(R.id.month);
        yearText = (EditText)findViewById(R.id.year);
        eventText = (EditText)findViewById(R.id.event);

        output = (TextView)findViewById(R.id.output);


        add_btn.setOnClickListener(this);
        clear_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        btn_id.setOnClickListener(this);
        btn_time.setOnClickListener(this);
        btn_event.setOnClickListener(this);
        updateDate_btn.setOnClickListener(this);
        done_btn.setOnClickListener(this);
        stat_btn.setOnClickListener(this);


        long date = System.currentTimeMillis();
        SimpleDateFormat dataDay = new SimpleDateFormat("dd");
        SimpleDateFormat dataMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dataYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        str = sdf.format(date);
        dataDayString = dataDay.format(date);
        dataMonthString = dataMonth.format(date);
        dataYearString = dataYear.format(date);
        dayText.setText(dataDayString);
        monthText.setText(dataMonthString);
        yearText.setText(dataYearString);
        allDateString = dataDayString+dataMonthString+dataYearString;


        dbHelper = new DBHelper(this);
        dbAgreements = new DBAgreements(this);
        dbReports = new DBReports(this);
        dbTrigger = new DBTrigger(this);





        String selection = DBHelper.KEY_DATE + "= ?";
        String [] selectionArgs = new String[] { allDateString  };
        String table = "schedule";
        String order = "time*100+min";


        output (table,null, selection, selectionArgs, order);

    }



    @Override
    public void onClick(View v) {

        String time = timeText.getText().toString();
        String min = minText.getText().toString();
        String event = eventText.getText().toString();
        String id = idText.getText().toString();
        dataDayString = dayText.getText().toString();
        dataMonthString = monthText.getText().toString();
        dataYearString = yearText.getText().toString();
        allDateString = dataDayString+dataMonthString+dataYearString;


        String selection = "date = ?";
        String [] selectionArgs = new String[] { allDateString  };


        String doneString = "0";
        doneStringOutput = "";
        String table = "schedule";
        String order = "year*10000+month*1000+day*100";


        table = "schedule";
        order = "time*100+min";

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        SQLiteDatabase databaseAgreements = dbAgreements.getWritableDatabase();
        SQLiteDatabase databaseReports = dbReports.getWritableDatabase();
        ContentValues contValOfDBAgreements = new ContentValues();
        ContentValues contentValuesOfDBReports = new ContentValues();
        SQLiteDatabase databaseTrigger = dbTrigger.getWritableDatabase();
        ContentValues contentTrigger = new ContentValues();


        ContentValues contentValues = new ContentValues();

        switch (v.getId()){

            case R.id.add_btn:
                String timeNull = "";
                String minNull = "";
                doneString = "0";

                if (time.equalsIgnoreCase("")){
                    text = "enter the time";
                    toast(text);
                    break;
                }
                if (min.equalsIgnoreCase("")){
                    text = "enter the time";
                    toast(text);
                    break;
                }
                if (event.equalsIgnoreCase("")){
                    text = "enter the event";
                    toast(text);
                    break;
                }
                if ((parseInt(time) > 24) || (parseInt(time) < 0)){
                    text = "Invalid time entry";
                    toast(text);
                    break;
                }
                if ((parseInt(min) > 59) || (parseInt(min) < 0)){
                    text = "Invalid time entry";
                    toast(text);
                    break;
                }
                if ((parseInt(time) < 10)){
                    timeNull = "0";
                }
                if ((parseInt(min) < 10)){
                    minNull = "0";
                }
                intent(allDateString, timeNull, minNull, time, min, event, doneString);

                selection = "date = ?";
                selectionArgs = new String[] { allDateString  };
                table = "schedule";
                order = "time*100+min";



                output (table,null, selection, selectionArgs, order);

                break;

            case R.id.btntime:

                output (table,null, selection, selectionArgs, order);

                text = "sorting";
                toast(text);

                break;

            case R.id.btnid:
                order = "_id";
                output (table,null, selection, selectionArgs, order);
                text = "sorting";
                toast(text);
                break;

            case R.id.btnevent:

                order = "event";

                output (table,null, selection, selectionArgs, order);

                text = "sorting";
                toast(text);
                break;

            case R.id.statsort:

                order = "done";

                output (table,null, selection, selectionArgs, order);


                Toast toas5 = Toast.makeText(Schedule.this,"sorting",Toast.LENGTH_LONG);
                toas5.show();
                break;

            case R.id.updateDate:

                output (table,null, selection, selectionArgs, order);

                Toast toas2 = Toast.makeText(Schedule.this,"date updated",Toast.LENGTH_LONG);
                toas2.show();
                break;

            case R.id.clear_btn:
                database.delete(DBHelper.TABLE_SCHEDULE,null,null);
                databaseReports.delete(DBReports.TABLE_REPORTS,null,null);
                out = "Data not found\n";
                output.setText(out);
                break;

            case R.id.update_btn:

                timeNull = "";
                minNull = "";

                if (time.equalsIgnoreCase("")){
                    text = "enter the time";
                    toast(text);
                    break;
                }
                if (min.equalsIgnoreCase("")){
                    text = "enter the time";
                    toast(text);
                    break;
                }
                if (event.equalsIgnoreCase("")){
                    text = "enter the event";
                    toast(text);
                    break;
                }
                if ((parseInt(time) > 24) || (parseInt(time) < 0)){
                    text = "Invalid time entry";
                    toast(text);
                    break;
                }
                if ((parseInt(min) > 59) || (parseInt(min) < 0)){
                    text = "Invalid time entry";
                    toast(text);
                    break;
                }

                contentValues.put(DBHelper.KEY_DATE, allDateString);
                contentValues.put(DBHelper.KEY_NULLTIME, timeNull);
                contentValues.put(DBHelper.KEY_NULLMIN, minNull);
                contentValues.put(DBHelper.KEY_TIME, time);
                contentValues.put(DBHelper.KEY_MIN, min);
                contentValues.put(DBHelper.KEY_EVENT, event);



                database.update(DBHelper.TABLE_SCHEDULE, contentValues,
                        DBHelper.KEY_ID + "= ?", new String[] { id });

                output (table,null, selection, selectionArgs, order);
                break;

            case R.id.delete_btn:
                if (id.equalsIgnoreCase("")){
                    break;
                }
                int delCount = database.delete(DBHelper.TABLE_SCHEDULE,
                        DBHelper.KEY_ID + "=" + id,null);

                output (table,null, selection, selectionArgs, order);
                break;

            case R.id.done_btn:
                if (id.equalsIgnoreCase("")){
                    break;
                }

                doneString = "1";

                contentValues.put(DBHelper.KEY_DONE, doneString);
                database.update(DBHelper.TABLE_SCHEDULE, contentValues, DBHelper.KEY_ID + "= ?", new String[] { id });
                databaseAgreements.delete(DBAgreements.TABLE_AGREEMENTS,null, null);
                databaseReports.delete(DBReports.TABLE_REPORTS,DBReports.KEY_ALLDATE + "= ?" , new String[] { allDateString });

                outputWithInsert(table, selection, selectionArgs, order);
                break;
        }

        databaseReports.delete(DBReports.TABLE_REPORTS,DBReports.KEY_ALLDATE + "= ?" , new String[] { allDateString });
        total = doneEvent / allEvent * 100;
        String dayNull = "";
        String monthNull = "";
        if ((parseInt(dataDayString) < 10)) {
            dayNull = "0";
        }
        if ((parseInt(dataMonthString) < 10)) {
            monthNull = "0";
        }
        String daySR    = DBReports.KEY_DAY;
        String dayNSR   = DBReports.KEY_NULLDAY;
        String monthSR  = DBReports.KEY_MONTH;
        String monthNSR = DBReports.KEY_NULLMONTH;
        String yearSR   = DBReports.KEY_YEAR;
        String totalSR  = DBReports.KEY_TOTAL;
        String allDate  = DBReports.KEY_ALLDATE;
        contentValuesOfDBReports.put(daySR    , dataDayString);
        contentValuesOfDBReports.put(dayNSR   , dayNull);
        contentValuesOfDBReports.put(monthSR  , dataMonthString);
        contentValuesOfDBReports.put(monthNSR , monthNull);
        contentValuesOfDBReports.put(yearSR   , dataYearString);
        contentValuesOfDBReports.put(totalSR  , total);
        contentValuesOfDBReports.put(allDate  , allDateString);

        databaseReports.insert(DBReports.TABLE_REPORTS,null, contentValuesOfDBReports);



        dbHelper.close();
        dbAgreements.close();
        dbReports.close();
        dbTrigger.close();


    }

    private void toast (String text){
        Toast toast = Toast.makeText(Schedule.this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    /*
    Для автосинхронизации поля Done в двух таблицах одновременно
    */
    private void outputWithInsert(String table, String select, String [] selectA, String order) {

        allEvent = 0; doneEvent = 0; total = 0;

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        SQLiteDatabase databaseAgreements = dbAgreements.getWritableDatabase();
        SQLiteDatabase databaseReports = dbReports.getWritableDatabase();
        ContentValues contValOfDBAgreements = new ContentValues();
        ContentValues contentValuesOfDBReports = new ContentValues();

        Cursor cursordone = database.query(table, null,
                select, selectA, null, null, order);

        if (cursordone.moveToFirst()){

            String idS = DBHelper.KEY_ID;
            String timeNS = DBHelper.KEY_NULLTIME;
            String minNS =DBHelper.KEY_NULLMIN;
            String timeS = DBHelper.KEY_TIME;
            String minS = DBHelper.KEY_MIN;
            String eventS = DBHelper.KEY_EVENT;
            String doneS = DBHelper.KEY_DONE;
            String relationS = DBHelper.KEY_RELATION;

            int idIndex = cursordone.getColumnIndex(idS);
            int timeNullIndex = cursordone.getColumnIndex(timeNS);
            int minNullIndex = cursordone.getColumnIndex(minNS);
            int timeIndex = cursordone.getColumnIndex(timeS);
            int minIndex = cursordone.getColumnIndex(minS);
            int eventIndex = cursordone.getColumnIndex(eventS);
            int doneIndex = cursordone.getColumnIndex(doneS);
            int relationI = cursordone.getColumnIndex(relationS);

            out = "";

            do {
                String id = cursordone.getString(idIndex);
                String timeN = cursordone.getString(timeNullIndex);
                String timeI = cursordone.getString(timeIndex);
                String minN = cursordone.getString(minNullIndex);
                String minI = cursordone.getString(minIndex);
                String eventI = cursordone.getString(eventIndex);
                String doneI = cursordone.getString(doneIndex);
                String doneN = "";

                if (parseInt(doneI) == 0) {
                    doneStringOutput = "DO     ";
                } else doneStringOutput = "DONE";
                if (cursordone.getInt(relationI) == 1) {
                    doneN = "    ";
                }

                out = out + id + "   " + timeN + timeI + ":" + minN +
                        minI + " " + doneN + doneStringOutput + "  " + eventI  + "\n";

                allEvent = allEvent + 1;

                if (parseInt(doneI) == 1){
                    doneEvent = doneEvent + 1;
                }

                if (cursordone.getInt(relationI) == 1) {
                    String dayNull = "";
                    String monthNull = "";
                    if ((parseInt(dataDayString) < 10)) {
                        dayNull = "0";
                    }
                    if ((parseInt(dataMonthString) < 10)) {
                        monthNull = "0";
                    }

                    String dayNS        = DBAgreements.KEY_NULLDAY;
                    String monthNS      = DBAgreements.KEY_NULLMONTH;
                    String dayS         = DBAgreements.KEY_DAY;
                    String monthS       = DBAgreements.KEY_MONTH;
                    String yearS        = DBAgreements.KEY_YEAR;
                    String agreementS   = DBAgreements.KEY_AGREEMENT;
                    String doneAgreemS  = DBAgreements.KEY_DONE;

                    contValOfDBAgreements.put(dayNS, dayNull);
                    contValOfDBAgreements.put(monthNS, monthNull);
                    contValOfDBAgreements.put(dayS, dataDayString);
                    contValOfDBAgreements.put(monthS, dataMonthString);
                    contValOfDBAgreements.put(yearS, dataYearString);
                    contValOfDBAgreements.put(agreementS, eventI);
                    contValOfDBAgreements.put(doneAgreemS, doneI);

                    databaseAgreements.insert(DBAgreements.TABLE_AGREEMENTS, null, contValOfDBAgreements);

                }
            }  while (cursordone.moveToNext()) ;
        } else
            out = "Data not found\n";
        output.setText(out);
        cursordone.close();
    }

    private void output (String table, String columns [], String select, String [] selectA, String order) {

        allEvent = 0; doneEvent = 0; total = 0;

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cur = database.query(table, columns, select, selectA, null, null, order);

        if (cur.moveToFirst()){

            String idS = DBHelper.KEY_ID;
            String timeNS = DBHelper.KEY_NULLTIME;
            String minNS =DBHelper.KEY_NULLMIN;
            String timeS = DBHelper.KEY_TIME;
            String minS = DBHelper.KEY_MIN;
            String eventS = DBHelper.KEY_EVENT;
            String doneS = DBHelper.KEY_DONE;
            String relationS = DBHelper.KEY_RELATION;
            String date = DBHelper.KEY_DATE;
            String doneN = "";

            int idIndex = cur.getColumnIndex(idS);
            int timeNullIndex = cur.getColumnIndex(timeNS);
            int minNullIndex = cur.getColumnIndex(minNS);
            int timeIndex = cur.getColumnIndex(timeS);
            int minIndex = cur.getColumnIndex(minS);
            int eventIndex = cur.getColumnIndex(eventS);
            int doneIndex = cur.getColumnIndex(doneS);
            int relationI = cur.getColumnIndex(relationS);
            int dateI = cur.getColumnIndex(date);

            out = "";

            do {

                String id = cur.getString(idIndex);
                String timeN = cur.getString(timeNullIndex);
                String timeI = cur.getString(timeIndex);
                String minN = cur.getString(minNullIndex);
                String minI = cur.getString(minIndex);
                String eventI = cur.getString(eventIndex);
                String doneI = cur.getString(doneIndex);
                String dateInd = cur.getString(dateI);
                doneN = "";

                allEvent = allEvent + 1;

                if (parseInt(doneI) == 1){
                    doneEvent = doneEvent + 1;
                }
                if ((parseInt(doneI) == 0)){
                    doneStringOutput = "DO     ";
                } else doneStringOutput = "DONE";

                out = out + id + " " + timeN + timeI + ":" + minN +
                        minI  + " " + doneStringOutput + "  " + eventI + "\n";
            } while (cur.moveToNext());

        } else

            out = "Data not found\n";

        output.setText(out);

        cur.close();
    }


    private void intent (String Date, String timeN, String minN, String time, String min,
                         String event, String doneS){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String dateS = DBHelper.KEY_DATE;
        String timeNS = DBHelper.KEY_NULLTIME;
        String minNS =DBHelper.KEY_NULLMIN;
        String timeS = DBHelper.KEY_TIME;
        String minS = DBHelper.KEY_MIN;
        String eventS = DBHelper.KEY_EVENT;
        String doneSS = DBHelper.KEY_DONE;

        contentValues.put(dateS, Date);
        contentValues.put(timeNS, timeN);
        contentValues.put(minNS, minN);
        contentValues.put(timeS, time);
        contentValues.put(minS, min);
        contentValues.put(eventS, event);
        contentValues.put(doneSS, doneS);

        database.insert(DBHelper.TABLE_SCHEDULE,null, contentValues);
    }



}
