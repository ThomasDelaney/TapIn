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

/**
 * Created by raulalvarez on 3/4/17.
 */



public class ClockIn extends AppCompatActivity {

    TextView disptime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateTimeString = sdf.format(d);

        disptime = (TextView)findViewById(R.id.clock);
        disptime.setText(currentDateTimeString);

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        String method = "ClockIn";
        String employee;
        String day = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
        String month = String.valueOf(cal.get(Calendar.MONTH));

        BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
        {
            @Override
            public void processFinish(String output)
            {

                if (output.equals("null "))
                {
                    Toast.makeText(ClockIn.this, "Employee Does Not Exists", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Intent myIntent = new Intent(getApplicationContext(), ClockIn.class);
                    startActivity(myIntent);

                }
            }
        });

        backgroundTask.execute(method, day, month);


    }
}
