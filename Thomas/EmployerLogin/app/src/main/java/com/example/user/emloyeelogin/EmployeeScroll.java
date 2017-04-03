package com.example.user.emloyeelogin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thomas on 03/04/2017.
 */

public class EmployeeScroll extends BaseAdapter
{
    private Context context;
    private ArrayList<Employee> employees;

    public EmployeeScroll(Context context, ArrayList<Employee> employees)
    {
        this.context = context;
        this.employees = employees;
    }

    @Override
    public int getCount()
    {
        return employees.size();
    }

    @Override
    public Object getItem(int position)
    {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = View.inflate(context, R.layout.employee, null);

        TextView name = (TextView)v.findViewById(R.id.ename);
        TextView checkin = (TextView)v.findViewById(R.id.checkin);

        name.setText(employees.get(position).getName());

        if (employees.get(position).isCheckedIn())
        {
            checkin.setText("Clocked In");
        }
        else
        {
            checkin.setText("Not Clocked In");
        }

        return v;
    }
}
