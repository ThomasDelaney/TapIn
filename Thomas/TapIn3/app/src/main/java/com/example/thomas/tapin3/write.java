package com.example.thomas.tapin3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class write extends AppCompatActivity
{
    EditText age;
    EditText fname;
    EditText sname;

    Button write;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        write = (Button)findViewById(R.id.write);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                age = (EditText) findViewById(R.id.age);
                fname = (EditText)findViewById(R.id.fname);
                sname = (EditText)findViewById(R.id.sname);

                if (!String.valueOf(age.getText()).equals("") && !String.valueOf(fname.getText()).equals("") && !String.valueOf(sname.getText()).equals(""))
                {
                    String uAge = age.getText().toString();
                    String uFname = fname.getText().toString();
                    String uSname = sname.getText().toString();

                    String method = "write";

                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse() {
                        @Override
                        public void processFinish(String output)
                        {
                            Log.d("kek",output);

                            if (output.equals("true"))
                            {
                                Toast.makeText(write.this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(write.this, "Employee Could not be Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    backgroundTask.execute(method, uAge, uFname, uSname);
                }
                else
                {
                    Toast.makeText(write.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
