package com.hamitao.zhiwan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.model.RecordModel;
import com.hamitao.zhiwan.mvp.record.RecordPresenter;
import com.hamitao.zhiwan.mvp.record.RecordView;
import com.zhiwan.hamitao.base_module.Constant;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import java.io.Serializable;
import java.util.List;

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
    TextView btnStartRecord;        //开始录音
    @BindView(R.id.tv_reRecord)
    TextView tvReRecord;            //重新录音
    @BindView(R.id.tv_saveRecord)
    TextView tvSaveRecord;          //保存录音
    @BindView(R.id.tv_tips)
    TextView tvTips;                //提示使用耳机录音
    @BindView(R.id.tv_recordTime)
    Chronometer tvRecordTime;       //录音时长

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
        presenter = new RecordPresenter(this, this);
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
                startRecort();
                break;
            case R.id.tv_reRecord:
                //重新录音
                presenter.showRerecordDialog();
                break;
            case R.id.tv_saveRecord:
                //保存录音
                savaRecort();
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
        ToastUtil.showShort(this, msg);
    }

    @Override
    public void startRecort() {
        presenter.startRecord();//开始录音
        tvRecordTime.start();//计时开始
    }

    @Override
    public void savaRecort() {
        presenter.stopRecord(); //停止录音
        presenter.showReNameDialog();//显示音频保存弹窗
        tvRecordTime.stop(); //计时停止
    }


    @Override
    public void reRecord() {
        presenter.reRecord();
        reset();
        tvRecordTime.start();
        ToastUtil.showShort(this, "重新录音");
    }


    @Override
    public void reset() {
        tvRecordTime.setBase(SystemClock.elapsedRealtime());
    }

    @Override
    public void getRecordList(List<RecordModel> list) {
        //这里将数据回传到我的录音
        Intent it = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("recordList", (Serializable) list);
        it.putExtra("recordList", bundle);
        setResult(Constant.RECORD_CODE, it);

    }

    @Override
    public void showButton() {
        tvReRecord.setVisibility(View.VISIBLE);
        tvSaveRecord.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButton() {
        tvReRecord.setVisibility(View.GONE);
        tvSaveRecord.setVisibility(View.GONE);
    }


}
