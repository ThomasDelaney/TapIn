package com.example.user.emloyeelogin;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thomas on 03/04/2017.
 */

public class EmployeeScroll extends BaseAdapter
{
    private Context context;
    private ArrayList<Employee> employees;
    private int type;

    public EmployeeScroll(Context context, ArrayList<Employee> employees, int type)
    {
        this.type = type;
        this.context = context;
        this.employees = employees;
    }

    public int getType()
    {
        return this.type;
    }

    public void setType(int type)
    {
        this.type = type;
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (type == 0)
        {
            View v = View.inflate(context, R.layout.employee, null);

            TextView name = (TextView) v.findViewById(R.id.ename);
            TextView checkin = (TextView) v.findViewById(R.id.checkin);

            name.setText(employees.get(position).getName());

            if (employees.get(position).isCheckedIn()) {
                checkin.setText("Clocked In");
                checkin.setTextColor(Color.parseColor("#0aad1a"));
            } else {
                checkin.setText("Not Clocked In");
                checkin.setTextColor(Color.parseColor("#ff0000"));
            }

            v.setTag(employees.get(position).getInAppID());

            return v;
        }
        else
        {
            final View v = View.inflate(context, R.layout.employee_time, null);

            TextView name = (TextView) v.findViewById(R.id.employeeName);
            TextView ejob = (TextView) v.findViewById(R.id.ejob);
            TextView isWorking = (TextView) v.findViewById(R.id.isWorking);
            Button ebutton = (Button) v.findViewById(R.id.ebutton);


            ebutton.setVisibility(View.INVISIBLE);
            name.setText(employees.get(position).getName());
            ejob.setText(employees.get(position).getJob());

            if (employees.get(position).getIsWorkingDay().equals("false"))
            {
                isWorking.setText("No Timetable for Today");
                isWorking.setTextColor(Color.parseColor("#ff0000"));
                ebutton.setVisibility(View.VISIBLE);
            }
            else
            {
                isWorking.setText("Timetable Already Added");
                isWorking.setTextColor(Color.parseColor("#0aad1a"));
            }

            v.setTag(employees.get(position).getInAppID());

            return v;
        }
    }
}
