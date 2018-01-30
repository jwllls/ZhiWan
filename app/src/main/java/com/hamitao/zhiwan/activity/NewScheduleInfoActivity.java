package com.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.hamitao.zhiwan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewScheduleInfoActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule_info);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("新课表信息");

    }

    @OnClick({R.id.back, R.id.btn_create, R.id.btn_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_create:
                new AlertView("提示", "课表生成成功", null, new String[]{"确定"}, null, this,
                        AlertView.Style.Alert, null).show();
                break;
            case R.id.btn_share:
                break;
        }
    }
}
