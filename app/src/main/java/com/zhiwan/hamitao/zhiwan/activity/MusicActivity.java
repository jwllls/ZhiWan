package com.zhiwan.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.Button;

import com.zhiwan.hamitao.zhiwan.R;
import com.zhiwan.hamitao.zhiwan.base.BaseActivity;
import com.zhiwan.hamitao.zhiwan.mvp.music.MusicPresenter;
import com.zhiwan.hamitao.zhiwan.mvp.music.MusicView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.wcy.lrcview.LrcView;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class MusicActivity extends BaseActivity implements MusicView {


    @BindView(R.id.lrcView)
    LrcView lrcView;
    @BindView(R.id.progressbar)
    ContentLoadingProgressBar progressbar;
    @BindView(R.id.btn_lrc)
    Button btnLrc;
    private MusicPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        presenter = new MusicPresenter(this, this);
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


    @OnClick({R.id.btn_before, R.id.btn_play, R.id.btn_next, R.id.btn_lrc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_before:
                break;
            case R.id.btn_play:
                presenter.start();
                break;
            case R.id.btn_next:
                break;
            case R.id.btn_lrc:
                break;
        }
    }
}
