package com.example.mushy.employeelogin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
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

                    System.out.println(splitInfo[0] + " " + splitInfo[1] + " " + splitInfo[2]);
                    String day_name;

                    switch(Integer.valueOf(splitInfo[0]) % 7)
                    {
                        case 1:
                        {
                            day_name="Monday";
                            break;
                        }
                        case 2:
                        {
                            day_name="Tuesday";
                            break;
                        }
                        case 3:
                        {
                            day_name="Wednesday";
                            break;
                        }
                        case 4:
                        {
                            day_name="Thursday";
                            break;
                        }

                        case 5:
                        {
                            day_name="Friday";
                            break;
                        }
                        case 6:
                        {
                            day_name="Saturday";
                            break;
                        }
                        case 7:
                        {
                            day_name="Sunday";
                            break;
                        }
                        default:
                        {
                            day_name="Ignore This";
                        }

                    }
                    String[] sTimeSplit = splitInfo[1].split(":");
                    String nSTime1= sTimeSplit[0] +":"+ sTimeSplit[1];

                    sTimeSplit = splitInfo[2].split(":");
                    String nSTime2= sTimeSplit[0] +":"+ sTimeSplit[1];

                    mProductList.add(new Product(Integer.valueOf(splitInfo[0]), day_name, nSTime1 + " - " +nSTime2));

                    //Employee n = new Employee(i, splitInfo[0], splitInfo[1], splitInfo[2], splitInfo[3], splitInfo[4], splitInfo[5]);
                    //currentEmployees.add(n);
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
