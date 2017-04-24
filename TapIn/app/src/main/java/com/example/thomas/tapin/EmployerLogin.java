package com.example.thomas.tapin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Thomas on 21/04/2017.
 */

public class EmployerLogin extends AppCompatActivity
{
    EditText user;
    EditText pass;
    Button login;

    int type;
    int nfc_chk = 1;

    EmployerSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employerlogin);

        session = new EmployerSessionManager(getApplicationContext());

        type = getIntent().getExtras().getInt("employerLoginType");

        login = (Button)findViewById(R.id.login);

        setTitle("Tap In Employer Mode");

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
                                Toast.makeText(EmployerLogin.this, "Password Incorrect or Employer Does Not Exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String[] tokens = output.split(",");

                                for (String t : tokens)
                                {
                                    info.add(t);
                                }

                                if (type == 0)
                                {
                                    session.createLoginSession(info.get(2), info.get(1), info.get(0));

                                    Intent intent = new Intent(getApplicationContext(), EmployerMain.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {

                                    Intent intent = new Intent(getApplicationContext(), nfcCheck.class);
                                    intent.putExtra("nfc_chk", nfc_chk);
                                    startActivity(intent);

                                    /*Intent intent = new Intent(getApplicationContext(), HubMain.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();*/
                                }
                            }
                        }
                    });

                    backgroundTask.execute(method, username, password);
                }
                else
                {
                    Toast.makeText(EmployerLogin.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}