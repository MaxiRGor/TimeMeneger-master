package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.myapplication.TwoFragment.elseDate;
import static java.lang.Integer.parseInt;

public class ThreeFragment extends Fragment {

    DBHelper dbHelper;
    String outputStr = "", totalDateThreeFragment, doneStringOutput = "0";
    View report_fragment_three = null;
    TextView output, dettext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        report_fragment_three = inflater.inflate(R.layout.activity_fragment_three, container, false);

        output = (TextView) report_fragment_three.findViewById(R.id.outputreport);
        dettext = (TextView) report_fragment_three.findViewById(R.id.textdetail);

        dbHelper = new DBHelper(getActivity());

        totalDateThreeFragment = Reports.totalDateReport;

        if (totalDateThreeFragment.length() != 8) {
            totalDateThreeFragment = elseDate;
        }


        outputStr = "Detail of " +  totalDateThreeFragment.charAt(0) + totalDateThreeFragment.charAt(1) + " "
                + totalDateThreeFragment.charAt(2) + totalDateThreeFragment.charAt(3) + " "
                + totalDateThreeFragment.charAt(4) + totalDateThreeFragment.charAt(5)
                + totalDateThreeFragment.charAt(6) + totalDateThreeFragment.charAt(7);
        dettext.setText(outputStr);

        String selection = "date = ?";
        String[] selectionArgs = new String[]{totalDateThreeFragment};

        String table = "schedule";
        String order = "time*100+min";

        output(table, selection, selectionArgs, order);
        return report_fragment_three;
    }

    private void output (String table, String select, String[] selectA, String order) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cur = database.query(table, null, select, selectA, null, null, order);

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

            outputStr = "";

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

                if ((parseInt(doneI) == 0)){
                    doneStringOutput = "DO     ";
                } else doneStringOutput = "DONE";
                if (cur.getInt(relationI) == 1) {
                    doneN = "    ";
                }
                outputStr += id + " " + timeN + timeI + ":" + minN +
                        minI  + " " + doneStringOutput + "  " + eventI + "\n";
            } while (cur.moveToNext());

        } else

            outputStr = "Data not found\n";

        output.setText(outputStr);

        cur.close();
    }
}