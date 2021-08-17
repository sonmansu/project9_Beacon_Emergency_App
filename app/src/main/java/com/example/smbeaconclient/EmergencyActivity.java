package com.example.smbeaconclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmergencyActivity extends AppCompatActivity {
    ImageView imageViewEvac;
    TextView tv1, tv2, tvFloorUser, tvFloorFire;
    private String TAG = "EmergencyActivityLog";
    String imageUri, strUserFloor, strFloorFire, people1f, people2f;
    int floorUser, floorFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        Log.d(getClass().getSimpleName(), "onCreate");

        // Show activity even in lock screen state
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        //init view
        imageViewEvac = findViewById(R.id.imageViewEvac);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tvFloorUser = findViewById(R.id.tvUserFloor);
        tvFloorFire = findViewById(R.id.tvFloorFire);

        //get intent
        Intent intent = getIntent();
        imageUri = intent.getStringExtra("imageUri");
        floorUser = intent.getIntExtra("floorUser", 0);
        floorFire = intent.getIntExtra("floorFire", 0);
        people1f = intent.getStringExtra("people1f");
        people2f = intent.getStringExtra("people2f");
        Log.d(TAG, "floorUser: " + floorUser);

        Glide.with(EmergencyActivity.this)
                .load(imageUri)
                .fallback(R.drawable.app_icon)
                .error(R.drawable.app_icon)
                .into(imageViewEvac);

        strUserFloor = "Evacuation route on the " + floorUser + " floor";
        strFloorFire = "Fire detected on the " + floorFire + " floor ! ";
        tvFloorUser.setText(strUserFloor);
        tv1.setText(people1f);
        tv2.setText(people2f);
        tvFloorFire.setText(strFloorFire);



        //        imageViewEvac.setImageBitmap(getBitmapfromUrl(imageUri));
//        imageViewEvac.setImageURI(Uri.parse(imageUri));

    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent called");
        super.onNewIntent(intent);
        setIntent(intent);

        imageUri = intent.getStringExtra("imageUri");
        floorUser = intent.getIntExtra("floorUser", 0);
        floorFire = intent.getIntExtra("floorFire", 0);
        people1f = intent.getStringExtra("people1f");
        people2f = intent.getStringExtra("people2f");


        Glide.with(EmergencyActivity.this)
                .load(imageUri)
                .fallback(R.drawable.app_icon)
                .error(R.drawable.app_icon)
                .into(imageViewEvac);

        strUserFloor = "Evacuation route on the " + floorUser + " floor";
        strFloorFire = "Fire detected on the " + floorFire + " floor ! ";
        tvFloorUser.setText(strUserFloor);
        tv1.setText(people1f);
        tv2.setText(people2f);
        tvFloorFire.setText(strFloorFire);


    }
}