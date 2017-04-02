package com.example.user.emloyeelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class loginPage extends AppCompatActivity
{
    EditText user;
    EditText pass;
    Button login;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v)
            {
                user = (EditText)findViewById(R.id.user);
                pass = (EditText)findViewById(R.id.pass);

                if (!String.valueOf(user.getText()).equals("") && !String.valueOf(pass.getText()).equals(""))
                {
                    String username = user.getText().toString();
                    String password = pass.getText().toString();

                    String method = "employerLogin";

                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
                    {
                        @Override
                        public void processFinish(String output)
                        {
                            ArrayList<String> info = new ArrayList<String>();

                            if (output.equals("null "))
                            {
                                Toast.makeText(loginPage.this, "Password Incorrect or Employer Does Not Exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String[] tokens = output.split(",");

                                for (String t : tokens)
                                {
                                    info.add(t);
                                }

                                session.createLoginSession(info.get(2), info.get(1), info.get(0));

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                    backgroundTask.execute(method, username, password);
                }
                else
                {
                    Toast.makeText(loginPage.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}