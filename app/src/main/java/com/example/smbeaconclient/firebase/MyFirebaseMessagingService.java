package com.example.smbeaconclient.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.smbeaconclient.EmergencyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import kotlin.text.Regex;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static String TAG = "MyFirebaseMessagingService";

    public void showDataMessage(String msgTitle, String msgContent) {
        Log.i("### data msgTitle : ", msgTitle);
        Log.i("### data msgContent : ", msgContent);
        String toastText = String.format("[Data 메시지] title: %s => content: %s", msgTitle, msgContent);
        Looper.prepare();
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
        Looper.loop();
    }

    /**
     * 수신받은 메시지를 Toast로 보여줌
     * @param msgTitle
     * @param msgContent
     */
    public void showNotificationMessage(String msgTitle, String msgContent) {
        Log.i("### noti msgTitle : ", msgTitle);
        Log.i("### noti msgContent : ", msgContent);
        String toastText = String.format("[Notification 메시지] title: %s => content: %s", msgTitle, msgContent);
        Looper.prepare();
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
        Looper.loop();
    }

    String myUrgentChannel;
    @Override
    public void onMessageReceived(RemoteMessage msg) {
//        Log.d(TAG, "onMessageReceived called");
//        Log.i("### msg : ", msg.toString());
//        if (msg.getData().isEmpty()) {
//            showNotificationMessage(msg.getNotification().getTitle(), msg.getNotification().getBody());  // Notification으로 받을 때
//        } else {
//            showDataMessage(msg.getData().get("title"), msg.getData().get("content"));  // Data로 받을 때
//        }
//
//        Intent i = new Intent(getApplicationContext(), EmergencyActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);

        Log.d(TAG, "From: " + msg.getFrom());

        myUrgentChannel = this.getPackageName();// init value

        String body = msg.getNotification().getBody();
//        var body = msg.GetNotification().Body;
        Log.d(TAG, "Notification Message Body: " + body);

        SendNotifications(msg);
    }

    public void SendNotifications(RemoteMessage message) { try { NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        var seed = Convert.ToInt32(Regex.Match(Guid.NewGuid().ToString(), @"\d+").Value);
        int id = new Random(seed).Next(000000000, 999999999);

        var push = new Intent();

        var fullScreenPendingIntent = PendingIntent.GetActivity(this, 0,
                push, PendingIntentFlags.CancelCurrent);


        NotificationCompat.Builder notification;

        if (Build.VERSION.SdkInt >= BuildVersionCodes.O)
        {
            var chan1 = new NotificationChannel(myUrgentChannel,
                    new Java.Lang.String("Primary"), NotificationImportance.High);
            chan1.LightColor = Color.Green;

            manager.CreateNotificationChannel(chan1);

            notification = new NotificationCompat.Builder(this, myUrgentChannel).SetOngoing(true);
        }
        else
        {
            notification = new NotificationCompat.Builder(this);
        }

        notification.SetOngoing(true);
        notification.SetAutoCancel(true);

        var clickaction = message.GetNotification().ClickAction;
        notification.SetContentIntent(fullScreenPendingIntent)
                .SetContentTitle(message.GetNotification().Title)
                .SetContentText(message.GetNotification().Body)
                .SetLargeIcon(BitmapFactory.DecodeResource(Resources, Resource.Drawable.img123))
                .SetSmallIcon(Resource.Drawable.img123)
                .SetStyle((new NotificationCompat.BigTextStyle()))
                .SetPriority(NotificationCompat.PriorityHigh)
                .SetColor(0x9c6114)
                .SetAutoCancel(true)g
                .SetOngoing(true);


        manager.Notify(id, notification.Build());

        // start a new app here
        Intent i = PackageManager.("com.companyname.formapp1");
        StartActivity(i);

    }
    catch (System.Exception ex)
    {
        System.Diagnostics.Debug.WriteLine("ecsption = "+ ex.StackTrace);
    }
    }fd

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(String token) { // 새 토큰 발급될 때 호출됨
        Log.d(TAG, "Refreshed token: " + token);

        //Save token on the phone internal memory  //토큰값을 휴대폰 내부 저장소에 저장
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
        editor.putString("token",token); // key,value 형식으로 저장
        editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.

        // write token document on the db //파베 db에다 이 놈의 토큰 문서를 저장함, token field 만 채움
        Workplace workplace = new Workplace(token, false);
        MyFirestore.getWorkplaceColRef().document(token).set(workplace);
    }
}
