package com.example.raulalvarez.tapintime;


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
    String minimuminTime;
    String maximuminTime;
    String minimumoutTime;
    String maximumoutTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        currentDateTimeString = sdf.format(d);

        disptime = (TextView)findViewById(R.id.clock);
        working = (TextView)findViewById(R.id.working);
        clockin = (Button)findViewById(R.id.clockin);
        clockout= (Button)findViewById(R.id.clockout);
        disptime.setText(currentDateTimeString);

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

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


                    String[] tokens = output.split(",");
                    System.out.println(output);


                    String[] starttimestr;
                    String[] endtimestr;
                    String[] currenttimestr;

                    starttimestr = tokens[2].split(":");
                    endtimestr = tokens[3].split(":");
                    currenttimestr = currentDateTimeString.split(":");


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
                        else if(minMinutes2 == -1)
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

                    minimuminTime = String.format("%s:%s:%s", String.valueOf(minHour), String.valueOf(minMinutes), starttimestr[2]);
                    maximuminTime = String.format("%s:%s:%s", String.valueOf(maxHour), String.valueOf(maxMinutes), starttimestr[2]);

                    minimumoutTime = String.format("%s:%s:%s", String.valueOf(minHour2), String.valueOf(minMinutes2), endtime[2]);
                    maximumoutTime = String.format("%s:%s:%s", String.valueOf(maxHour2), String.valueOf(maxMinutes2), endtime[2]);

                    System.out.println(minimumoutTime);
                    System.out.println(maximumoutTime);


                    clockin.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick (View v) {

                            try {

                                Date min = new SimpleDateFormat("HH:mm:ss").parse(minimuminTime);
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.setTime(min);

                                Date max = new SimpleDateFormat("HH:mm:ss").parse(maximuminTime);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(max);
                                calendar2.add(Calendar.DATE, 1);

                                Date cur = new SimpleDateFormat("HH:mm:ss").parse(currentDateTimeString);
                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.setTime(cur);
                                calendar3.add(Calendar.DATE, 1);

                                Date x = calendar3.getTime();

                                if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {


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

                                Date min = new SimpleDateFormat("HH:mm:ss").parse(minimumoutTime);
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.setTime(min);

                                Date max = new SimpleDateFormat("HH:mm:ss").parse(maximumoutTime);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(max);
                                calendar2.add(Calendar.DATE, 1);

                                Date cur = new SimpleDateFormat("HH:mm:ss").parse(currentDateTimeString);
                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.setTime(cur);
                                calendar3.add(Calendar.DATE, 1);

                                Date x = calendar3.getTime();

                                if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {


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
}
