package com.example.user.emloyeelogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

import java.util.Calendar;

/**
 * Created by Thomas on 07/04/2017.
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

        cid = getIntent().getExtras().getString("cid");

        calendar = (CalendarView)findViewById(R.id.calendar);

        //Sunday will be the first day in the CalendarView
        calendar.setFirstDayOfWeek(1);

        //next view lines will only allow the Employer to add timetimes to employees for the current month
        //get first date of the month and set it to the minimum month available for selection in the CalendarView
        Calendar firstDate = Calendar.getInstance();
        firstDate.set(firstDate.get(Calendar.YEAR), firstDate.get(Calendar.MONTH), 1, 0, 0, 0);
        calendar.setMinDate(firstDate.getTimeInMillis());

        //do same for the last day of the month, set to max
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(lastDate.get(Calendar.YEAR), lastDate.get(Calendar.MONTH)+1, 0, 0, 0, 0);
        calendar.setMaxDate(lastDate.getTimeInMillis());

        calendar.setDate(firstDate.getTimeInMillis());

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, final int month, final int dayOfMonth)
            {
                final AlertDialog alertDialog = new AlertDialog.Builder(addTimetables.this).create();
                alertDialog.setTitle("Add Timetables");
                alertDialog.setMessage("For "+dayOfMonth+"/"+month+"/"+year+"?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent2 = new Intent(getApplicationContext(), employeeTimetable.class);
                                intent2.putExtra("cid", cid);
                                intent2.putExtra("day", String.valueOf(dayOfMonth));
                                intent2.putExtra("month", String.valueOf(month));

                                System.out.println(String.valueOf(dayOfMonth)+" "+String.valueOf(month)+" "+cid);
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
