package com.zhiwan.hamitao.im_module.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhiwan.hamitao.im_module.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.btn_click)
    Button btnClick;
    @BindView(R.id.tv_chat)
    TextView tvChat;

    private MainActivity activity;
    private MyCallback myCallback;

    public interface MyCallback{
        void onSuccess(String str);
    }

    public void setMyCallback(MyCallback myCallback){
        this.myCallback = myCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        activity = new MainActivity();

    }

    @OnClick({R.id.btn_click, R.id.tv_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_click:

                if (myCallback!=null){
                    myCallback.onSuccess("success");
                }

                finish();
                break;
            case R.id.tv_chat:
                break;
        }
    }
}
