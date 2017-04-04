package com.example.user.emloyeelogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class view extends AppCompatActivity
{
    private ListView listView;
    private EmployeeScroll scroll;
    private ArrayList<Employee> currentEmployees;
    private ProgressBar spinner;
    String cid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        spinner = (ProgressBar)findViewById(R.id.progressBar2);
        spinner.setVisibility(View.VISIBLE);

        cid = getIntent().getExtras().getString("cid");

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

                    AlertDialog alertDialog = new AlertDialog.Builder(view.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("There are No Employees Working Today");
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
                    String[] tokens = output.split(" ");

                    for (String t : tokens)
                    {
                        info.add(t);
                    }

                    for (int i = 0; i < info.size(); i++)
                    {
                        String employee = info.get(i);

                        String[] splitInfo = employee.split(",");

                        Employee n = new Employee(splitInfo[0], splitInfo[1]);
                        currentEmployees.add(n);
                    }

                    spinner.setVisibility(View.INVISIBLE);
                    scroll = new EmployeeScroll(getApplicationContext(), currentEmployees);
                    listView.setAdapter(scroll);
                }
            }
        });
        backgroundTask.execute("getCheckedIn", cid, "1", "3");
    }
}
