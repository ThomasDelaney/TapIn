package com.example.user.emloyeelogin;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    TextView nameDisplay;
    Button logout;
    Button add;
    Button check;

    String username = "";
    String cid = "";
    String name = "";

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());

        nameDisplay = (TextView)findViewById(R.id.name);
        logout = (Button)findViewById(R.id.logout);
        add = (Button)findViewById(R.id.add);
        check = (Button)findViewById(R.id.checkStaff);

        //check if the user is already logged in
        if (session.checkLogin())
        {
            //finish will end the current activity, this will ensure that if the user clicks back the app will just close
            finish();
        }

        //get user data from current session and store in HashMap
        HashMap<String, String> user = session.getUserDetails();

        username = user.get(SessionManager.KEY_USER);
        cid = user.get(SessionManager.KEY_CID);
        name = user.get(SessionManager.KEY_NAME);

        nameDisplay.setText(name);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                session.logOut();
                finish();
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
    }
}
