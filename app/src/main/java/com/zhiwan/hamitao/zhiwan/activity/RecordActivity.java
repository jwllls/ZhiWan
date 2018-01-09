package com.zhiwan.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zhiwan.hamitao.zhiwan.R;
import com.zhiwan.hamitao.zhiwan.mvp.music.RecordPresenter;
import com.zhiwan.hamitao.zhiwan.mvp.music.RecordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 录音界面
 */
public class RecordActivity extends AppCompatActivity implements RecordView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btn_startRecord)
    TextView btnStartRecord;
    @BindView(R.id.tv_reRecord)
    TextView tvReRecord;
    @BindView(R.id.tv_saveRecord)
    TextView tvSaveRecord;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.tv_recordTime)
    TextView tvRecordTime;


    private RecordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        presenter = new RecordPresenter(this,this);
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("录音室");

    }

    @OnClick({R.id.back, R.id.btn_startRecord, R.id.tv_reRecord, R.id.tv_saveRecord, R.id.tv_tips})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_startRecord:
                //开始录音
                break;
            case R.id.tv_reRecord:
                //重新录音
                break;
            case R.id.tv_saveRecord:
                //保存录音
                break;
            case R.id.tv_tips:
                //提示
                break;
        }
    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onMessageShow(String msg) {

    }

    @Override
    public void startRecort() {

    }

    @Override
    public void savaRecort() {

    }

    @Override
    public void reRecord() {

    }
}
