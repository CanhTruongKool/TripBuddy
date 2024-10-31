package com.example.tripbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_3);

        Button splashNext = findViewById(R.id.getStartedButton);
        splashNext.setOnClickListener(v -> {
            // Chuyển đổi sang SecondActivity
            Intent intent = new Intent(SplashActivity3.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}