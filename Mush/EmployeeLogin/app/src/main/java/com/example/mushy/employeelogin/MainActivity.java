package com.example.mushy.employeelogin;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{
    public Button loginbutton;
    public EditText user2;
    public EditText pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbutton = (Button) findViewById(R.id.loginButton);
        user2 = (EditText) findViewById(R.id.user);
        pass2 = (EditText) findViewById(R.id.pass);

        loginbutton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v)
            {
            // Check if the fields are empty
            if (String.valueOf(user2.getText()).equals("") || String.valueOf(pass2.getText()).equals(""))
            {
                Toast.makeText(MainActivity.this, "Fill in both fields", Toast.LENGTH_LONG).show();
            }
            else
            {

                String user = user2.getText().toString();
                String method = "read";
                String pass= pass2.getText().toString();

                BackgroundTask backgroundTask = new BackgroundTask(
                new BackgroundTask.AsyncResponse()
                {
                    @Override
                    public void processFinish(String output)
                    {
                        if (output.equals("true"))
                        {
                            Intent intent = new Intent(getApplicationContext(), secondActivity.class);
                            startActivity(intent);
                        }
                        else if(output.equals("false"))
                        {
                            Toast.makeText(MainActivity.this, "Wrong login details", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                backgroundTask.execute(method, user, pass);
            }

            }
        });


    }
}
