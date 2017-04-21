package com.example.thomas.tapin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Thomas on 21/04/2017.
 */

public class EmployerMain extends AppCompatActivity
{
    TextView nameDisplay;
    Button logout;
    Button add;
    Button check;
    Button timetable;

    String username = "";
    String cid = "";
    String name = "";

    EmployerSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employermain);

        setTitle("Tap In Employer Mode");

        session = new EmployerSessionManager(getApplicationContext());

        nameDisplay = (TextView)findViewById(R.id.name);
        logout = (Button)findViewById(R.id.logout);
        add = (Button)findViewById(R.id.add);
        check = (Button)findViewById(R.id.checkStaff);
        timetable = (Button)findViewById(R.id.timetable);

        //check if the user is already logged in
        if (session.checkLogin())
        {
            //finish will end the current activity, this will ensure that if the user clicks back the app will just close
            finish();
        }

        //get user data from current session and store in HashMap
        HashMap<String, String> user = session.getUserDetails();

        username = user.get(EmployerSessionManager.KEY_USER);
        cid = user.get(EmployerSessionManager.KEY_CID);
        name = user.get(EmployerSessionManager.KEY_NAME);

        nameDisplay.setText(name);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final AlertDialog alertDialog = new AlertDialog.Builder(EmployerMain.this).create();
                alertDialog.setTitle("Log Out");
                alertDialog.setMessage("Are You Sure You Want to Log out?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                session.logOut();
                                finish();
                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                alertDialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), nfcCheck.class);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), view.class);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });

        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), addTimetables.class);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
    }
}