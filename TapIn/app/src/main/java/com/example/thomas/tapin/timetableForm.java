package com.example.thomas.tapin;

import android.content.DialogInterface;
import android.content.Intent;
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

public class timetableForm extends AppCompatActivity
{
    String day;
    String month;
    String eid;
    String name;

    String stime;
    String etime;

    TimePicker clock;
    Button next;
    Button dButton;
    Button cButton;

    TextView headerText;

    ProgressBar spinner;

    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_timetable);

        day = getIntent().getExtras().getString("day");
        month = getIntent().getExtras().getString("month");
        eid = getIntent().getExtras().getString("eid");
        type = getIntent().getExtras().getInt("type");
        name = getIntent().getExtras().getString("name");

        if (type == 1)
        {
            stime = getIntent().getExtras().getString("stime");
            etime = getIntent().getExtras().getString("etime");
        }

        setTitle("Schedule For: "+name);

        clock = (TimePicker)findViewById(R.id.timeSelect);
        next = (Button)findViewById(R.id.nbutton);
        dButton = (Button)findViewById(R.id.deleteButton);
        cButton = (Button)findViewById(R.id.continueButton);
        headerText = (TextView) findViewById(R.id.headerText);
        spinner = (ProgressBar)findViewById(R.id.dSpinner);

        next.setVisibility(View.INVISIBLE);
        dButton.setVisibility(View.INVISIBLE);
        cButton.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);

        clock.setIs24HourView(true);

        if (Build.VERSION.SDK_INT >= 23)
        {
            clock.setHour(8);
            clock.setMinute(0);
        }
        else
        {
            clock.setCurrentHour(8);
            clock.setCurrentMinute(0);
        }

        if (type == 0)
        {
            next.setVisibility(View.VISIBLE);

            headerText.setText("Pick Start Time");
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(getApplicationContext(), timetableForm2.class);
                    intent2.putExtra("day", day);
                    intent2.putExtra("month", month);
                    intent2.putExtra("eid", eid);
                    intent2.putExtra("name", name);

                    if (Build.VERSION.SDK_INT >= 23)
                    {
                        intent2.putExtra("sTimeHour", clock.getHour());
                        intent2.putExtra("sTimeMinute", clock.getMinute());
                    }
                    else
                    {
                        intent2.putExtra("sTimeHour", clock.getCurrentHour());
                        intent2.putExtra("sTimeMinute", clock.getCurrentMinute());
                    }
                    startActivity(intent2);
                }
            });
        }
        else
        {
            dButton.setVisibility(View.VISIBLE);
            cButton.setVisibility(View.VISIBLE);

            String[] sTimeSplit = stime.split(":");
            String nSTime = sTimeSplit[0]+":"+sTimeSplit[1];

            String[] eTimeSplit = etime.split(":");
            final String nETime = eTimeSplit[0]+":"+eTimeSplit[1];

            headerText.setText("Pick New Start Time\nCurrent Start Time is "+nSTime);

            cButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent2 = new Intent(getApplicationContext(), timetableForm2.class);
                    intent2.putExtra("day", day);
                    intent2.putExtra("month", month);
                    intent2.putExtra("eid", eid);
                    intent2.putExtra("type", type);
                    intent2.putExtra("etime", nETime);
                    intent2.putExtra("name", name);

                    if (Build.VERSION.SDK_INT >= 23)
                    {
                        intent2.putExtra("sTimeHour", clock.getHour());
                        intent2.putExtra("sTimeMinute", clock.getMinute());
                    }
                    else
                    {
                        intent2.putExtra("sTimeHour", clock.getCurrentHour());
                        intent2.putExtra("sTimeMinute", clock.getCurrentMinute());
                    }

                    startActivity(intent2);
                }
            });

            dButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    final AlertDialog alertDialog = new AlertDialog.Builder(timetableForm.this).create();
                    alertDialog.setTitle("Delete Schedule");
                    alertDialog.setMessage("Are You Sure You Want to Delete this Schedule?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    clock.setVisibility(View.INVISIBLE);
                                    headerText.setVisibility(View.INVISIBLE);
                                    cButton.setVisibility(View.INVISIBLE);
                                    dButton.setVisibility(View.INVISIBLE);
                                    spinner.setVisibility(View.VISIBLE);

                                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
                                    {
                                        @Override
                                        public void processFinish(String output)
                                        {
                                            if (output.equals("true"))
                                            {
                                                spinner.setVisibility(View.INVISIBLE);
                                                AlertDialog alertDialog = new AlertDialog.Builder(timetableForm.this).create();
                                                alertDialog.setTitle("Success!");
                                                alertDialog.setMessage("Schedule for this Employee has been Deleted");
                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                                        new DialogInterface.OnClickListener()
                                                        {
                                                            public void onClick(DialogInterface dialog, int which)
                                                            {
                                                                Intent intent2 = new Intent(getApplicationContext(), EmployerMain.class);
                                                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                startActivity(intent2);
                                                                finish();
                                                            }
                                                        });
                                                alertDialog.show();
                                            }
                                            else
                                            {
                                                spinner.setVisibility(View.INVISIBLE);
                                                AlertDialog alertDialog = new AlertDialog.Builder(timetableForm.this).create();
                                                alertDialog.setTitle("Failure");
                                                alertDialog.setMessage("Something Went Wrong When Deleting this Schedule, Please Try Again Later");
                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                                        new DialogInterface.OnClickListener()
                                                        {
                                                            public void onClick(DialogInterface dialog, int which)
                                                            {
                                                                Intent intent2 = new Intent(getApplicationContext(), EmployerMain.class);
                                                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                startActivity(intent2);
                                                                finish();
                                                            }
                                                        });
                                                alertDialog.show();
                                            }
                                        }
                                    });
                                    if( BackgroundTask.isNetworkAvailable(timetableForm.this))
                                    {
                                        backgroundTask.execute("deleteTimetable", eid, day, month);
                                    }
                                    else
                                    {
                                        finish();
                                        Toast.makeText(timetableForm.this,"No internet connection", Toast.LENGTH_LONG ).show();
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
