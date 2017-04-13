package com.example.user.emloyeelogin;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class nfcCheck extends AppCompatActivity
{
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_check);

        setTitle("Add Employee");

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        String cid = getIntent().getExtras().getString("cid");

        if (nfcAdapter != null && nfcAdapter.isEnabled())
        {
            Intent intent = new Intent(getApplicationContext(), add.class);
            intent.putExtra("cid", cid);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
    }

    @Override
    public void onResume()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentfilter = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentfilter, null);

        super.onResume();
    }

    @Override
    public void onPause()
    {
        nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }
}
