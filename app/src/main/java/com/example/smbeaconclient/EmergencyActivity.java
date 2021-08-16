package com.example.smbeaconclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
    TextView tv1, tv2, tvUserFloor;
    private String TAG = "EmergencyActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        Log.d(getClass().getSimpleName(), "onCreate");

        // 잠금 화면 위로 activity 띄워줌
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);



        Intent intent = getIntent();
//        String imageUri = "";// = intent.getStringExtra("imageUri");
        String imageUri = intent.getStringExtra("imageUri");
        int floor = intent.getIntExtra("floor", 0);
//        Log.d(TAG, "imageUri: " + imageUri);
        Log.d(TAG, "floor: " + floor);

        imageViewEvac = findViewById(R.id.imageViewEvac);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tvUserFloor = findViewById(R.id.tvUserFloor);
//        imageViewEvac.setImageBitmap(getBitmapfromUrl(imageUri));
//        imageViewEvac.setImageURI(Uri.parse(imageUri));

        Glide.with(EmergencyActivity.this)
                .load(imageUri)
                .fallback(R.drawable.app_icon)
                .error(R.drawable.app_icon)
                .into(imageViewEvac);

        String strUserFloor = "Evacuation route on the " + floor + " floor";
        tvUserFloor.setText(strUserFloor);


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
        String imageUri = intent.getStringExtra("imageUri");
        int floor = intent.getIntExtra("floor", 0);
        String strUserFloor = "Evacuation route on the " + floor + " floor";
        tvUserFloor.setText(strUserFloor);


    }
}