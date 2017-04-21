package com.example.thomas.tapin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by Thomas on 21/04/2017.
 */

public class timetableForm2 extends AppCompatActivity
{
    String day;
    String month;
    String eid;
    String name;

    int sTimeHour;
    int sTimeMinute;

    TextView header;
    TimePicker clock;
    Button submit;
    Button dButton;
    Button cButton;
    ProgressBar spinner;

    String sTime;
    String eTime;

    String cEndTime;

    int type;

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
        type = getIntent().getExtras().getInt("type");
        name = getIntent().getExtras().getString("name");

        setTitle("Schedule For: "+name);

        if (type == 1)
        {
            cEndTime = getIntent().getExtras().getString("etime");
        }

        clock = (TimePicker)findViewById(R.id.etimeSelect);
        submit = (Button)findViewById(R.id.sbutton);
        dButton = (Button)findViewById(R.id.dButton);
        cButton = (Button)findViewById(R.id.confirmButton);
        header = (TextView)findViewById(R.id.eTimeHeader);
        spinner = (ProgressBar)findViewById(R.id.sProgressBar);

        submit.setVisibility(View.INVISIBLE);
        dButton.setVisibility(View.INVISIBLE);
        cButton.setVisibility(View.INVISIBLE);

        spinner.setVisibility(View.INVISIBLE);
        spinner.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        clock.setIs24HourView(true);

        if (Build.VERSION.SDK_INT >= 23)
        {
            clock.setHour(9);
            clock.setMinute(0);
        }
        else
        {
            clock.setCurrentHour(9);
            clock.setCurrentMinute(0);
        }

