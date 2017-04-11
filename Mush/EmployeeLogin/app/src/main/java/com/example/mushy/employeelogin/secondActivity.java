package com.example.mushy.employeelogin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class secondActivity extends Activity
{
    private ListView lvProduct;
    private ProductListAdapter adapter;
    private List<Product> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Schedule for week 1");
        BackgroundTask backgroundTask = new BackgroundTask(
        new BackgroundTask.AsyncResponse()
        {
            @Override
            public void processFinish(String output)
            {

                final ArrayList<String> days = new ArrayList<>();

                lvProduct = (ListView) findViewById(R.id.listview_product);
                mProductList = new ArrayList<>();

                // Getting the details from the DB
                String[] tokens = output.split("-");
                for (String t : tokens)
                {
                    days.add(t);
                }
                for (int i = 0; i < days.size() -1; i++)
                {
                    String day = days.get(i);

                    String[] splitInfo = day.split(",");

                    System.out.println(splitInfo[0] + " " + splitInfo[1] + " " + splitInfo[2] + " " + splitInfo[3]);
                    SimpleDateFormat newDateFormat= new SimpleDateFormat("dd/MM/yyyy");
                    Date myDate=null;
                    Calendar c = Calendar.getInstance(); // used to get the year

                    try
                    {
                        myDate = newDateFormat.parse(splitInfo[0]+"/"+splitInfo[1]+"/"+c.get(Calendar.YEAR));
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                    newDateFormat.applyPattern("EEEE"); // get format in full weekday name
                    String nDate = newDateFormat.format(myDate);

                    String[] sTimeSplit = splitInfo[2].split(":");
                    String nSTime1= sTimeSplit[0] +":"+ sTimeSplit[1];

                    sTimeSplit = splitInfo[3].split(":");
                    String nSTime2= sTimeSplit[0] +":"+ sTimeSplit[1];

                    mProductList.add(new Product(Integer.valueOf(splitInfo[0]), nDate, nSTime1 + " - " +nSTime2));

                }



                // Add sample data for test


                // init adapter
                adapter = new ProductListAdapter(getApplicationContext(), mProductList);
                lvProduct.setAdapter(adapter);

            }
        });

        // getId gets the id of the employee
        backgroundTask.execute("getTimetable", MainActivity.getId());


    }
}
