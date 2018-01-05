package com.servustech.alex.servustech.utils.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.activities.mainScreenFlow.MainScreenActivity;

import java.util.HashMap;
import java.util.Map;

public class MessageReceiver extends FirebaseMessagingService {
    private static final String TAG = "MainScreenActivity";
    private static final String CHANNEL_ID = "my_channel_01";
    public static final String KEY_TO_DATA = "key_to_data";

    public static final String TITLE_KEY = "title_key";
    public static final String BODY_KEY = "body_key";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            displayNotification(remoteMessage.getData());
        }
    }



    private void displayNotification( Map<String, String> data) {
        String title = data.get("title");
        String body = data.get("body");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_announcement_white_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, MainScreenActivity.class);
        resultIntent.putExtra(MessageReceiver.KEY_TO_DATA, new HashMap<>(data));
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0/*Request code*/,
                resultIntent,
                PendingIntent.FLAG_ONE_SHOT);

        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }


}
