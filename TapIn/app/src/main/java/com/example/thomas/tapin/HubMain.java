package com.example.thomas.tapin;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Thomas on 21/04/2017.
 */

public class HubMain  extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    TextView yeeid;
    public static String employee;
    private ProgressBar spinner;
    private GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_main);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        setTitle("Tap In Hub");


        yeeid = (TextView)findViewById(R.id.yeeid);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        gifImageView = (GifImageView)findViewById(R.id.gifImageView);

        try
        {
            InputStream inputStream = getAssets().open("nfc.gif");

            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        }
        catch (IOException e)
        {}
        gifImageView.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        if(intent.hasExtra(NfcAdapter.EXTRA_TAG))
        {
            gifImageView.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.VISIBLE);
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

                if(!String.valueOf(yeeid.getText()).equals("")) {

                    employee = yeeid.getText().toString();

                    String method = "getEmployeeInfo";

                    gifImageView.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                    BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
                    {
                        @Override
                        public void processFinish(String output)
                        {

                            if (output.equals("null "))
                            {
                                Toast.makeText(HubMain.this, "Employee Does Not Exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent myIntent = new Intent(getApplicationContext(), ClockIn.class);
                                myIntent.putExtra("employeeIDHub", employee);
                                startActivity(myIntent);
                                gifImageView.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                    backgroundTask.execute(method,employee);
                }
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
        Intent intent = new Intent(this, HubMain.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem()
    {
        nfcAdapter.disableForegroundDispatch(this);
    }

}
