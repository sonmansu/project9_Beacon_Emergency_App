package com.example.smbeaconclient.ztrash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.smbeaconclient.EmergencyActivity;

public class FcmBroadcastReceiver extends BroadcastReceiver {
    String TAG = "FcmReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("example.test.broadcast")) {
            Toast.makeText(context, "Customize broadcast onReceive", Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "sendbroadcastonReceive");
        Intent i = new Intent(context, EmergencyActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //는 안되더라
//        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);


        context.startActivity(i);

    }
}
