package com.example.mushy.employeelogin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
                lvProduct = (ListView) findViewById(R.id.listview_product);
                mProductList = new ArrayList<>();

                // Getting the details from the DB

                // Add sample data for test
                mProductList.add(new Product(1, "Monday", "9:00 - 18:00"));
                mProductList.add(new Product(2, "Tuesday", "9:00 - 18:00"));
                mProductList.add(new Product(3, "Wednesday", "9:00 - 18:00"));
                mProductList.add(new Product(4, "Thursday", "9:00 - 18:00"));
                mProductList.add(new Product(5, "Friday", "9:00 - 18:00"));
                mProductList.add(new Product(6, "Saturday", "9:00 - 18:00"));
                mProductList.add(new Product(7, "Sunday", "9:00 - 18:00"));


                // init adapter
                adapter = new ProductListAdapter(getApplicationContext(), mProductList);
                lvProduct.setAdapter(adapter);

            }
        });

        // getId gets the id of the employee
        backgroundTask.execute("getTimetable", MainActivity.getId());


    }
}
