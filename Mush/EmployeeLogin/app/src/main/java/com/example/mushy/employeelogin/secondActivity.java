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

                // Add sample data for test
                mProductList.add(new Product(1, "Test1", 100, "Tester1"));
                mProductList.add(new Product(2, "Test2", 200, "Tester2"));
                mProductList.add(new Product(3, "Test3", 300, "Tester3"));
                mProductList.add(new Product(4, "Test4", 400, "Tester4"));
                mProductList.add(new Product(5, "Test5", 500, "Tester5"));


                // init adapter
                adapter = new ProductListAdapter(getApplicationContext(), mProductList);
                lvProduct.setAdapter(adapter);

            }
        });
        backgroundTask.execute("getTimetable", "1");


    }
}
