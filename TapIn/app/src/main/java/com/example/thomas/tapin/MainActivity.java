package com.example.thomas.tapin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    EmployerSessionManager employerSessionManager;
    EmployeeSessionManager employeeSessionManager;

    Button employerButton;
    Button employeeButton;
    Button hubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employerButton = (Button)findViewById(R.id.employerLoginButton);
        employeeButton = (Button)findViewById(R.id.employeeLoginButton);
        hubButton = (Button)findViewById(R.id.hubButton);

        employerSessionManager = new EmployerSessionManager(getApplicationContext());
        employeeSessionManager = new EmployeeSessionManager(getApplicationContext());

        //employerSessionManager.logOut();
        //employeeSessionManager.logOut();

        if (employerSessionManager.isLoggedIn())
        {
            Intent i = new Intent(getApplicationContext(), EmployerMain.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
        else if (employeeSessionManager.isLoggedIn())
        {
            Intent i = new Intent(getApplicationContext(), Choose_week.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }

        employerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), EmployerMain.class);
                startActivity(intent);
            }
        });

        hubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), EmployerLogin.class);
                intent.putExtra("employerLoginType", 1);
                startActivity(intent);
            }
        });

        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), EmployeeLogin.class);
                startActivity(intent);
            }
        });
    }
}
