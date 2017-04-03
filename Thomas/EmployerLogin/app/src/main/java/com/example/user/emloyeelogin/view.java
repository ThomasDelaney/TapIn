package com.example.user.emloyeelogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class view extends AppCompatActivity
{
    private ListView listView;
    private EmployeeScroll scroll;
    private ArrayList<Employee> currentEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


    }
}
