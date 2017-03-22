package com.example.mushy.tapin;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);


        // variable that identifies the NFC
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        /*
            Test to see if the phone has NFC and if it's enabled
            Also if we have the permission to use the NFC
        */

        if( nfcAdapter != null && nfcAdapter.isEnabled() )
        {
            Toast.makeText(this, "NFC Enabled", Toast.LENGTH_LONG).show();
        }
        else
        {
            finish();
            Toast.makeText(this, "NFC Not Enabled", Toast.LENGTH_LONG).show();
        }

    }

}
