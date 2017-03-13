package com.example.thomas.tapin3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class read extends AppCompatActivity
{
    TextView age;
    TextView fname;
    TextView sname;

    EditText userID;

    Button get;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        get = (Button)findViewById(R.id.get);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v)
            {
                userID = (EditText)findViewById(R.id.enter);
                age = (TextView)findViewById(R.id.age);
                fname = (TextView)findViewById(R.id.fname);
                sname = (TextView)findViewById(R.id.sname);

                if (!String.valueOf(userID.getText()).equals(""))
                {
                    String user = userID.getText().toString();
                    String method = "read";

                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
                    {
                        @Override
                        public void processFinish(String output)
                        {
                            ArrayList<String> info = new ArrayList<String>();
                            if (output.equals(""))
                            {
                                info.add("");
                                info.add("Employee not Found");
                                info.add("");
                            }
                            else
                            {
                                String[] tokens = output.split(" ");

                                for (String t : tokens)
                                {
                                    info.add(t);
                                }
                            }

                            age.setText("Age: "+info.get(0));
                            fname.setText("First Name: "+info.get(1));
                            sname.setText("Second Name: "+info.get(2));
                        }
                    });

                    backgroundTask.execute(method, user);
                }
                else
                {
                    Toast.makeText(read.this, "Please Enter Employee ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
