package com.example.thomas.tapin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Thomas on 23/04/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        if (remoteMessage.getData().size() > 0)
        {
            try
            {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            }
            catch (Exception e)
            {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void sendPushNotification(JSONObject json)
    {
        try
        {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.tapinicon)
                            .setContentTitle(message)
                            .setContentText(title)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

            //creating an intent for the notification
            Intent intent = new Intent(this, EmployerMain.class);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);

            //create random notification ID
            int mNotificationId = new Random().nextInt(500);

            NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }
        catch (JSONException e)
        {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        }
        catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}