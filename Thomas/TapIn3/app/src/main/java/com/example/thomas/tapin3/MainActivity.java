package com.example.thomas.tapin3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    public Button readButton;

    public void init()
    {
        readButton = (Button)findViewById(R.id.read);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v)
            {
                Intent intent = new Intent(MainActivity.this, read.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
