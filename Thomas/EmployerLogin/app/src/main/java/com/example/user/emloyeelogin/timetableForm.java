package com.example.user.emloyeelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by Thomas on 09/04/2017.
 */

public class timetableForm extends AppCompatActivity
{
    String day;
    String month;
    String eid;

    TimePicker clock;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_timetable);

        day = getIntent().getExtras().getString("day");
        month = getIntent().getExtras().getString("month");
        eid = getIntent().getExtras().getString("eid");

        clock = (TimePicker)findViewById(R.id.timeSelect);
        next = (Button)findViewById(R.id.nbutton);

        clock.setIs24HourView(true);
        clock.setCurrentHour(8);
        clock.setCurrentMinute(0);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent2 = new Intent(getApplicationContext(), timetableForm2.class);
                intent2.putExtra("day", day);
                intent2.putExtra("month", month);
                intent2.putExtra("eid", eid);
                intent2.putExtra("sTimeHour", clock.getCurrentHour());
                intent2.putExtra("sTimeMinute", clock.getCurrentMinute());
                startActivity(intent2);
            }
        });
    }
}
