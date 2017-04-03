package com.example.user.emloyeelogin;

import android.os.AsyncTask;

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
 * Created by Thomas on 23/03/2017.
 */

//This class deals with the database and the online server

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
        String employerLoginUrl = "http://tapin.comli.com/employerLogin.php";
        String writeEmployeeUrl = "http://tapin.comli.com/writeEmployee.php";
        String getEmployeeIDUrl = "http://tapin.comli.com/getEmployeeID.php";

        String method = params[0];

        if (method.equals("employerLogin"))
        {
            String username = params[1];
            String password = params[2];

            try
            {
                URL url = new URL(employerLoginUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //encode data and write to php file on server
                String data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
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
        else if (method.equals("writeEmployee"))
        {
            String name = params[1];
            String username = params[2];
            String email = params[3];
            String phone = params[4];
            String job = params[5];
            String wage = params[6];
            String parttime = params[7];
            String password = params[8];
            String cid = params[9];

            try
            {
                URL url = new URL(writeEmployeeUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //encode data and write to php file on server
                String data = URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name, "UTF-8")+"&"+URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")
                        +"&"+URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"+URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8")
                        +"&"+URLEncoder.encode("job", "UTF-8")+"="+URLEncoder.encode(job, "UTF-8")+"&"+URLEncoder.encode("wage", "UTF-8")+"="+URLEncoder.encode(wage, "UTF-8")
                        +"&"+URLEncoder.encode("parttime", "UTF-8")+"="+URLEncoder.encode(parttime, "UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8")
                        +"&"+URLEncoder.encode("cid", "UTF-8")+"="+URLEncoder.encode(cid, "UTF-8");
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
        else if (method.equals("getEmployeeID"))
        {
            String name = params[1];
            String username = params[2];
            String email = params[3];
            String phone = params[4];
            String job = params[5];
            String wage = params[6];
            String parttime = params[7];
            String password = params[8];
            String cid = params[9];

            try
            {
                URL url = new URL(getEmployeeIDUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //encode data and write to php file on server
                String data = URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name, "UTF-8")
                        +"&"+URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")
                        +"&"+URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"+URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8")
                        +"&"+URLEncoder.encode("job", "UTF-8")+"="+URLEncoder.encode(job, "UTF-8")+"&"+URLEncoder.encode("wage", "UTF-8")+"="+URLEncoder.encode(wage, "UTF-8")
                        +"&"+URLEncoder.encode("parttime", "UTF-8")+"="+URLEncoder.encode(parttime, "UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8")
                        +"&"+URLEncoder.encode("cid", "UTF-8")+"="+URLEncoder.encode(cid, "UTF-8");
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