        if (type == 0)
        {
            submit.setVisibility(View.VISIBLE);

            if (sTimeMinute < 10)
            {
                header.setText("Pick End Time - Start Time is " + String.valueOf(sTimeHour) + ":0" + String.valueOf(sTimeMinute));
            }
            else
            {
                header.setText("Pick End Time - Start Time is " + String.valueOf(sTimeHour) + ":" + String.valueOf(sTimeMinute));
            }

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    final AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                    alertDialog.setTitle("Add Schedule");
                    alertDialog.setMessage("Are You Sure You Want to Confirm this Schedule?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    int currentHour;
                                    int currentMinute;

                                    if (Build.VERSION.SDK_INT >= 23) {
                                        currentHour = clock.getHour();
                                        currentMinute = clock.getMinute();
                                    } else {
                                        currentHour = clock.getCurrentHour();
                                        currentMinute = clock.getCurrentMinute();
                                    }

                                    if (currentHour <= sTimeHour)
                                    {
                                        Toast.makeText(timetableForm2.this, "End Time Cannot be Less than Start Time + 1 Hour", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        clock.setVisibility(View.INVISIBLE);
                                        header.setVisibility(View.INVISIBLE);
                                        submit.setVisibility(View.INVISIBLE);
                                        spinner.setVisibility(View.VISIBLE);

                                        sTime = String.valueOf(sTimeHour) + ":" + String.valueOf(sTimeMinute) + ":00";
                                        eTime = String.valueOf(currentHour) + ":" + String.valueOf(currentMinute) + ":00";

                                        BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse() {
                                            @Override
                                            public void processFinish(String output) {
                                                if (output.equals("true")) {
                                                    spinner.setVisibility(View.INVISIBLE);

                                                    AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                                                    alertDialog.setTitle("Success!");
                                                    alertDialog.setMessage("Schedule for this Employee has been Added");
                                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                                                                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    startActivity(intent2);
                                                                    finish();
                                                                }
                                                            });
                                                    alertDialog.show();
                                                } else if (output.equals("false")) {
                                                    spinner.setVisibility(View.INVISIBLE);

                                                    AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                                                    alertDialog.setTitle("Failure");
                                                    alertDialog.setMessage("Something Went Wrong When Adding this Schedule, Please Try Again Later");
                                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
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

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
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
        else
        {
            dButton.setVisibility(View.VISIBLE);
            cButton.setVisibility(View.VISIBLE);

            if (sTimeMinute < 10)
            {
                header.setText("Pick New End Time\nNew Start Time is " + String.valueOf(sTimeHour) + ":0" + String.valueOf(sTimeMinute)+"\nCurrent End Time is "+cEndTime);
            }
            else
            {
                header.setText("Pick End Time\nNew Start Time is " + String.valueOf(sTimeHour) + ":" + String.valueOf(sTimeMinute)+"\nCurrent End Time is "+cEndTime);
            }

            dButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    final AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                    alertDialog.setTitle("Delete Schedule");
                    alertDialog.setMessage("Are You Sure You Want to Delete this Schedule?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    clock.setVisibility(View.INVISIBLE);
                                    header.setVisibility(View.INVISIBLE);
                                    submit.setVisibility(View.INVISIBLE);
                                    dButton.setVisibility(View.INVISIBLE);
                                    cButton.setVisibility(View.INVISIBLE);
                                    spinner.setVisibility(View.VISIBLE);

                                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
                                    {
                                        @Override
                                        public void processFinish(String output)
                                        {
                                            if (output.equals("true"))
                                            {
                                                AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                                                alertDialog.setTitle("Success!");
                                                alertDialog.setMessage("Schedule for this Employee has been Deleted");
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
                                            else
                                            {
                                                AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                                                alertDialog.setTitle("Failure");
                                                alertDialog.setMessage("Something Went Wrong When Deleting this Schedule, Please Try Again Later");
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
                                    backgroundTask.execute("deleteTimetable", eid, day, month);
                                }
                            });

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
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

            cButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    final AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                    alertDialog.setTitle("Update/Edit Schedule");
                    alertDialog.setMessage("Are You Sure You Want to Confirm this New Schedule?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    int currentHour;
                                    int currentMinute;

                                    if (Build.VERSION.SDK_INT >= 23) {
                                        currentHour = clock.getHour();
                                        currentMinute = clock.getMinute();
                                    } else {
                                        currentHour = clock.getCurrentHour();
                                        currentMinute = clock.getCurrentMinute();
                                    }

                                    if (currentHour <= sTimeHour)
                                    {
                                        Toast.makeText(timetableForm2.this, "End Time Cannot be Less than Start Time + 1 Hour", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        clock.setVisibility(View.INVISIBLE);
                                        header.setVisibility(View.INVISIBLE);
                                        submit.setVisibility(View.INVISIBLE);
                                        dButton.setVisibility(View.INVISIBLE);
                                        cButton.setVisibility(View.INVISIBLE);
                                        spinner.setVisibility(View.VISIBLE);

                                        sTime = String.valueOf(sTimeHour) + ":" + String.valueOf(sTimeMinute) + ":00";
                                        eTime = String.valueOf(currentHour) + ":" + String.valueOf(currentMinute) + ":00";

                                        BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse() {
                                            @Override
                                            public void processFinish(String output) {
                                                if (output.equals("true")) {
                                                    spinner.setVisibility(View.INVISIBLE);

                                                    AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                                                    alertDialog.setTitle("Success!");
                                                    alertDialog.setMessage("Schedule for this Employee has been Uodated");
                                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                                                                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    startActivity(intent2);
                                                                    finish();
                                                                }
                                                            });
                                                    alertDialog.show();
                                                } else if (output.equals("false")) {
                                                    spinner.setVisibility(View.INVISIBLE);

                                                    AlertDialog alertDialog = new AlertDialog.Builder(timetableForm2.this).create();
                                                    alertDialog.setTitle("Failure");
                                                    alertDialog.setMessage("Something Went Wrong When Updating this Schedule, Please Try Again Later");
                                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
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
                                        backgroundTask.execute("editTimetable", eid, day, month, sTime, eTime);
                                    }
                                }
                            });

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
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
}

