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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class FirstMainActivity extends AppCompatActivity {
    String TAG = "FirstMainActivity";
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;

    private static final int PERMISSIONS_REQUEST_CODE = 22;
    private PermissionSupport permission;

    @RequiresApi(api = Build.VERSION_CODES.Q)
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

//        permissionCheck();
        permissionCheck2();

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
    private static final String[] FOREGROUND_LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,       // GPS
            Manifest.permission.ACCESS_COARSE_LOCATION,     // GPS approximate location
    };
    @RequiresApi(api = Build.VERSION_CODES.Q) //29
    private static final String[] BACKGROUND_LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
    };
    /** start; **/
@RequiresApi(api = Build.VERSION_CODES.Q)
public boolean chkPermission() {
    // 위험 권한을 모두 승인했는지 여부
    boolean mPermissionsGranted = false;
//    String[] mRequiredPermissions = new String[1];
    String[] mRequiredPermissions = {
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.READ_SMS,
    };

    // 승인 받기 위한 권한 목록
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //30
        mRequiredPermissions[0] = Manifest.permission.READ_PHONE_NUMBERS;
    }else{
        mRequiredPermissions[0] = Manifest.permission.READ_PRECISE_PHONE_STATE;
    }

    // 필수 권한을 가지고 있는지 확인한다.
    mPermissionsGranted = hasPermissions(mRequiredPermissions);

    // 필수 권한 중에 한 개라도 없는 경우
    if (!mPermissionsGranted) {
        // 권한을 요청한다.
//        ActivityCompat.requestPermissions(FirstMainActivity.this, mRequiredPermissions, PERMISSIONS_REQUEST_CODE);

        ActivityCompat.requestPermissions(FirstMainActivity.this, FOREGROUND_LOCATION_PERMISSIONS, 1);
// Check the first statement grant needed permissions, and then run the second line:
        ActivityCompat.requestPermissions(FirstMainActivity.this, BACKGROUND_LOCATION_PERMISSIONS, 1);
    }

    return mPermissionsGranted;
}

    public boolean hasPermissions(String[] permissions) {
        // 필수 권한을 가지고 있는지 확인한다.
        for (String permission : permissions) {
            if (checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            // 권한을 모두 승인했는지 여부
            boolean chkFlag = false;
            // 승인한 권한은 0 값, 승인 안한 권한은 -1을 값으로 가진다.
            for (int g : grantResults) {
                if (g == -1) {
                    chkFlag = true;
                    break;
                }
            }

            // 권한 중 한 개라도 승인 안 한 경우
            if (chkFlag){
                chkPermission();
            }
        }
    }
    /** end**/


    private void permissionCheck2() {
        if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { //안드로이드 29이상부턴 직접 가서 설정해줘야하나본데
                if (this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)  != PackageManager.PERMISSION_GRANTED) {
                    if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("This app needs background location access");
                        builder.setMessage("Please grant location access so this app can detect beacons in the background.");
                        builder.setPositiveButton(android.R.string.ok, null);
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @TargetApi(23)
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                        PERMISSION_REQUEST_BACKGROUND_LOCATION);
                            }
                        });
                        builder.show();
                    }  else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Functionality limited");
                        builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons in the background.  Please go to Settings -> Applications -> Permissions and grant background location access to this app.");
                        builder.setPositiveButton(android.R.string.ok, null);
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        });
                        builder.show();
                    }
                }
            }
        } else {
            if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Functionality limited");
                builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.  Please go to Settings -> Applications -> Permissions and grant location access to this app.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
                builder.show();
            }
        }
    }

    private void permissionCheck() {
        // 방금 전 만들었던 클래스 객체 생성
        permission = new PermissionSupport(this, this);

        // 권한 체크한 후에 리턴이 false로 들어온다면
        if (!permission.checkPermission()){
            // 권한 요청을 해줍니다.
            permission.requestPermission();
        }
    }
    //    // Request Permission에 대한 결과 값을 받아올 수 있습니다.
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        // 여기서도 리턴이 false로 들어온다면 (사용자가 권한 허용을 거부하였다면)
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (!permission.permissionResult(requestCode, permissions, grantResults)) {
//            // 저의 경우는 여기서 다시 Permission 요청을 걸었습니다.
//            permission.requestPermission();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_REQUEST_FINE_LOCATION: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d(TAG, "fine location permission granted");
//                } else {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("Functionality limited");
//                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.");
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                        }
//
//                    });
//                    builder.show();
//                }
//                return;
//            } case PERMISSION_REQUEST_BACKGROUND_LOCATION: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d(TAG, "background location permission granted");
//                } else {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("Functionality limited");
//                    builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons when in the background.");
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                        }
//                    });
//                    builder.show();
//                }
//                return;
//            }
//        }
//    }
//    /** end of checking permission **/


}
