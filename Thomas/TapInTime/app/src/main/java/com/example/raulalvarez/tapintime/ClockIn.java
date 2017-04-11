package com.example.raulalvarez.tapintime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by raulalvarez on 3/4/17.
 */


public class ClockIn extends AppCompatActivity {

    TextView disptime;
    TextView working;

    String yeeid = MainActivity.employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateTimeString = sdf.format(d);

        disptime = (TextView)findViewById(R.id.clock);
        working = (TextView)findViewById(R.id.working);
        disptime.setText(currentDateTimeString);

        Calendar cal = new GregorianCalendar(Locale.ENGLISH);
        cal.setTime(d);

        String method = "ClockIn";
        String day = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
        String month = String.valueOf(cal.get(Calendar.MONTH));

        BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
        {
            @Override
            public void processFinish(String output)
            {

                if (output.equals("null "))
                {
                    working.setText("You are not working today");
                }
                else
                {
                    working.setText("You are working today");
                }
            }
        });


        backgroundTask.execute(method, day, month, yeeid);
        System.out.println(method);
        System.out.println(day);
        System.out.println(month);
        System.out.println(yeeid);


    }
}
