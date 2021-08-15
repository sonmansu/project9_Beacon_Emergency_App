package com.example.smbeaconclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class FirstMainActivity extends AppCompatActivity {
    String TAG = "FirstMainActivitylog";

    private final int PERMISSION_CODE_MULTIPLES = 3;
    private static final int PERMISSION_CODE_BACKGROUND_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstmain);

        Button btnCheck = (Button) findViewById(R.id.btnCheck);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        if (chkPermission()) {
            // 휴대폰 정보는 TelephonyManager 를 이용
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            // READ_PHONE_NUMBERS 또는 READ_PHONE_STATE 권한을 허가 받았는지 확인
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Log.d(TAG, "음성통화 상태 : [ getCallState ] >>> " + tm.getCallState());
            Log.d(TAG, "데이터통신 상태 : [ getDataState ] >>> " + tm.getDataState());
            Log.d(TAG, "전화번호 : [ getLine1Number ] >>> " + tm.getLine1Number());
            Log.d(TAG, "통신사 ISO 국가코드 : [ getNetworkCountryIso ] >>> "+tm.getNetworkCountryIso());
            Log.d(TAG, "통신사 ISO 국가코드 : [ getSimCountryIso ] >>> "+tm.getSimCountryIso());
            Log.d(TAG, "망사업자 MCC+MNC : [ getNetworkOperator ] >>> "+tm.getNetworkOperator());
            Log.d(TAG, "망사업자 MCC+MNC : [ getSimOperator ] >>> "+tm.getSimOperator());
            Log.d(TAG, "망사업자명 : [ getNetworkOperatorName ] >>> "+tm.getNetworkOperatorName());
            Log.d(TAG, "망사업자명 : [ getSimOperatorName ] >>> "+tm.getSimOperatorName());
            Log.d(TAG, "SIM 카드 상태 : [ getSimState ] >>> "+tm.getSimState());

            // 유니크한 단말 번호 >>> Android ID 사용
            @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
            Log.d(TAG, "Android_ID >>> "+android_id);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.Q) //29
    private static final String[] BACKGROUND_LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
    };

    public boolean chkPermission() {
        boolean mPermissionsGranted = false; //Whether you have approved all risk permissions
        String[] mRequiredPermissions = new String[3];

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //30
            mRequiredPermissions[0] = Manifest.permission.READ_PHONE_NUMBERS;
        }else{
            mRequiredPermissions[0] = Manifest.permission.READ_PHONE_STATE;
        }
        mRequiredPermissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION; //for android 12 needed, GPS
        mRequiredPermissions[2] = Manifest.permission.ACCESS_FINE_LOCATION;   //GPS approximate location


        //Check that user have the necessary permissions
        mPermissionsGranted = hasPermissions(mRequiredPermissions);


        // If don't have any of the required permissions, ask for the permissions.
        if (!mPermissionsGranted) {
            ActivityCompat.requestPermissions(FirstMainActivity.this, mRequiredPermissions, PERMISSION_CODE_MULTIPLES);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                backgroundPermissionDialog();
            }
        }

        return mPermissionsGranted;
    }

    //Ensure that you have the necessary permissions
    public boolean hasPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED ) {
                return false;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)  != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // Starting with Android API 30, backgroundPermission must be set up directly
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void requestBackgroundPermission() {
        ActivityCompat.requestPermissions(FirstMainActivity.this, BACKGROUND_LOCATION_PERMISSIONS, PERMISSION_CODE_BACKGROUND_LOCATION);
    }
    private void backgroundPermissionDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("This app needs background location access");
        builder.setMessage("Please set it to 'always allow' so this app can detect beacons in the background.");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestBackgroundPermission();
            }
        });

        builder.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CODE_MULTIPLES && (grantResults.length > 0) ||
           requestCode == PERMISSION_CODE_BACKGROUND_LOCATION && (grantResults.length > 0)  ){


            for(int i=0; i < grantResults.length ; i++){
                // If grantResults is 0, it means you allowed it, -1 means you denied it
                if(grantResults[i] == -1){
                    Toast.makeText(FirstMainActivity.this, "This app will not be available if you do not accept all of the permissions.", Toast.LENGTH_SHORT).show();
                    chkPermission();
                }
            }
        } else {
            Toast.makeText(FirstMainActivity.this, "This app will not be available if you do not accept all of the permissions.", Toast.LENGTH_SHORT).show();
            chkPermission();
        }
    }

}
