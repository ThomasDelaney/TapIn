package com.example.thomas.tapin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * Created by Thomas on 24/03/2017.
 */

public class EmployerSessionManager
{
    public EmployerSessionManager(Context context)
    {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    private SharedPreferences pref;
    private Editor editor;
    private Context context;

    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "New User";

    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_USER = "username";
    public static final String KEY_CID = "cid";

    public void createLoginSession(String name, String username, String cid)
    {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_USER, username);
        editor.putString(KEY_CID, cid);

        editor.commit();
    }

    //gets users details and returns in form of HashMap
    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_USER, pref.getString(KEY_USER, null));
        user.put(KEY_CID, pref.getString(KEY_CID, null));

        return user;
    }

    public boolean isLoggedIn()
    {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean checkLogin()
    {
        if(!isLoggedIn())
        {
            Intent i = new Intent(context, EmployerLogin.class);

            i.putExtra("employerLoginType", 0);
            //create new task -- clearing flags
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //bring to login page
            context.startActivity(i);
            return true;
        }
        return false;
    }

    public void logOut()
    {
        //clear shared preferences
        editor.clear();
        editor.commit();

        //user is now logged out, so redirect to login page
        Intent i = new Intent(context, MainActivity.class);

        //clear all previous activities and indicate a new one will be started
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //start login page
        context.startActivity(i);
    }
}
