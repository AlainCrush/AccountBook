package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        getWindow().addFlags(WindowManager.LayoutParams.FLAGS_CHANGED);//隐藏状态栏
        Thread thread =new Thread(){
            public void run(){
                try {
                    sleep(3000);
                    Intent intent = new Intent(InitialActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}