package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private Button schedule, agreements, reports, wishes, admin;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView ttime = (TextView)findViewById(R.id.time);
                                TextView tdate = (TextView)findViewById(R.id.day);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy MMM dd");
                                String dataString = sdf2.format(date);
                                String timeString = sdf.format(date);
                                ttime.setText(timeString);
                                tdate.setText(dataString);
                            }
                        });
                    }
                } catch (InterruptedException e){

                }
            }
        }; t.start();
        setSchedule();

    }


    public void setSchedule() {
        schedule = (Button)findViewById(R.id.schedule);
        agreements = (Button)findViewById(R.id.agreements);
        reports = (Button)findViewById(R.id.reports);
        wishes = (Button)findViewById(R.id.wishes);
        admin = (Button)findViewById(R.id.admin);

        schedule.setOnClickListener(
                new View.OnClickListener() {
                    public void  onClick (View v){
                        Intent intent  = new Intent(MainActivity.this, Schedule.class);
                        startActivity(intent);
                    }
                }
        );
        agreements.setOnClickListener(
                new View.OnClickListener() {
                    public void  onClick (View v){
                        Intent intent  = new Intent(MainActivity.this, Agreements.class);
                        startActivity(intent);
                    }
                }
        );
        reports.setOnClickListener(
                new View.OnClickListener() {
                    public void  onClick (View v){
                        Intent intent  = new Intent(MainActivity.this, Reports.class);
                        startActivity(intent);
                    }
                }
        );
        wishes.setOnClickListener(
                new View.OnClickListener() {
                    public void  onClick (View v){
                        Intent intent  = new Intent(MainActivity.this, Wishes.class);
                        startActivity(intent);
                    }
                }
        );
        admin.setOnClickListener(
                new View.OnClickListener() {
                    public void  onClick (View v){
                        Intent intent  = new Intent(MainActivity.this, AccessActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }
}
