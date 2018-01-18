package com.zhiwan.hamitao.im_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhiwan.hamitao.im_module.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhiwan.hamitao.im_module.activity.ChatActivity.*;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_go)
    Button btnGo;
    @BindView(R.id.tv_main)
    TextView tvMain;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_go, R.id.tv_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_go:


                ChatActivity chatActivity = new ChatActivity();
                chatActivity.setMyCallback(new MyCallback() {
                    @Override
                    public void onSuccess(String str) {
                        tvMain.setText(str);
                    }
                });
                startActivity(new Intent(this,ChatActivity.class));



                break;
            case R.id.tv_main:
                break;
        }
    }
}
