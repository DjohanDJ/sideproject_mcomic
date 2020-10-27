package com.example.m_comic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.m_comic.R;

import java.util.Timer;
import java.util.TimerTask;

public class LandingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        final String currentUserId = sharedPreferences.getString("user_userId", "");
        assert currentUserId != null;

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!currentUserId.equals("")) {
                    startActivity(new Intent(LandingActivity.this, NavigationActivity.class));
                } else {
                    startActivity(new Intent(LandingActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 2000);


    }
}