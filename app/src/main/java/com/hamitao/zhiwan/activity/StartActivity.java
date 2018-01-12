package com.hamitao.zhiwan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hamitao.zhiwan.R;


/**
 * Created by linjianwen on 2018/1/4.
 */

public class StartActivity extends AppCompatActivity {


    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);


        jumpActivity();

    }

    private void jumpActivity() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        }, 1000);

    }
}
