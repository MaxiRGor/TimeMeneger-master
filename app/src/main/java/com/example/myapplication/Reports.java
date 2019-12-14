package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class Reports extends AppCompatActivity {


    EditText dayreport_eText, monthreport_eText, yearreport_eText;
    TextView output_act;
    String yearReport, monthReport, dayReport, text = "";
    String dayNull, monthNull;
    public static String totalDateReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);


        dayreport_eText = (EditText) findViewById(R.id.dayreport);
        monthreport_eText = (EditText) findViewById(R.id.monthreport);
        yearreport_eText = (EditText) findViewById(R.id.yearreport);
        output_act = (TextView) findViewById(R.id.output_act_frag_two);


        Fragment fragment = null;
        fragment = new TwoFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }


    public void Change(View view) {
        Fragment fragment = null;
        dayReport = dayreport_eText.getText().toString();
        monthReport = monthreport_eText.getText().toString();
        yearReport = yearreport_eText.getText().toString();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy MMM dd");
        long date = System.currentTimeMillis();
        String dataString = sdf2.format(date);
        totalDateReport = dataString;
        dayNull = "";
        monthNull = "";

        if (!dayReport.isEmpty() && (Integer.parseInt(dayReport) < 10) && dayReport.length() == 1) {
            dayNull = "0";
        }
        if (!monthReport.isEmpty() && (Integer.parseInt(monthReport) < 10) && monthReport.length() == 1) {
            monthNull = "0";
        }
        if (!dayReport.isEmpty() && !monthReport.isEmpty() && !yearReport.isEmpty()) {
            totalDateReport = dayNull + dayReport + monthNull + monthReport + yearReport;
        }
        switch (view.getId()) {
            case R.id.frag2:
                fragment = new TwoFragment();
                break;
            case R.id.frag3:
                fragment = new ThreeFragment();
                break;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }

    private void toast(String text) {
        Toast toast = Toast.makeText(Reports.this, text, Toast.LENGTH_LONG);
        toast.show();
    }


}
