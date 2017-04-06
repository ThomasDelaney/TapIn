package com.example.raulalvarez.tapintime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String currentDateTimeString = sdf.format(d);

        disptime = (TextView)findViewById(R.id.clock);
        disptime.setText(currentDateTimeString);


    }
}
