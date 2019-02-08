package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ourproject.mohankumardhakal.agroproject.R;

public class SplashActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.splash_screen);
        super.onCreate(savedInstanceState);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            }
        });
        thread.start();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if (msg.what == 100) {
                    startActivity(new Intent(SplashActivity.this,
                            HomeActivity.class));
                }
                return false;
            }
        });
        for (int i = 0; i < 200; i++) {
        }
        try {
            thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(100);
    }
}
