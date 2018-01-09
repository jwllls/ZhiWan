package com.zhiwan.hamitao.zhiwan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhiwan.hamitao.zhiwan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的录音
 */
public class MyRecordActivity extends AppCompatActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.list_recyclerView)
    RecyclerView listRecyclerView;
    @BindView(R.id.list_refresh)
    BGARefreshLayout listRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        title.setText("我的录音");
        title.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.back, R.id.tv_record, R.id.tv_manager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_record:
                //进入录音界面
                startActivity(new Intent(this,RecordActivity.class));
                break;
            case R.id.tv_manager:
                //管理录音文件
                break;
        }
    }
}
