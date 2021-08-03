package com.example.smbeaconclient.firebase;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.smbeaconclient.MainActivity;
import com.example.smbeaconclient.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static String TAG = "MyFirebaseMessagingService";
    private static String token;

    public interface FirestoreResults {
        public void onResultGet(String str);
    }
    public static void getToken(final FirestoreResults results) {
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        token = s;
                        Log.d(TAG, "토큰1:"+token);
                        results.onResultGet(token);
                    }
                });
//        Log.d(TAG, "토큰2:"+token);
//        return token;

    }
//    public static String getToken() {
////        FirebaseMessaging.getInstance().getToken()
////                .addOnCompleteListener(new OnCompleteListener<String>() {
////                    @Override
////                    public void onComplete(@NonNull Task<String> task) {
////                        if (!task.isSuccessful()) {
////                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
////                            return;
////                        }
////                        // Get new FCM registration token
////                        token = task.getResult();
////                        // Log and toast
////                    }
////                });
//        FirebaseMessaging.getInstance().getToken()
//                .addOnSuccessListener(new OnSuccessListener<String>() {
//                    @Override
//                    public void onSuccess(String s) {
//                        token = s;
//                        Log.d(TAG, "토큰1:"+token);
//
//                    }
//                });
//        Log.d(TAG, "토큰2:"+token);
//        return token;
//
//    }

    // 새 토큰 발급될 때 호출됨
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
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        //Save token on the phone memory (?) //토큰값을 휴대폰 메모리에 저장
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
        editor.putString("token",token); // key,value 형식으로 저장
        editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.
//        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show(); //error occurs

        // If you want to send messages to this application instance or manage this apps subscriptions on the server side,
        // send the FCM registration token to your app server.
//        sendRegistrationToServer(token); //파베에 토큰 적음

        //add Document named token in Workers Collection// Workers콜렉션에 token명을 가진 Doc 추가
        MyFirestore.getWorkersColInstance().document(token).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "document exists, our worker! " + document.getData());
                    } else {
                        Log.d(TAG, "No such document,stranger");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
}
