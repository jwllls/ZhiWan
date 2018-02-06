package com.hamitao.zhiwan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chenenyu.router.Router;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.mvp.start.StartPresenter;
import com.hamitao.zhiwan.mvp.start.StartView;
import com.zhiwan.hamitao.base_module.util.ToastUtil;


/**
 * Created by linjianwen on 2018/1/4.
 */

public class StartActivity extends AppCompatActivity implements StartView {

    private String TAG = StartActivity.class.getSimpleName();

    private StartPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();


    }

    private void init() {
        presenter = new StartPresenter(this);
        presenter.jumpActivity();
    }


    @Override
    public void onBegin() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(this, msg);
    }

    @Override
    public void naviMain() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Router.build("main").go(StartActivity.this);
                finish();
            }
        }, 1000);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void naviLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void autoLogin() {

    }
}
