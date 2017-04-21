package com.example.thomas.tapin;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Thomas on 21/04/2017.
 */

public class EmployeeListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<Employee> employeeList;

    public EmployeeListAdapter(Context mContext, List<Employee> employeeList)
    {
        this.mContext = mContext;
        this.employeeList = employeeList;
    }

    @Override
    public int getCount()
    {
        return employeeList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return employeeList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = View.inflate(mContext, R.layout.employee_list, null);
        TextView tvName= (TextView) v.findViewById(R.id.tv_name);
        TextView tvDescription= (TextView) v.findViewById(R.id.tv_description);

        // set text for TextView
        tvName.setText(employeeList.get(position).getName());
        tvDescription.setText(employeeList.get(position).getDescription());

        // Depending if working the text will be either BLACK or RED
        if(employeeList.get(position).getDescription().equals("Not Scheduled"))
        {
            tvDescription.setTextColor(Color.RED);
            tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        }
        else
        {
            tvDescription.setTextColor(Color.BLACK);
            tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        }
        // save product id to tag
        v.setTag(employeeList.get(position).getId());

        return v;
    }
}

