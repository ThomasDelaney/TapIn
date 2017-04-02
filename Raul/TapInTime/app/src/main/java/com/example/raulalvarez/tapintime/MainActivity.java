package com.example.raulalvarez.tapintime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText yee;
    Button checkin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkin = (Button)findViewById(R.id.checkin);

        checkin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v)
            {
                yee = (EditText)findViewById(R.id.employee);

                if(!String.valueOf(yee.getText()).equals("")) {

                    String employee = yee.getText().toString();

                    String method = "getEmployeeInfo";

                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
                    {
                        @Override
                        public void processFinish(String output)
                        {
                            ArrayList<String> info = new ArrayList<String>();

                            if (output.equals("null "))
                            {
                                Toast.makeText(MainActivity.this, "Employee Does Not Exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                /*String[] tokens = output.split(",");

                                for (String t : tokens)
                                {
                                    info.add(t);
                                }*/

                                Toast.makeText(MainActivity.this, "YUSH", Toast.LENGTH_SHORT).show();
                                //session.createLoginSession(info.get(2), info.get(1), info.get(0));
                                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                //startActivity(intent);
                                //finish();
                            }
                        }
                    });

                    backgroundTask.execute(method,employee);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}