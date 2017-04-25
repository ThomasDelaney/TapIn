package com.example.thomas.tapin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Thomas on 21/04/2017.
 */

public class EmployeeMain extends AppCompatActivity
{
    private ListView lvProduct;
    private EmployeeListAdapter adapter;
    private List<Employee> employeeList;
    private int[] days_working= new int[7];
    // Temp Product used for sorting the arraylist
    private Employee temp = new Employee(1000, "Temp","Temp");
    public TextView Total_Hours_tv;
    private String eid;
    private String selectedWeek;
    public int selected;
    private long total_to_work;
    private int total_hours_to_work, total_minutes_to_work;


    EmployeeSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

        session = new EmployeeSessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        eid = user.get(EmployeeSessionManager.KEY_ID);

        total_to_work=0;
        Total_Hours_tv = (TextView) findViewById(R.id.total_hours);
        final ArrayList<String> days = new ArrayList<>();
        lvProduct = (ListView) findViewById(R.id.listview_product);
        employeeList = new ArrayList<Employee>();

        // get the selected week
        selected = getIntent().getExtras().getInt("week");
        selectedWeek = getIntent().getExtras().getString("weekString");

        setTitle("Week "+selectedWeek + " Schedule");

        BackgroundTask backgroundTask = new BackgroundTask(
                new BackgroundTask.AsyncResponse()
                {
                    @Override
                    public void processFinish(String output)
                    {
                        // Getting the index for the weeks Monday
                        Calendar calendar = Calendar.getInstance();
                        int monday_index = calendar.get(Calendar.DAY_OF_MONTH);
                        int last_day_of_Month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

                        while(monday_index != Calendar.MONDAY && monday_index != (Calendar.MONDAY +7) && monday_index != (Calendar.MONDAY +14) && monday_index != (Calendar.MONDAY +21))
                        {
                            monday_index--;
                        }
                        // Because everything starts at 0 and we are starting everything at 1
                        monday_index+=2;

                        // Test to display other weeks
                        monday_index= monday_index +  (7 * selected);


                        // Getting the current month

                        int month = calendar.get(Calendar.MONTH) +1;

                        // Getting the details from the DB
                        String[] tokens = output.split("-");
                        for(String t : tokens)
                        {
                            days.add(t);
                        }


                        int last_day_of_months = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                        int ends= monday_index;

                        if( monday_index > last_day_of_months)
                        {
                            month++;
                            monday_index -= last_day_of_months;
                            last_day_of_months = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                        }
                        ends-=7;

                        for (int i = 0; i < days.size() -1; i++)
                        {
                            String day = days.get(i);
                            String[] splitInfo = day.split(",");

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


                            // If the months changes in the middle of the week that we are displaying
                            if( monday_index  + 7 < last_day_of_Month )
                            {
                                if(Integer.valueOf(splitInfo[0]) >= (monday_index ) && Integer.valueOf(splitInfo[0]) <= (monday_index  +7) && Integer.valueOf(splitInfo[1]) == month)
                                {
                                    String start = splitInfo[2];
                                    String end = splitInfo[3];
                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                    Date date1 = new Date();
                                    Date date2 = new Date();
                                    try
                                    {
                                        date1 = format.parse(start);
                                        date2 = format.parse(end);
                                    }
                                    catch (ParseException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    long total_to_work = date2.getTime() - date1.getTime();
                                    // Adding to the total
                                    total_minutes_to_work  += (int) ((total_to_work / (1000*60)) % 60);
                                    total_hours_to_work   += (int) ((total_to_work / (1000*60*60)) % 24);

                                    days_working[(Integer.valueOf(splitInfo[0]) - (monday_index - 1) ) % 7]=1;
                                    if(Integer.valueOf(splitInfo[1]) < 10)
                                    {
                                        employeeList.add(new Employee(Integer.valueOf(splitInfo[0]) , nDate +" "+ splitInfo[0] + "/0" + splitInfo[1], nSTime1 + " - " + nSTime2));
                                    }
                                    else
                                    {
                                        employeeList.add(new Employee(Integer.valueOf(splitInfo[0]) , nDate +" "+ splitInfo[0] + "/" + splitInfo[1], nSTime1 + " - " + nSTime2));
                                    }
                                }

                            }
                            else  // The week goes over two months
                            {
                                if( (Integer.valueOf(splitInfo[0]) >= (monday_index) && Integer.valueOf(splitInfo[0]) <= last_day_of_Month && Integer.valueOf(splitInfo[1]) == month) || ((Integer.valueOf(splitInfo[1]) == month+1) && Integer.valueOf(splitInfo[0]) <= (monday_index + 7 - last_day_of_Month)) )
                                {
                                    // Get the amount of time to be spent at work
                                    String start = splitInfo[2];
                                    String end = splitInfo[3];
                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

                                    Date date1 = new Date();
                                    Date date2 = new Date();

                                    try
                                    {
                                        date1 = format.parse(start);
                                        date2 = format.parse(end);
                                    }
                                    catch (ParseException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    long total_to_work = date2.getTime() - date1.getTime();
                                    // Adding to the total
                                    total_minutes_to_work  += (int) ((total_to_work / (1000*60)) % 60);
                                    total_hours_to_work   += (int) ((total_to_work / (1000*60*60)) % 24);

                                    days_working[(Integer.valueOf(splitInfo[0]) - (monday_index -1) ) % 7]=1;
                                    if(Integer.valueOf(splitInfo[1]) < 10)
                                    {
                                        employeeList.add(new Employee(Integer.valueOf(splitInfo[0]) , nDate +" "+ splitInfo[0] + "/0" + splitInfo[1], nSTime1 + " - " + nSTime2));
                                    }
                                    else
                                    {
                                        employeeList.add(new Employee(Integer.valueOf(splitInfo[0]) , nDate +" "+ splitInfo[0] + "/" + splitInfo[1], nSTime1 + " - " + nSTime2));
                                    }
                                }
                            }
                        }
                        // Making the objects for the days when the employee doesn't work
                        for(int index=0; index<7; index++)
                        {
                            if(days_working[index] != 1)
                            {
                                String MyDate2= Day_Of_Week(index);

                                if(month <10)
                                {
                                    //mProductList.add(new Product(index + end -2 , MyDate2 + " " + (end   + index  ) + "/0" + month, "Not Scheduled"));
                                    employeeList.add(new Employee(index + monday_index - 1, MyDate2 + " " + (monday_index + index - 1) + "/0" + month, "Not Scheduled"));

                                }
                                else
                                {
                                    //mProductList.add(new Product(index + end -2 , MyDate2 + " " + (end  + index ) + "/" + month, "Not Scheduled"));
                                    employeeList.add(new Employee(index + monday_index - 1, MyDate2 + " " + (monday_index + index - 1) + "/" + month, "Not Scheduled"));
                                }
                            }
                        }


                /*
                    Ordering the ArrayList with a bubble sort algorithm
                */
                        for (int i = 0; i < employeeList.size(); i++)
                        {
                            for (int j = 1; j < (employeeList.size() - i); j++)
                            {
                                if (employeeList.get(j-1).employeeIDinteger > employeeList.get(j).employeeIDinteger)
                                {
                                    // id, name, desc
                                    temp.employeeIDinteger = employeeList.get(j-1).employeeIDinteger;
                                    temp.name = employeeList.get(j-1).name;
                                    temp.description = employeeList.get(j-1).description;

                                    employeeList.get(j-1).employeeIDinteger = employeeList.get(j).employeeIDinteger;
                                    employeeList.get(j-1).name = employeeList.get(j).name;
                                    employeeList.get(j-1).description= employeeList.get(j).description;

                                    employeeList.get(j).employeeIDinteger = temp.employeeIDinteger;
                                    employeeList.get(j).name = temp.name;
                                    employeeList.get(j).description = temp.description;
                                }
                            } // end inner for
                        } // end outer for


                        String total_text;
                        if(total_hours_to_work == 1)
                        {
                            total_text="This Week: " + String.valueOf(String.format(Locale.UK,"%d hr & %d mins", total_hours_to_work, total_minutes_to_work));
                        }
                        else
                        {
                            total_text="This Week: " + String.valueOf(String.format(Locale.UK,"%d hrs & %d mins", total_hours_to_work, total_minutes_to_work));
                        }
                        Total_Hours_tv.setText(total_text);
                        // init adapter
                        adapter = new EmployeeListAdapter(getApplicationContext(), employeeList);
                        lvProduct.setAdapter(adapter);
                    }
                });
        if( BackgroundTask.isNetworkAvailable(EmployeeMain.this))
        {
            backgroundTask.execute("getTimetable", eid);
        }
        else
        {
            finish();
            Toast.makeText(EmployeeMain.this,"No internet connection", Toast.LENGTH_LONG ).show();
        }

    }



    public String Day_Of_Week(int a)
    {
        String output_String;

        switch(a + 1)
        {
            case 1:
            {
                output_String = "Monday";
                break;
            }
            case 2:
            {
                output_String = "Tuesday";
                break;
            }
            case 3:
            {
                output_String = "Wednesday";
                break;
            }
            case 4:
            {
                output_String = "Thursday";
                break;
            }
            case 5:
            {
                output_String = "Friday";
                break;
            }
            case 6:
            {
                output_String = "Saturday";
                break;
            }
            case 7:
            {
                output_String = "Sunday";
                break;
            }
            default:
            {
                output_String = "error";
            }
        }

        return output_String;
    }
}

