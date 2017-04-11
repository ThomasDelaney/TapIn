package com.example.raulalvarez.tapintime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by raulalvarez on 3/4/17.
 */


public class ClockIn extends AppCompatActivity {

    TextView disptime;
    TextView working;

    Button clockin;
    Button clockout;

    String yeeid = MainActivity.employee;

    String currentDateTimeString;
    String minimumTime;
    String maximumTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        currentDateTimeString = sdf.format(d);

        disptime = (TextView)findViewById(R.id.clock);
        working = (TextView)findViewById(R.id.working);
        disptime.setText(currentDateTimeString);

        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTime(d);
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        String method = "ClockIn";
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);


        BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
        {
            @Override
            public void processFinish(final String output)
            {
                final ArrayList<String> info = new ArrayList<String>();

                if (output.equals("null "))
                {
                    working.setText("You are not working today");
                }
                else
                {
                    working.setText("You are working today");

                    clockin = (Button)findViewById(R.id.clockin);
                    clockout= (Button)findViewById(R.id.clockout);

                    String[] tokens = output.split("-");

                    for (String t : tokens) {
                        info.add(t);
                    }


                    String[] starttimestr = new String[0];
                    String[] endtimestr = new String[0];
                    String[] splitInfo = new String[0];

                    for (int i = 0; i < info.size(); i++)
                    {
                        String employee = info.get(i);
                        splitInfo = employee.split(",");
                    }
                    starttimestr = splitInfo[2].split(":");
                    endtimestr = splitInfo[2].split(":");

                    //final int[] currenttime = new int[0];
                    final int[] starttime = new int[0];
                    int[] endtime = new int[0];
                    for(int i = 0; i < starttime.length; i++)
                    {
                        starttime[i] = Integer.parseInt(starttimestr[i]);
                        endtime[i] = Integer.parseInt(endtimestr[i]);
                        //currenttime[i] = Integer.parseInt(currentDateTimeString);
                    }

                    int minHour = starttime[0];
                    int maxHour = starttime[0];
                    int minMinutes = starttime[1];
                    int maxMinutes = starttime[1];
                    for(int i = 0; i < 15; i++ )
                    {
                        minMinutes--;
                        maxMinutes++;

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
                    }

                    minimumTime = String.format("%s:%s:%s", String.valueOf(minHour), String.valueOf(minMinutes), starttimestr[2]);
                    maximumTime = String.format("%s:%s:%s", String.valueOf(maxHour), String.valueOf(maxMinutes), starttimestr[2]);


                    clockin.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick (View v) {

                            try {

                                Date min = new SimpleDateFormat("HH:mm:ss").parse(minimumTime);
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.setTime(min);

                                Date max = new SimpleDateFormat("HH:mm:ss").parse(maximumTime);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(max);
                                calendar2.add(Calendar.DATE, 1);

                                Date cur = new SimpleDateFormat("HH:mm:ss").parse(currentDateTimeString);
                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.setTime(cur);
                                calendar3.add(Calendar.DATE, 1);

                                Date x = calendar3.getTime();

                                if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                    //checkes whether the current time is between 14:49:00 and 20:11:13.
                                    System.out.println(true);
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
                }
            }
        });


        backgroundTask.execute(method, day, month, yeeid);


    }
}
