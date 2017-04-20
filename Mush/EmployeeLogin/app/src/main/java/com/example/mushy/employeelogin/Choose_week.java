package com.example.mushy.employeelogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Mushy on 4/20/2017
 */

public class Choose_week extends AppCompatActivity
{
    public TextView tv_choose;
    public static Spinner spinner;
    public Button button_choose;
    public static Activity fa;
    private Calendar calendar;
    private int monday_index, month, last_day_of_month, end;
    private boolean condition = false;
    List<String> spinnerArray =  new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_week);
        fa = this; // Used so we can finish this activity when we log out


        tv_choose = (TextView) findViewById(R.id.tv_choose);
        spinner = (Spinner) findViewById(R.id.spinner);
        button_choose = (Button) findViewById(R.id.button_choose);

        // Populating the spinner

        calendar = Calendar.getInstance();
        monday_index = calendar.get(Calendar.DAY_OF_MONTH);

        while(monday_index != Calendar.MONDAY && monday_index != (Calendar.MONDAY +7) && monday_index != (Calendar.MONDAY +14) && monday_index != (Calendar.MONDAY +21))
        {
            monday_index--;
        }
        /*
            Because everything starts at 0 and we are starting everything at 1
            This is the first monday of the month
        */
        monday_index+=2;

        month = calendar.get(Calendar.MONTH) +1;
        last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        end= monday_index +6;
        if( end > last_day_of_month)
        {
            month++;
            end -= last_day_of_month;
            last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
            condition=true;
        }
        if(  month< 10)
        {
            int index=0;
            do
            {
                if(condition == false)
                {
                    spinnerArray.add(monday_index + "/0" + String.valueOf(month) + " - " + end + "/0" + String.valueOf(month));
                }
                else
                {
                    spinnerArray.add(monday_index + "/0" + String.valueOf(month-1) + " - " + end + "/0" + String.valueOf(month));
                    condition=false;
                }
                monday_index = end +1;
                end+=7;
                if( end > last_day_of_month)
                {
                    month++;
                    end -= last_day_of_month;
                    last_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                    condition=true;
                }
                index++;
            }while(index <4);

        }
        else
        {
            int index=0;
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
            }while(index<4);

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

                Intent intent = new Intent(getApplicationContext(), secondActivity.class);
                startActivity(intent);
            }
        });
    }


}