package com.example.thomas.tapin;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Thomas on 21/04/2017.
 */

public class nfcCheck extends AppCompatActivity
{
    private NfcAdapter nfcAdapter;
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_check);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        desc = (TextView)findViewById(R.id.textView4);

        String cid = getIntent().getExtras().getString("cid");
        int nfc_chk = getIntent().getExtras().getInt("nfc_chk");

        if (nfc_chk == 0)
        {
            desc.setText("this will be needed to load\nEmployee info on TapIn Card");
            setTitle("Add Employee");
        }
        else
        {
            desc.setText("this will be needed to get\nEmployee info from TapIn Card");
            setTitle("Tap In Hub");
        }

        if (nfcAdapter != null && nfcAdapter.isEnabled() && nfc_chk == 0)
        {
            Intent intent = new Intent(getApplicationContext(), add.class);
            intent.putExtra("cid", cid);
            startActivity(intent);
            finish();
        }
        else if (nfcAdapter != null && nfcAdapter.isEnabled() && nfc_chk == 1)
        {
            Intent intent = new Intent(getApplicationContext(), HubMain.class);
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
        Intent intent = new Intent(this, EmployerMain.class);
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