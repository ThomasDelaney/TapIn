package com.example.thomas.tapin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Thomas on 21/04/2017.
 */

public class ClockIn extends AppCompatActivity
{
    TextView disptime;
    TextView working;
    TextView pay;

    Button clockin;
    Button clockout;

    private ProgressBar spinner;

    String yeeid;

    String currentDateTimeString;
    String minimuminTime;
    String maximuminTime;
    String minimumoutTime;
    String maximumoutTime;
    String timetable;

    String day;
    String month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        setTitle("Tap In Hub");

        yeeid = getIntent().getExtras().getString("employeeIDHub");

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        currentDateTimeString = sdf.format(d);

        disptime = (TextView)findViewById(R.id.clock);
        working = (TextView)findViewById(R.id.working);
        pay = (TextView)findViewById(R.id.payment);

        spinner = (ProgressBar)findViewById(R.id.spinner2);

        clockin = (Button)findViewById(R.id.clockin);
        clockout= (Button)findViewById(R.id.clockout);
        disptime.setText(currentDateTimeString);

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        String method = "ClockInCheck";
        day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        spinner.setVisibility(View.VISIBLE);

        BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
        {
            @Override
            public void processFinish(final String output)
            {

                if (output.equals("null "))
                {
                    spinner.setVisibility(View.INVISIBLE);
                    working.setText("You are not working today");
                }
                else
                {
                    spinner.setVisibility(View.INVISIBLE);
                    working.setText("You are working today");


                    final String[] tokens = output.split(",");
                    System.out.println(output);

                    final String[] starttimestr;
                    final String[] endtimestr;
                    String[] currenttimestr;
                    final float wage = Float.parseFloat(tokens[4].trim());

                    starttimestr = tokens[2].split(":");
                    endtimestr = tokens[3].split(":");
                    currenttimestr = currentDateTimeString.split(":");
                    timetable = tokens[1];

                    int[] currenttime = new int[3];
                    int[] starttime = new int[3];
                    int[] endtime = new int[3];

                    for(int i = 0; i < 3; i++)
                    {
                        starttime[i] = Integer.valueOf(starttimestr[i].trim());
                        endtime[i] = Integer.valueOf(endtimestr[i].trim());
                        currenttime[i] = Integer.valueOf(currenttimestr[i].trim());
                    }

                    int minHour = starttime[0];
                    int maxHour = starttime[0];
                    int minMinutes = starttime[1];
                    int maxMinutes = starttime[1];

                    int minHour2 = endtime[0];
                    int maxHour2 = endtime[0];
                    int minMinutes2 = endtime[1];
                    int maxMinutes2 = endtime[1];

                    for(int i = 0; i < 15; i++ )
                    {
                        minMinutes--;
                        minMinutes2--;
                        maxMinutes++;
                        maxMinutes2++;

                        if(minMinutes == -1)
                        {
                            minMinutes = 59;
                            minHour--;
                        }
                        else if (maxMinutes == 60)
                        {
                            maxMinutes = 1;
                            maxHour++;
                        }

                        if(minMinutes2 == -1)
                        {
                            minMinutes2 = 59;
                            minHour2--;
                        }
                        else if (maxMinutes2 == 60)
                        {
                            maxMinutes2 = 1;
                            maxHour2++;
                        }
                    }

                    minimuminTime = String.format("%s:%s:%s", String.valueOf(minHour), String.valueOf(minMinutes), "00");
                    maximuminTime = String.format("%s:%s:%s", String.valueOf(maxHour), String.valueOf(maxMinutes), "00");

                    minimumoutTime = String.format("%s:%s:%s", String.valueOf(minHour2), String.valueOf(minMinutes2), "00");
                    maximumoutTime = String.format("%s:%s:%s", String.valueOf(maxHour2), String.valueOf(maxMinutes2), "00");

                    System.out.println(minimuminTime);
                    System.out.println(maximuminTime);

                    System.out.println(minimumoutTime);
                    System.out.println(maximumoutTime);


                    clockin.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick (View v) {

                            try {
                                Date d = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                currentDateTimeString = sdf.format(d);

                                Date min2 = new SimpleDateFormat("HH:mm:ss").parse(minimuminTime);
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.setTime(min2);

                                Date max2 = new SimpleDateFormat("HH:mm:ss").parse(maximuminTime);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(max2);

                                Date cur2 = new SimpleDateFormat("HH:mm:ss").parse(currentDateTimeString);
                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.setTime(cur2);

                                Date x = calendar3.getTime();
                                System.out.println(calendar1.getTime());
                                System.out.println(calendar2.getTime());
                                System.out.println(calendar3.getTime());


                                if (x.after(calendar1.getTime()) && x.before(calendar2.getTime()))
                                {
                                    String method = "ClockIn";

                                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
                                    {
                                        @Override
                                        public void processFinish(String output)
                                        {
                                            System.out.println(output);

                                            if(output.equals("Success "))
                                            {
                                                working.setTextColor(Color.GREEN);
                                                working.setText("Clocked in Succesfully");
                                                sendNotification();
                                            }
                                            else if(output.equals("Failure "))
                                            {
                                                working.setTextColor(Color.RED);
                                                working.setText("You can't clock in again");
                                            }
                                        }
                                    });

                                    if( BackgroundTask.isNetworkAvailable(ClockIn.this))
                                    {
                                        backgroundTask.execute(method,timetable,currentDateTimeString);
                                    }
                                    else
                                    {
                                        finish();
                                        Toast.makeText(ClockIn.this,"No internet connection", Toast.LENGTH_LONG ).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(ClockIn.this, "Sorry, You Can not Clock in Now", Toast.LENGTH_SHORT).show();
                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    clockout.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick (View v) {

                            try {
                                Date d = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                currentDateTimeString = sdf.format(d);

                                Date min = new SimpleDateFormat("HH:mm:ss").parse(minimumoutTime);
                                Calendar calendarmin = Calendar.getInstance();
                                calendarmin.setTime(min);

                                Date max = new SimpleDateFormat("HH:mm:ss").parse(maximumoutTime);
                                Calendar calendarmax = Calendar.getInstance();
                                calendarmax.setTime(max);

                                Date cur = new SimpleDateFormat("HH:mm:ss").parse(currentDateTimeString);
                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.setTime(cur);
                                Date x = calendar3.getTime();

                                String start = tokens[2];
                                String end = tokens[3];
                                if (x.after(calendarmin.getTime()) && x.before(calendarmax.getTime()))
                                {
                                    String method = "ClockOut";
                                    final String totalstr = String.format("%.2f", getPayment(start, end, wage));
                                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
                                    {

                                        @Override
                                        public void processFinish(String output)
                                        {
                                            System.out.println(output);

                                            if(output.equals("Success "))
                                            {
                                                working.setTextColor(Color.GREEN);
                                                working.setText("Clocked out Succesfully");
                                                pay.setText("Payment: €" + totalstr);

                                            }
                                            else if(output.equals("Failure ") )
                                            {
                                                working.setTextColor(Color.RED);
                                                working.setText("Please try again");
                                            }
                                        }
                                    });
                                    if( BackgroundTask.isNetworkAvailable(ClockIn.this))
                                    {
                                        backgroundTask.execute(method, timetable, currentDateTimeString, totalstr);
                                    }
                                    else
                                    {
                                        finish();
                                        Toast.makeText(ClockIn.this,"No internet connection", Toast.LENGTH_LONG ).show();
                                    }

                                }
                                else
                                {
                                    Toast.makeText(ClockIn.this, "Sorry, You Can not Clock out Now", Toast.LENGTH_SHORT).show();
                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }
            }
        });


        backgroundTask.execute(method, day, month, yeeid);
    }

    void sendNotification()
    {
        BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
        {
            @Override
            public void processFinish(String output)
            {
                BackgroundTask backgroundTask2 = new BackgroundTask(new BackgroundTask.AsyncResponse()
                {
                    @Override
                    public void processFinish(String output)
                    {
                    }
                });
                if( BackgroundTask.isNetworkAvailable(ClockIn.this))
                {
                    backgroundTask2.execute("sendNotification", output, yeeid, day, month);
                }
                else
                {
                    finish();
                    Toast.makeText(ClockIn.this,"No internet connection", Toast.LENGTH_LONG ).show();
                }

            }
        });
        if( BackgroundTask.isNetworkAvailable(ClockIn.this))
        {
            backgroundTask.execute("getCIDfromYEEID", yeeid);
        }
        else
        {
            finish();
            Toast.makeText(ClockIn.this,"No internet connection", Toast.LENGTH_LONG ).show();
        }

    }

    float getPayment(String start, String end, float wage)
    {
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

        long difference = date2.getTime() - date1.getTime();

        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
        System.out.println(minutes);
        DecimalFormat df = new DecimalFormat("0.00");
        float hours =  (float) minutes / 60;
        System.out.println(hours);
        float total = hours * wage;

        return total;
    }

}

