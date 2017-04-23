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
 * Created by Thomas on 21/04/2017
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
    private boolean condition = false, condition2 = false;
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
                        else
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
        // initially the monday_index starts at our current day
        calendar = Calendar.getInstance();
        monday_index = calendar.get(Calendar.DAY_OF_MONTH);

        // We decrement until we meet the number of the closest monday index (only if current day > monday index)
        while (monday_index != Calendar.MONDAY && monday_index != (Calendar.MONDAY + 7) && monday_index != (Calendar.MONDAY + 14) && monday_index != (Calendar.MONDAY + 21))
        {
            monday_index--;
        }
        /*
            Because everything starts at 0 and we are starting everything at 1, we increment the monday index once
            This is now the index of the closest monday
        */
        monday_index += 1;

        // Months start at 0 and we are wroking with Jan == 1 so we increment the value once
        month = calendar.get(Calendar.MONTH) + 1;
        // Getting the number of the last day of the month
        last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        // End is a variable used for the checks (so we don't affect the initial value of the monday index)
        end = monday_index + 7;

        // if the end of the week crosses to the next month
        if (end > last_day_of_month)
        {
            // Because in the spinner we display the numbers from monday index to end-1 (or monday index +6)
            // We have to make sure that we don't change the month if we are displaying the last day of the current month
            // ex: 24/04- 30/04   (notice the month of the 30/04)
            if(end-1 == last_day_of_month) condition2 = true;
            month++;
            // Getting the number of the day from the next month
            // if the end is greater than the last day then the number of the day in the next month is end - last day
            end -= last_day_of_month;
            // Getting the number of the last day of the next month
            last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
            // Just a check in case we crossed to the next month
            condition = true;
        }

        // Simple if to check if we have to add a "0"
        if (month < 10)
        {
            // index used to make sure that the do while loops only 4 times aka 4 weeks
            int index = 0;
            do
            {
                // If we didn't cross to the next month
                if (!condition)
                {
                    spinnerArray.add(monday_index + "/0" + String.valueOf(month) + " - " + (end - 1) + "/0" + String.valueOf(month));
                }
                else // if we crossed to the next month
                {
                    // if it's the last day of the previous month then we make sure to put the number of the last month instead of the new one
                    if(condition2)
                    {
                        spinnerArray.add(monday_index + "/0" + String.valueOf(month - 1) + " - " + (monday_index + 6) + "/0" + String.valueOf(month-1));
                    }
                    else // we put the normal value of the next month
                    {
                        spinnerArray.add(monday_index + "/0" + String.valueOf(month - 1) + " - " + (monday_index + 6) + "/0" + String.valueOf(month));
                    }
                    // Resetting the condition  (even though it's not really needed)
                    condition = false;
                }
                // We give the end the value of the next monday index
                monday_index = end;
                end += 7;

                // Same check as before
                if (end > last_day_of_month)
                {
                    if(end-1 == last_day_of_month) condition2 = true;
                    month++;
                    end -= last_day_of_month;
                    last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                    condition = true;
                }
                index++;
            }
            while (index < 4);

        }
        else // month >= 10
        {
            int index = 0;
            do
            {
                // Same idea as before but adapted a bit for this part
                spinnerArray.add(monday_index + "/" + String.valueOf(month) + " - " + end + "/" + String.valueOf(month));
                monday_index = end;
                end += 7;

                // Same check as before
                if (end > last_day_of_month)
                {
                    if(end-1 == last_day_of_month) condition2 = true;
                    month++;
                    end -= last_day_of_month;
                    last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                    condition = true;
                }
                index++;
            }
            while (index < 4);
        }

        // setting the adapter
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

