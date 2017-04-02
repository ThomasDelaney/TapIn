package com.example.user.emloyeelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class add2 extends AppCompatActivity
{
    TextView name;
    EditText username;
    EditText password;
    EditText password2;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add2);

        name = (TextView)findViewById(R.id.eName2);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password2);
        next = (Button)findViewById(R.id.next2);

        final String eName = getIntent().getExtras().getString("name");
        final String eEmail = getIntent().getExtras().getString("email");
        final String ePhone = getIntent().getExtras().getString("phone");
        final String eJob = getIntent().getExtras().getString("job");
        final String eWage = getIntent().getExtras().getString("wage");
        final String ePart = getIntent().getExtras().getString("parttime");

        name.setText(eName);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().equals("") && !password.getText().toString().equals("") && !password2.getText().toString().equals(""))
                {
                    String uName = username.getText().toString();
                    String uPass1 = password.getText().toString();
                    String uPass2 = password2.getText().toString();

                    if (uPass1.equals(uPass2)) {
                        Intent intent = new Intent(getApplicationContext(), add3.class);
                        intent.putExtra("name", eName);
                        intent.putExtra("email", eEmail);
                        intent.putExtra("phone", ePhone);
                        intent.putExtra("job", eJob);
                        intent.putExtra("wage", eWage);
                        intent.putExtra("parttime", ePart);
                        intent.putExtra("username", uName);
                        intent.putExtra("password", uPass1);

                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(add2.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(add2.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
