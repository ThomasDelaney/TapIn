package com.example.raulalvarez.tapintime;

/**
 * Created by raulalvarez on 3/4/17.
 */

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by raulalvarez on 2/4/17.
 */

public class BackgroundTask extends AsyncTask<String, Void, String>
{
    public AsyncResponse delegate = null;

    public BackgroundTask(AsyncResponse delegate)
    {
        this.delegate = delegate;
    }
    public interface AsyncResponse
    {
        void processFinish(String output);
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params)
    {
        String getEmployeeInfoUrl = "http://tapin.comli.com/getEmployeeInfo.php";
        String getCheckedIn = "http://tapin.comli.com/getCheckedIn2.php";

        String method = params[0];

        if (method.equals("getEmployeeInfo"))
        {

            String employee = params[1];

            try
            {
                URL url = new URL(getEmployeeInfoUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //encode data and write to php file on server
                String data = URLEncoder.encode("employee", "UTF-8")+"="+URLEncoder.encode(employee, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String response = "";

                String line = "";

                while((line = bufferedReader.readLine()) != null)
                {
                    response += line;
                    response += " ";
                }

                System.out.println(response);

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else if(method.equals("ClockIn"))
        {
            String day = params[1];
            String month = params[2];
            String cid = params[3];

            try
            {
                URL url = new URL(getCheckedIn);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //encode data and write to php file on server
                String data =   URLEncoder.encode("day", "UTF-8")+"="+URLEncoder.encode(day, "UTF-8")+"&"+
                                URLEncoder.encode("month", "UTF-8")+"="+URLEncoder.encode(month, "UTF-8")+"&"+
                                URLEncoder.encode("cid", "UTF-8")+"="+URLEncoder.encode(cid, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String response = "";

                String line = "";

                while((line = bufferedReader.readLine()) != null)
                {
                    response += line;
                    response += " ";
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;

    }

    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result)
    {
        delegate.processFinish(result);
    }
}

