package com.example.thomas.tapin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

import java.util.Calendar;

/**
 * Created by Thomas on 21/04/2017.
 */

public class addTimetables extends AppCompatActivity
{
    private String cid;
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_date);

        setTitle("Schedules");

        cid = getIntent().getExtras().getString("cid");

        calendar = (CalendarView)findViewById(R.id.calendar);

        //Sunday will be the first day in the CalendarView
        calendar.setFirstDayOfWeek(1);

        //next view lines will only allow the Employer to add timetimes to employees for the current month and next 2 months but not past the current day
        Calendar firstDay = Calendar.getInstance();
        //get current day
        int day = firstDay.get(Calendar.DAY_OF_MONTH);
        //set first day allowed to be selected to be current day plus 1
        firstDay.set(firstDay.get(Calendar.YEAR), firstDay.get(Calendar.MONTH), day+1, 0, 0, 0);
        calendar.setMinDate(firstDay.getTimeInMillis());

        //do same for the last day of the month, set to max
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(lastDate.get(Calendar.YEAR), lastDate.get(Calendar.MONTH)+3, 0, 0, 0, 0);
        calendar.setMaxDate(lastDate.getTimeInMillis());

        calendar.setDate(firstDay.getTimeInMillis());

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, final int year, final int month, final int dayOfMonth)
            {
                final AlertDialog alertDialog = new AlertDialog.Builder(addTimetables.this).create();
                alertDialog.setTitle("Add Timetables");
                alertDialog.setMessage("For "+dayOfMonth+"/"+(month+1)+"/"+year+"?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent2 = new Intent(getApplicationContext(), employeeTimetable.class);
                                intent2.putExtra("cid", cid);
                                intent2.putExtra("day", String.valueOf(dayOfMonth));
                                intent2.putExtra("month", String.valueOf(month+1));
                                intent2.putExtra("year", String.valueOf(year));
                                startActivity(intent2);
                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                alertDialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });
    }
}
