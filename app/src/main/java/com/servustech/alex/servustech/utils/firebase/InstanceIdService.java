package com.servustech.alex.servustech.utils.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Use to create and save a token first time it is used
 * The token is needed to identify this phone when sending a notification
 *
 * After it is created, it is saved in a SharedPreferences file name "token_safe" under the key "token"
 */
public class InstanceIdService extends FirebaseInstanceIdService {
    public static final String KEY_TO_TOKEN = "token";
    private static final String TAG = "InstanceIdService";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        /*Save the token in the SharedPreferences file */
        InstanceIdService.saveToken(token,this);
    }

     private static void saveToken(String token, Context context) {
         SharedPreferences.Editor editor = context.getSharedPreferences("token_safe", Context.MODE_PRIVATE).edit();
         editor.putString(KEY_TO_TOKEN, token);
         editor.apply();
    }
}
