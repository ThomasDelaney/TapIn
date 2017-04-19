package com.example.mushy.employeelogin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class secondActivity extends Activity
{
    private ListView lvProduct;
    private ProductListAdapter adapter;
    private List<Product> mProductList;
    private int[] days_working= new int[7];

    // Temp Product used for sorting the arraylist
    Product temp= new Product(1000, "Temp","Temp");
    public TextView Total_Hours_tv;
    float Total_Hours;
    int  Hours=0, Halves=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Total_Hours_tv = (TextView) findViewById(R.id.total_hours);
        final ArrayList<String> days = new ArrayList<>();
        lvProduct = (ListView) findViewById(R.id.listview_product);
        mProductList = new ArrayList<>();
        setTitle("Schedule for the current week");


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

                // Getting the current month

                int month = calendar.get(Calendar.MONTH) +1;

                // Getting the details from the DB
                String[] tokens = output.split("-");
                for(String t : tokens)
                {
                    days.add(t);
                }
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
                    if( monday_index + 7 < last_day_of_Month )
                    {
                        if(Integer.valueOf(splitInfo[0]) >= (monday_index) && Integer.valueOf(splitInfo[0]) <= (monday_index +7) && Integer.valueOf(splitInfo[1]) == month)
                        {
                            // Split the start time and end time and get the ammount to be spent at work
                            String[] Start = splitInfo[2].split(":");
                            String[] Stop = splitInfo[3].split(":");
                            Hours = ( Integer.valueOf(Stop[0]) - Integer.valueOf(Start[0]) );
                            Halves = ( Integer.valueOf(Stop[1]) + Integer.valueOf(Start[1]) );
                            Total_Hours+= Hours + ((float)Halves/60) ;

                            days_working[(Integer.valueOf(splitInfo[0]) - monday_index + 1 ) % 7]=1;


                            if(Integer.valueOf(splitInfo[1]) < 10)
                            {
                                mProductList.add(new Product(Integer.valueOf(splitInfo[0]) , nDate +" "+ splitInfo[0] + "/0" + splitInfo[1], nSTime1 + " - " + nSTime2));
                            }
                            else
                            {
                                mProductList.add(new Product(Integer.valueOf(splitInfo[0]) , nDate +" "+ splitInfo[0] + "/" + splitInfo[1], nSTime1 + " - " + nSTime2));
                            }
                        }

                    }
                    else  // The week goes over two months
                    {
                        if( (Integer.valueOf(splitInfo[0]) >= (monday_index) && Integer.valueOf(splitInfo[0]) <= last_day_of_Month && Integer.valueOf(splitInfo[1]) == month) || ((Integer.valueOf(splitInfo[1]) == month+1) && Integer.valueOf(splitInfo[0]) <= (monday_index + 7 - last_day_of_Month)) )
                        {
                            // Split the start time and end time and get the ammount to be spent at work
                            String[] Start = splitInfo[2].split(":");
                            String[] Stop = splitInfo[3].split(":");
                            Hours = ( Integer.valueOf(Stop[0]) - Integer.valueOf(Start[0]) );
                            Halves = ( Integer.valueOf(Stop[1]) + Integer.valueOf(Start[1]) );
                            Total_Hours+= Hours + ((float)Halves/60) ;

                            days_working[(Integer.valueOf(splitInfo[0]) - monday_index + 1 ) % 7]=1;
                            if(Integer.valueOf(splitInfo[1]) < 10)
                            {
                                mProductList.add(new Product(Integer.valueOf(splitInfo[0]) , nDate +" "+ splitInfo[0] + "/0" + splitInfo[1], nSTime1 + " - " + nSTime2));
                            }
                            else
                            {
                                mProductList.add(new Product(Integer.valueOf(splitInfo[0]) , nDate +" "+ splitInfo[0] + "/" + splitInfo[1], nSTime1 + " - " + nSTime2));
                            }
                        }
                    }
                }
                for(int index=0; index<7; index++)
                {
                    if(days_working[index] != 1)
                    {
                        String MyDate2= Day_Of_Week(index);

                        if(month <10)
                        {
                            mProductList.add(new Product(index + monday_index - 1, MyDate2 + " " + (monday_index + index - 1) + "/0" + month, "Not Scheduled"));
                        }
                        else
                        {
                            mProductList.add(new Product(index + monday_index - 1, MyDate2 + " " + (monday_index + index - 1) + "/" + month, "Not Scheduled"));
                        }
                    }
                }


                /*
                    Ordering the Arralist with a bubble sort algorithm
                */
                for (int i = 0; i < mProductList.size(); i++)
                {
                    for (int j = 1; j < (mProductList.size() - i); j++)
                    {
                        if (mProductList.get(j-1).id > mProductList.get(j).id)
                        {
                            // id, name, desc
                            temp.id = mProductList.get(j-1).id;
                            temp.name = mProductList.get(j-1).name;
                            temp.description = mProductList.get(j-1).description;

                            mProductList.get(j-1).id = mProductList.get(j).id;
                            mProductList.get(j-1).name = mProductList.get(j).name;
                            mProductList.get(j-1).description= mProductList.get(j).description;

                            mProductList.get(j).id = temp.id;
                            mProductList.get(j).name = temp.name;
                            mProductList.get(j).description = temp.description;
                        }
                    } // end inner for
                } // end outer for

                String total_text="Total Hours This Week: " + String.valueOf(Total_Hours);
                Total_Hours_tv.setText(total_text);
                // init adapter
                adapter = new ProductListAdapter(getApplicationContext(), mProductList);
                lvProduct.setAdapter(adapter);
            }
        });

        // getId gets the id of the employee
        backgroundTask.execute("getTimetable", MainActivity.getId());
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
