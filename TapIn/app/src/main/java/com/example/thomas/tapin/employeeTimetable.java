package com.example.thomas.tapin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * Created by Thomas on 21/04/2017.
 */

public class employeeTimetable extends AppCompatActivity
{
    private String day;
    private String month;
    private String cid;
    private String year;

    private ListView listView;
    private EmployeeScroll scroll;
    private ArrayList<Employee> currentEmployees;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        cid = getIntent().getExtras().getString("cid");
        day = getIntent().getExtras().getString("day");
        month = getIntent().getExtras().getString("month");
        year = getIntent().getExtras().getString("year");

        setTitle("Schedule for Date: "+day+"/"+month+"/"+year);

        spinner = (ProgressBar)findViewById(R.id.progressBar2);
        spinner.setVisibility(View.VISIBLE);
        spinner.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        listView = (ListView)findViewById(R.id.employeeList);

        currentEmployees = new ArrayList<Employee>();
        getCurrentEmployees();
    }

    public void getCurrentEmployees()
    {
        final ArrayList<String> info = new ArrayList<String>();

        BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
        {
            @Override
            public void processFinish(String output)
            {
                if (output.equals("null"))
                {
                    spinner.setVisibility(View.INVISIBLE);

                    AlertDialog alertDialog = new AlertDialog.Builder(employeeTimetable.this).create();
                    alertDialog.setTitle("Sorry");
                    alertDialog.setMessage("Some has gone wrong. Please Try Again Later");
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
                    String[] tokens = output.split("-");

                    for (String t : tokens)
                    {
                        info.add(t);
                    }

                    for (int i = 0; i < info.size(); i++)
                    {
                        String employee = info.get(i);

                        String[] splitInfo = employee.split(",");

                        Employee n = new Employee(i, splitInfo[0], splitInfo[1], splitInfo[2], splitInfo[3], splitInfo[4], splitInfo[5]);
                        currentEmployees.add(n);
                    }

                    spinner.setVisibility(View.INVISIBLE);
                    scroll = new EmployeeScroll(getApplicationContext(), currentEmployees, 1);
                    listView.setAdapter(scroll);

                    scroll.day = day;
                    scroll.month = month;
                }
            }
        });
        backgroundTask.execute("eWorkingCheck", cid, day, month);
    }
}

