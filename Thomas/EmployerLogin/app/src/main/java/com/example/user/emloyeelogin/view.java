package com.example.user.emloyeelogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class view extends AppCompatActivity
{
    private ListView listView;
    private EmployeeScroll scroll;
    private ArrayList<Employee> currentEmployees;
    String cid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

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

                if (output.equals("null "))
                {

                }
                else
                {
                    String[] tokens = output.split(" ");

                    for (String t : tokens)
                    {
                        info.add(t);
                        //System.out.println(t);
                    }

                    for (int i = 0; i < info.size(); i++)
                    {
                        String employee = info.get(i);

                        String[] splitInfo = employee.split(",");

                        //System.out.println(splitInfo[0]+" "+splitInfo[1]);
                        Employee n = new Employee(splitInfo[0], splitInfo[1]);
                        currentEmployees.add(n);
                    }

                    scroll = new EmployeeScroll(getApplicationContext(), currentEmployees);
                    listView.setAdapter(scroll);
                }
            }
        });
        backgroundTask.execute("getCheckedIn", cid, "1", "3");
    }
}
