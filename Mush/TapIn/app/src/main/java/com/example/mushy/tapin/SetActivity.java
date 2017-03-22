package com.example.mushy.tapin;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class SetActivity extends AppCompatActivity
{
    NfcAdapter nfcAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        // variable that identifies the NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        /*
            Test to see if the phone has NFC and if it's enabled
            Also if we have the permission to use the NFC
        */

        if( nfcAdapter == null || !(nfcAdapter.isEnabled()) )
        {
            Toast.makeText(this, "NFC Not Enabled", Toast.LENGTH_LONG).show();
            finish();
        }
    } // end onCreate

    protected void onNewIntent(Intent intent) {

        Toast.makeText(this, "NFC Tag Found!", Toast.LENGTH_LONG).show();

        super.onNewIntent(intent);
    }

    /*
        This makes the NFC search for the nearby tags
     */
    @Override
    protected void onResume()
    {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[] {};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);

        super.onResume();
    }

    /*
        When the NFC pauses (AKA stops looking for a nearby tag)
        We display a toast saying that it found one
     */
    @Override
    protected void onPause()
    {

        nfcAdapter.disableForegroundDispatch(this);
        onNewIntent(getIntent());
        super.onPause();
    }

}
