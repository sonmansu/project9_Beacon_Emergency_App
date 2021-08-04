package com.example.smbeaconclient.firebase;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static String TAG = "MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

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

        // If you want to send messages to this application instance or manage this apps subscriptions on the server side,
        // send the FCM registration token to your app server.
//        sendRegistrationToServer(token); //파베에 토큰 적음


        // write token document on the db //파베 db에다 이 놈의 토큰 문서를 저장함, token field 만 채움
        Workplace workplace = new Workplace(token, false);
        MyFirestore.getWorkplaceColRef().document(token).set(workplace);
    }
}
