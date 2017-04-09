package com.example.user.emloyeelogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class timetableForm2 extends AppCompatActivity
{
    String day;
    String month;
    String eid;

    int sTimeHour;
    int sTimeMinute;

    TextView header;
    TimePicker clock;
    Button submit;
    ProgressBar spinner;

    String sTime;
    String eTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_form2);

        day = getIntent().getExtras().getString("day");
        month = getIntent().getExtras().getString("month");
        eid = getIntent().getExtras().getString("eid");
        sTimeHour = getIntent().getExtras().getInt("sTimeHour");
        sTimeMinute = getIntent().getExtras().getInt("sTimeMinute");

        clock = (TimePicker)findViewById(R.id.etimeSelect);
        submit = (Button)findViewById(R.id.sbutton);
        header = (TextView)findViewById(R.id.eTimeHeader);
        spinner = (ProgressBar)findViewById(R.id.sProgressBar);

        if (sTimeMinute < 10)
        {
            header.setText("Pick End Time - Start Time is " + String.valueOf(sTimeHour) + ":0" + String.valueOf(sTimeMinute));
        }
        else
        {
            header.setText("Pick End Time - Start Time is " + String.valueOf(sTimeHour) + ":" + String.valueOf(sTimeMinute));
        }

        spinner.setVisibility(View.INVISIBLE);
        spinner.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        clock.setIs24HourView(true);
        clock.setCurrentHour(8);
        clock.setCurrentMinute(0);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (clock.getCurrentHour() <= sTimeHour)
                {
                    Toast.makeText(timetableForm2.this, "End Time Cannot be Greater than Start Time + 1 Hour", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    clock.setVisibility(View.INVISIBLE);
                    header.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.VISIBLE);

                    sTime = String.valueOf(sTimeHour)+":"+String.valueOf(sTimeMinute)+":00";
                    eTime = String.valueOf(clock.getCurrentHour())+":"+String.valueOf(clock.getCurrentMinute())+":00";

                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
                    {
                        @Override
                        public void processFinish(String output)
                        {
                            if (output.equals("true"))
                            {
                                spinner.setVisibility(View.INVISIBLE);

                                AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                                alertDialog.setTitle("Success!");
                                alertDialog.setMessage("Schedule for this Employee has been Added");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                        new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent2);
                                                finish();
                                            }
                                        });
                                alertDialog.show();
                            }
                            else if (output.equals("false"))
                            {
                                spinner.setVisibility(View.INVISIBLE);

                                AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                                alertDialog.setTitle("Failure");
                                alertDialog.setMessage("Something Went Wrong When Adding this Schedule, Please Try Again Later");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                        new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent2);
                                                finish();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                    });
                    backgroundTask.execute("writeEmployeeSchedule", eid, day, month, sTime, eTime);
                }
            }
        });
    }
}
