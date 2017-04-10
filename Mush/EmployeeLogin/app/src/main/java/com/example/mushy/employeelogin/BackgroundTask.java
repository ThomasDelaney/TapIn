package com.example.mushy.employeelogin;

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
import java.net.URL;
import java.net.URLEncoder;

class BackgroundTask extends AsyncTask<String, Void, String>
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
        String method = params[0];

        if (method.equals("read"))
        {
            String read_url = "http://tapin.comli.com/employee.php";

            String user = params[1];
            String pass = params[2];

            try
            {
                URL url = new URL(read_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //encode data and write to php file on server
                String data = URLEncoder.encode("user", "UTF-8")+"="+URLEncoder.encode(user, "UTF-8") +"&"+ URLEncoder.encode("pass", "UTF-8")+"="+URLEncoder.encode(pass, "UTF-8");
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
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        else if (method.equals("getTimetable"))
            {
                String read_url = "http://tapin.comli.com/employee.php";
                String YeeID = params[1];


                try
                {
                    URL url = new URL(read_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    //encode data and write to php file on server
                    String data = URLEncoder.encode("employee", "UTF-8")+"="+URLEncoder.encode(YeeID, "UTF-8");

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
                } catch (IOException e)
                {
                    e.printStackTrace();
                }



            } // end else if

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
