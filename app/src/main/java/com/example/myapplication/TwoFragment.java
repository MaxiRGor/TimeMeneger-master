package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TwoFragment extends Fragment {

    public static String elseDate;
    TextView inputTextView = null;
    View report_fragment_two = null;
    String outputStr = "", dataDayString = "", dataMonthString = "", dataYearString = "", allDateString = "";

    DBReports dbReports;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        report_fragment_two = inflater.inflate(R.layout.activity_fragment_two, container, false);
        inputTextView = (TextView) report_fragment_two.findViewById(R.id.output_act_frag_two);


        long date = System.currentTimeMillis();
        SimpleDateFormat dataDay = new SimpleDateFormat("dd");
        SimpleDateFormat dataMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dataYear = new SimpleDateFormat("yyyy");
        dataDayString = dataDay.format(date);
        dataMonthString = dataMonth.format(date);
        dataYearString = dataYear.format(date);
        allDateString = dataYearString + dataMonthString + dataDayString;


        List<Button> buttonList = getListOfButtons(report_fragment_two);

        View.OnClickListener shortClickListener = setOnShortClickListener();
        dbReports = new DBReports(getActivity());

        for (Button button : buttonList) {
            button.setOnClickListener(shortClickListener);
        }

        String table = "ReportsT";
        String order = "year*100+month*10+day";
        output(table, null, null, order);

        return report_fragment_two;
    }

    private View.OnClickListener setOnShortClickListener() {

        return new View.OnClickListener() {
            @Override
            final public void onClick(View view) {
                switch (view.getId()) {
                }
            }
        };
    }

    private List<Button> getListOfButtons(View view) {

        List<Button> buttons = new ArrayList<>();
        return buttons;
    }

    private void output(String table, String select, String[] selectA, String order) {

        SQLiteDatabase database = dbReports.getWritableDatabase();

        Cursor cur = database.query(table, null, select, selectA, null, null, order);

        if (cur.moveToFirst()) {

            String idS = DBReports.KEY_ID;
            String dayNS = DBReports.KEY_NULLDAY;
            String monthNS = DBReports.KEY_NULLMONTH;
            String dayS = DBReports.KEY_DAY;
            String monthS = DBReports.KEY_MONTH;
            String yearS = DBReports.KEY_YEAR;
            String totalS = DBReports.KEY_TOTAL;

            int idIndex = cur.getColumnIndex(idS);
            int dayNullIndex = cur.getColumnIndex(dayNS);
            int monthNullIndex = cur.getColumnIndex(monthNS);
            int dayIndex = cur.getColumnIndex(dayS);
            int monthIndex = cur.getColumnIndex(monthS);
            int yearIndex = cur.getColumnIndex(yearS);
            int totalIndex = cur.getColumnIndex(totalS);


            outputStr = "";

            do {

                String id = cur.getString(idIndex);
                String dayN = cur.getString(dayNullIndex);
                String dayI = cur.getString(dayIndex);
                String monthN = cur.getString(monthNullIndex);
                String monthI = cur.getString(monthIndex);
                String yearI = cur.getString(yearIndex);
                String totalI = cur.getString(totalIndex);

                if (totalI == null) {
                    totalI = "0";
                }

                elseDate = dayN + dayI + monthN + monthI + yearI;
                outputStr = outputStr + id + "  " + dayN + dayI + " " + monthN +
                        monthI + " " + yearI + " -- " + totalI + "%" + "\n";

            } while (cur.moveToNext());

        } else

            outputStr = "Data not found\n";

        inputTextView.setText(outputStr);

        cur.close();
    }


}
