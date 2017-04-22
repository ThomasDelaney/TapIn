package com.example.thomas.tapin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Thomas on 21/04/2017.
 */

public class EmployeeLogin extends AppCompatActivity
{
    public Button loginbutton;
    public EditText user2;
    public EditText pass2;
    public static String id;
    public static String Uname, Upass, Uid;
    EmployeeSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);

        setTitle("Tap In Employee");
        
        session = new EmployeeSessionManager(getApplicationContext());

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
                    Toast.makeText(EmployeeLogin.this, "Fill in both fields", Toast.LENGTH_LONG).show();
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

                                    if(output.equals("false"))
                                    {
                                        Toast.makeText(EmployeeLogin.this, "Wrong login details", Toast.LENGTH_LONG).show();
                                    }
                                    else //if (output.equals("true"))
                                    {
                                        // This returns the id of the employee based on the username and password
                                        id = output.replaceAll("[^0-9]", "");

                                        //create login session for employee
                                        session.createLoginSession(id, user2.getText().toString().trim(), pass2.getText().toString().trim());

                                        //call create week and end all previous activities
                                        Intent intent = new Intent(getApplicationContext(), Choose_week.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });


                    backgroundTask.execute(method, user, pass);
                }

            }
        });
    }

    public static String getUname() { return Uname;}
    public static String getUpass() { return Upass;}
    public static String getUid() {return Uid;}
}
