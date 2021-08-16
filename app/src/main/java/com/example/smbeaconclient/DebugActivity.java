package com.example.smbeaconclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.altbeacon.beacon.BeaconManager;

public class DebugActivity extends AppCompatActivity {
    protected static final String TAG = "DebugActivity";
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;

    TextView textViewFloor, textViewRanging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        Log.d(TAG, "onCreate called");


        textViewFloor = findViewById(R.id.textViewFloor);
        textViewRanging = findViewById(R.id.textViewRanging);

        verifyBluetooth();

        /** start; temporary; for checking token value;  **/
        Button logTokenButton = findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                    return;
                                }
                                // Get new FCM registration token
                                String token = task.getResult();
                                // Log and toast
                                String msg = getString(R.string.msg_token_fmt, token);
                                Log.d(TAG, msg);
                                Toast.makeText(DebugActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
//                end of get token
            }
        });
        /** end; for temporary  **/
    }
    @Override
    public void onResume() {
        super.onResume();
        BeaconReferenceApplication application = ((BeaconReferenceApplication) this.getApplicationContext());
        application.setMonitoringActivity(this);
        updateLog(application.getLog());
    }

    @Override
    public void onPause() {
        super.onPause();
        ((BeaconReferenceApplication) this.getApplicationContext()).setMonitoringActivity(null);
    }

    private void verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //finish();
                        //System.exit(0);
                    }
                });
                builder.show();
            }
        } catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //finish();
                    //System.exit(0);
                }
            });
            builder.show();
        }
    }

    public void updateLog(final String log) {
        runOnUiThread(new Runnable() {
            public void run() {
                EditText editText = (EditText)DebugActivity.this.findViewById(R.id.textViewMonitoring);
                editText.setText(log);
            }
        });
    }

}

/** END OF CODE **/

//    public void onRangingClicked(View view) {
//        Intent myIntent = new Intent(this, RangingActivity.class);
//        this.startActivity(myIntent);
//    }
//    public void onEnableClicked(View view) {
//        BeaconReferenceApplication application = ((BeaconReferenceApplication) this.getApplicationContext());
//        if (BeaconManager.getInstanceForApplication(this).getMonitoredRegions().size() > 0) {
//            application.disableMonitoring();
//            ((Button)findViewById(R.id.enableButton)).setText("Re-Enable Monitoring");
//        }
//        else {
//            ((Button)findViewById(R.id.enableButton)).setText("Disable Monitoring");
//            application.enableMonitoring();
//        }
//
//    }