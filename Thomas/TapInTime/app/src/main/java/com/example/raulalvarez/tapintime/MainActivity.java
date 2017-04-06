package com.example.raulalvarez.tapintime;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    TextView yeeid;
    Button checkin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        yeeid = (TextView)findViewById(R.id.yeeid);
        checkin = (Button)findViewById(R.id.checkin);

        checkin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v)
            {

                if(!String.valueOf(yeeid.getText()).equals("")) {

                    String employee = yeeid.getText().toString();

                    String method = "getEmployeeInfo";

                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
                    {
                        @Override
                        public void processFinish(String output)
                        {
                            ArrayList<String> info = new ArrayList<String>();

                            if (output.equals("null "))
                            {
                                Toast.makeText(MainActivity.this, "Employee Does Not Exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                /*String[] tokens = output.split(",");

                                for (String t : tokens)
                                {
                                    info.add(t);
                                }*/

                                //Toast.makeText(MainActivity.this, "YUSH", Toast.LENGTH_SHORT).show();
                                yeeid.setText("");
                                Intent myIntent = new Intent(getApplicationContext(), ClockIn.class);
                                startActivity(myIntent);

                            }
                        }
                    });

                    backgroundTask.execute(method,employee);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        if(intent.hasExtra(NfcAdapter.EXTRA_TAG))
        {
            Toast.makeText(this, "Reading from Tag", Toast.LENGTH_SHORT).show();
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(parcelables != null && parcelables.length > 0)
            {
                readTextfromMessage((NdefMessage)parcelables[0]);
            }
        }
    }

    private void readTextfromMessage(NdefMessage ndefMessage)
    {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords != null && ndefRecords.length > 0)
        {
            NdefRecord ndefRecord = ndefRecords[0];

            String content = getTextFromNdefRecord(ndefRecord);

            if(content == null)
            {
                Toast.makeText(this, "Tag is Empty", Toast.LENGTH_SHORT).show();
            }
            else
            {
                yeeid.setText(content);
            }
        }
    }

    private String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String content = null;

        try
        {
            byte[] payload = ndefRecord.getPayload();
            String encoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTf-8";

            int languageSize = payload[0] & 0063;

            content = new String(payload, languageSize+1, payload.length - languageSize - 1, encoding);
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("createTextRecord", e.getMessage());
        }

        return content;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        enableForegroundDispatchSystem();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        disableForegroundDispatchSystem();
    }

    private void enableForegroundDispatchSystem()
    {
        Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem()
    {
        nfcAdapter.disableForegroundDispatch(this);
    }
}