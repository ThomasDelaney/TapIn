package com.example.thomas.tapin;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Created by Thomas on 21/04/2017.
 */

public class add3 extends AppCompatActivity
{
    NfcAdapter nfcAdapter;
    private GifImageView gifImageView; //will be used for nfc gif

    TextView header;
    private ProgressBar spinner;

    String eID = "";
    String eName = "";
    String eEmail = "";
    String ePhone = "";
    String eJob = "";
    String eWage = "";
    String ePart = "";
    String cid = "";
    String username = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add3);

        eName = getIntent().getExtras().getString("name");
        eEmail = getIntent().getExtras().getString("email");
        ePhone = getIntent().getExtras().getString("phone");
        eJob = getIntent().getExtras().getString("job");
        eWage = getIntent().getExtras().getString("wage");
        ePart = getIntent().getExtras().getString("parttime");
        cid = getIntent().getExtras().getString("cid");
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");

        setTitle("Add Employee");

        header = (TextView)findViewById(R.id.nfctext);

        gifImageView = (GifImageView) findViewById(R.id.gifImageView);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.INVISIBLE);

        try
        {
            InputStream inputStream = getAssets().open("nfc.gif");

            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        }
        catch (IOException e)
        {}

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    public void onNewIntent(final Intent intent)
    {
        super.onNewIntent(intent);

        if (intent.hasExtra(NfcAdapter.EXTRA_TAG))
        {
            //make gif and header text invisible
            gifImageView.setVisibility(View.INVISIBLE);
            header.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.VISIBLE);

            BackgroundTask backgroundTask = new BackgroundTask(new BackgroundTask.AsyncResponse()
            {
                @Override
                public void processFinish(String output)
                {

                    if (output.equals("false "))
                    {
                        Toast.makeText(add3.this, "Error While Adding Employee", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            backgroundTask.execute("writeEmployee", eName, username, eEmail, ePhone, eJob, eWage, ePart, password, cid);

            BackgroundTask backgroundTask2 = new BackgroundTask(new BackgroundTask.AsyncResponse()
            {
                @Override
                public void processFinish(String output)
                {

                    if (output.equals("null"))
                    {
                        Toast.makeText(add3.this, "Error While Writing Employee to Card", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        eID = output;
                    }

                    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                    NdefMessage ndefMessage = createNdefMessage(eID);

                    writeMessage(tag, new NdefMessage(new NdefRecord(NdefRecord.TNF_EMPTY, null, null, null)));
                    writeMessage(tag, ndefMessage);

                    AlertDialog alertDialog = new AlertDialog.Builder(add3.this).create();
                    alertDialog.setTitle("Success!");
                    alertDialog.setMessage("Employee Has been Successfully added to Database and NFC Card");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent2);
                                    finish();
                                }
                            });
                    alertDialog.show();
                }
            });
            backgroundTask2.execute("getEmployeeID", eName, username, eEmail, ePhone, eJob, eWage, ePart, password, cid);
        }
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
        Intent intent = new Intent(this, add3.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem()
    {
        nfcAdapter.disableForegroundDispatch(this);
    }

    private void formatTag(Tag tag, NdefMessage ndefMessage)
    {
        try
        {
            NdefFormatable ndefFormatable = NdefFormatable.get(tag);

            if (ndefFormatable == null)
            {
                Toast.makeText(this, "Tag is not formatable", Toast.LENGTH_LONG).show();
            }

            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();
        }
        catch (Exception e)
        {
            Log.e("formatTag", e.getMessage());
        }
    }

    private void writeMessage(Tag tag, NdefMessage ndefMessage)
    {
        try
        {
            if (tag == null)
            {
                Toast.makeText(this, "An Error has Occurred, Please Try Again", Toast.LENGTH_LONG).show();
            }

            Ndef ndef = Ndef.get(tag);

            if (ndef == null)
            {
                formatTag(tag, ndefMessage);
            }
            else
            {
                ndef.connect();

                if (!ndef.isWritable())
                {
                    Toast.makeText(this, "Tag is not Writable", Toast.LENGTH_LONG).show();
                    ndef.close();
                    return;
                }

                ndef.writeNdefMessage(ndefMessage);
                ndef.close();
            }
        }
        catch (Exception e)
        {
            Log.e("writeMessage", e.getMessage());
        }
    }

    private NdefRecord createTextRecord (String message)
    {
        try
        {
            byte[] language;
            language = Locale.getDefault().getLanguage().getBytes("UTF-8");

            final byte[] text = message.getBytes("UTF-8");
            final int languageSize = language.length;
            final int textLength = text.length;

            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLength);

            payload.write((byte) (languageSize & 0x1F));
            payload.write(language, 0, languageSize);
            payload.write(text, 0, textLength);

            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("createTextRecord", e.getMessage());
        }
        return null;
    }

    private NdefMessage createNdefMessage(String message)
    {
        NdefRecord ndefRecord = createTextRecord(message);

        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[] {ndefRecord});

        return ndefMessage;
    }
}
