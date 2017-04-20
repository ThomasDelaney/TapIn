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
import java.util.List;


/**
 * Created by Mushy on 4/20/2017
 */

public class Choose_week extends AppCompatActivity
{
    public TextView tv_choose;
    public Spinner spinner;
    public Button button_choose;
    public static Activity fa;

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
        spinnerArray.add("item1");
        spinnerArray.add("item2");
        spinnerArray.add("item3");
        spinnerArray.add("item4");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner sItems = (Spinner) findViewById(R.id.spinner);
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