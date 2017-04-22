package com.example.thomas.tapin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Thomas on 21/04/2017.
 */

public class Choose_week extends AppCompatActivity
{
    TextView welcome;
    TextView company;
    TextView pos;
    public TextView tv_choose;
    public Spinner spinner;
    public Button button_choose;
    public Button logout;
    public TextView eName, ePos, eComp;
    private boolean condition = false;
    private String eid;
    List<String> spinnerArray = new ArrayList<String>();
    private ProgressBar progressBar;

    EmployeeSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_week);

        session = new EmployeeSessionManager(getApplicationContext());

        setTitle("Tap In Employee");

        //check if the user is already logged in
        if (session.checkLogin())
        {
            //finish will end the current activity, this will ensure that if the user clicks back the app will just close
            finish();
        }

        logout = (Button)findViewById(R.id.button_logout);


        logout.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v)
            {
                final AlertDialog alertDialog = new AlertDialog.Builder(Choose_week.this).create();
                alertDialog.setTitle("Log Out");
                alertDialog.setMessage("Are You Sure You Want to Log out?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                session.logOut();
                                finish();
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

        Calendar calendar;
        int monday_index, month, last_day_of_month, end;

        welcome = (TextView)findViewById(R.id.welcome);
        company = (TextView)findViewById(R.id.company);
        pos = (TextView)findViewById(R.id.position);
        eName = (TextView) findViewById(R.id.welcomeName);
        ePos = (TextView) findViewById(R.id.positionName);
        eComp = (TextView) findViewById(R.id.companyName);
        tv_choose = (TextView) findViewById(R.id.tv_choose);
        spinner = (Spinner) findViewById(R.id.spinner);
        button_choose = (Button) findViewById(R.id.button_choose);
        progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        HashMap<String, String> user = session.getUserDetails();

        welcome.setVisibility(View.INVISIBLE);
        company.setVisibility(View.INVISIBLE);
        pos.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);
        eName.setVisibility(View.INVISIBLE);
        ePos.setVisibility(View.INVISIBLE);
        eComp.setVisibility(View.INVISIBLE);
        tv_choose.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
        button_choose.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        eid = user.get(EmployeeSessionManager.KEY_ID);

        // Getting the data about the employee through the php file (Name, position title and company name)

        BackgroundTask backgroundTask = new BackgroundTask(
                new BackgroundTask.AsyncResponse()
                {
                    @Override
                    public void processFinish(String output)
                    {

                        if(output.equals("null"))
                        {
                            Toast.makeText(Choose_week.this, "No data in database", Toast.LENGTH_LONG).show();
                        }
                        else //if (output.equals("true"))
                        {
                            String[] splitInfo = output.split(",");
                            eName.setText(splitInfo[0]);
                            ePos.setText(splitInfo[1]);
                            eComp.setText(splitInfo[2]);
                        }

                        eName.setVisibility(View.VISIBLE);
                        ePos.setVisibility(View.VISIBLE);
                        eComp.setVisibility(View.VISIBLE);
                        tv_choose.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.VISIBLE);
                        button_choose.setVisibility(View.VISIBLE);
                        welcome.setVisibility(View.VISIBLE);
                        company.setVisibility(View.VISIBLE);
                        pos.setVisibility(View.VISIBLE);
                        logout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );
        backgroundTask.execute("getDetails", eid);

        // Populating the spinner
        calendar = Calendar.getInstance();
        monday_index = calendar.get(Calendar.DAY_OF_MONTH);

        while (monday_index != Calendar.MONDAY && monday_index != (Calendar.MONDAY + 7) && monday_index != (Calendar.MONDAY + 14) && monday_index != (Calendar.MONDAY + 21))
        {
            monday_index--;
        }
        /*
            Because everything starts at 0 and we are starting everything at 1
            This is the first monday of the month
        */
        monday_index += 1;

        month = calendar.get(Calendar.MONTH) + 1;
        last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        end = monday_index + 7;

        if (end > last_day_of_month)
        {
            month++;
            end -= last_day_of_month;
            last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
            condition = true;
        }

        if (month < 10)
        {
            int index = 0;
            do
            {
                if (!condition)
                {
                    spinnerArray.add(monday_index + "/0" + String.valueOf(month) + " - " + (end - 1) + "/0" + String.valueOf(month));
                }
                else
                {
                    spinnerArray.add(monday_index + "/0" + String.valueOf(month - 1) + " - " + (monday_index + 6) + "/0" + String.valueOf(month));
                    condition = false;
                }
                // monday of next week
                monday_index = end;
                end += 7;

                if (end > last_day_of_month)
                {
                    month++;
                    end -= last_day_of_month;
                    last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                    condition = true;
                }
                index++;
            }
            while (index < 4);

        }
        else
            {
            int index = 0;
            do
            {
                spinnerArray.add(monday_index + "/" + String.valueOf(month) + " - " + end + "/" + String.valueOf(month));
                monday_index = end + 1;
                end += 7;

                if (end > last_day_of_month)
                {
                    month++;
                    end -= last_day_of_month;
                    last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                }
                index++;
            }
            while (index < 4);
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);

        button_choose.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), EmployeeMain.class);
                intent.putExtra("week", spinner.getSelectedItemPosition());
                intent.putExtra("weekString", spinner.getSelectedItem().toString());
                startActivity(intent);
            }
        });
    }
}

