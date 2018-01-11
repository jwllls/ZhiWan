package com.zhiwan.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.zhiwan.hamitao.zhiwan.R;
import com.zhiwan.hamitao.zhiwan.model.RecordFileModel;
import com.zhiwan.hamitao.zhiwan.mvp.music.RecordPresenter;
import com.zhiwan.hamitao.zhiwan.mvp.music.RecordView;
import com.zhiwan.hamitao.zhiwan.util.LogUtil;
import com.zhiwan.hamitao.zhiwan.util.ToastUtil;

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
    TextView btnStartRecord;
    @BindView(R.id.tv_reRecord)
    TextView tvReRecord;
    @BindView(R.id.tv_saveRecord)
    TextView tvSaveRecord;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.tv_recordTime)
    Chronometer tvRecordTime;


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
//                finish();
                presenter.setRecordList();
                break;
            case R.id.btn_startRecord:
                //开始录音
                startRecort();

                break;
            case R.id.tv_reRecord:
                //重新录音
                reRecord();

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

    }

    @Override
    public void startRecort() {
        //开始录音
        presenter.startRecord();
        //计时开始
        tvRecordTime.start();
        ToastUtil.showShort(this,"开始录音");
    }

    @Override
    public void savaRecort() {
        //停止录音
        presenter.stopRecord();
        //显示音频保存弹窗
        presenter.showReNameDialog();
        //计时停止
        tvRecordTime.stop();
        ToastUtil.showShort(this,"保存录音");
    }



    @Override
    public void reRecord() {
        presenter.reRecord();
        reset();
        tvRecordTime.start();
        ToastUtil.showShort(this,"重新录音");

    }


    @Override
    public void reset() {
        tvRecordTime.setBase(SystemClock.elapsedRealtime());
    }

    @Override
    public void getRecordList(List<RecordFileModel> list) {
        if (list!=null){
            ToastUtil.showShort(this,list.size()+"条数据====="+list.get(0).getRecordFile().getName());

            for (int i = 0; i < list.size(); i++) {
                LogUtil.e("file",list.get(i).getRecordFile().getName()+"===="+list.get(i).getRecordDate());
            }


        }
    }
}
