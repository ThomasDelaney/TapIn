package com.example.thomas.tapin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thomas on 21/04/2017
 */

public class EmployeeScroll extends BaseAdapter
{
    private Context context;
    private ArrayList<Employee> employees;
    private int type;

    public String day;
    public String month;

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
            TextView sTimeText = (TextView) v.findViewById(R.id.sTimeText);
            TextView eTimeText = (TextView) v.findViewById(R.id.eTimeText);
            Button ebutton = (Button) v.findViewById(R.id.ebutton);
            Button editDelete = (Button) v.findViewById(R.id.editDelete);


            ebutton.setVisibility(View.INVISIBLE);
            sTimeText.setVisibility(View.INVISIBLE);
            eTimeText.setVisibility(View.INVISIBLE);
            editDelete.setVisibility(View.INVISIBLE);
            name.setText(employees.get(position).getName());
            ejob.setText(employees.get(position).getJob());

            if (employees.get(position).getIsWorkingDay().equals("false"))
            {
                isWorking.setText("No Timetable for Today");
                isWorking.setTextColor(Color.parseColor("#ff0000"));
                ebutton.setVisibility(View.VISIBLE);

                ebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent2 = new Intent(context, timetableForm.class);
                        intent2.putExtra("day", day);
                        intent2.putExtra("month", month);
                        intent2.putExtra("eid", employees.get(position).getEid());
                        intent2.putExtra("name", employees.get(position).getName());
                        intent2.putExtra("type", 0);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent2);
                    }
                });
            }
            else
            {
                isWorking.setText("Timetable Added");
                isWorking.setTextColor(Color.parseColor("#0aad1a"));
                sTimeText.setVisibility(View.VISIBLE);
                eTimeText.setVisibility(View.VISIBLE);
                editDelete.setVisibility(View.VISIBLE);

                String[] sTimeSplit = employees.get(position).getTime1().split(":");
                String nSTime = sTimeSplit[0]+":"+sTimeSplit[1];

                String[] eTimeSplit = employees.get(position).getTime2().split(":");
                String nETime = eTimeSplit[0]+":"+eTimeSplit[1];

                sTimeText.setText(nSTime+"\n     -");
                eTimeText.setText(nETime);

                editDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent2 = new Intent(context, timetableForm.class);
                        intent2.putExtra("day", day);
                        intent2.putExtra("month", month);
                        intent2.putExtra("eid", employees.get(position).getEid());
                        intent2.putExtra("name", employees.get(position).getName());
                        intent2.putExtra("type", 1);
                        intent2.putExtra("stime", employees.get(position).getTime1());
                        intent2.putExtra("etime", employees.get(position).getTime2());
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent2);
                    }
                });
            }

            v.setTag(employees.get(position).getInAppID());

            return v;
        }
    }
}
