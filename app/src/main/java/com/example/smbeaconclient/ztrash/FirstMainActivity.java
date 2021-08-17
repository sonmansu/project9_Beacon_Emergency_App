//package com.example.smbeaconclient;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//
//public class FirstMainActivity extends AppCompatActivity {
//    private static final int OVERLAY_PERMISSION_REQ_CODE = 500;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_firstmain);
//
//        checkPermission(); //다른앱 위에 그리기 권한 있는지 확인
//
//
//        Button btnCheck = (Button) findViewById(R.id.btnCheck);
//        btnCheck.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//    }
//    /* start of 다른앱위에 그리기 권한 */
//    private void checkPermission() {
//        if (!Settings.canDrawOverlays(getApplicationContext())) showDialog(); //다른앱위에 그리기 권한이 없을때만 실행
//    }
//    void showDialog() {
//        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(FirstMainActivity.this)
//                .setTitle("다른 앱 위에 표시 권한을 설정해주십시오")
//                .setMessage("권한 설정하러 가기")
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        requestPermissions();
//                    }
//                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    @Override public void onClick(DialogInterface dialogInterface, int i) {
//                    }
//                });
//        AlertDialog msgDlg = msgBuilder.create();
//        msgDlg.show();
//    }
//    private void requestPermissions() {
//        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
//    }
//    /* end of 다른앱위에 그리기 권한 */
//
//}
