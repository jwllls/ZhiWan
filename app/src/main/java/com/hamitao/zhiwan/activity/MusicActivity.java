package com.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.base.BaseActivity;
import com.hamitao.zhiwan.mvp.music.MusicPresenter;
import com.hamitao.zhiwan.mvp.music.MusicView;
import com.hamitao.zhiwan.util.ToastUtil;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.wcy.lrcview.LrcView;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class MusicActivity extends BaseActivity implements MusicView {


    @BindView(R.id.lrcView)
    LrcView lrcView;   //歌词
    @BindView(R.id.seekbar)
    SeekBar seekBar;  //进度条
    @BindView(R.id.btn_lrc)
    Button btnLrc;  //显示歌词按钮
    @BindView(R.id.more)
    TextView more;


    private MusicPresenter presenter;


    String musicUrl = "http://res.webftp.bbs.hnol.net/zhangyu/music/cd63/03.mp3";
    String url = "http://up.xzdown.com/s/2018-01-21/1516504844.mp3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        presenter = new MusicPresenter(this, this);
        initView();
    }


    //获取歌词
    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }


    private void initView() {
        more.setVisibility(View.VISIBLE);

        more.setText("歌单");

        lrcView.loadLrc(getLrcText("chengdu.lrc"));

        presenter.setMusicData(musicUrl);

        presenter.setObject(seekBar, lrcView);


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


    @OnClick({R.id.btn_before, R.id.btn_play, R.id.btn_next, R.id.btn_lrc, R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_before:
                presenter.playBefore();
                break;
            case R.id.btn_play:
             /*   if (!mediaPlayer.isPlaying()) {
                    presenter.play();
                    handler.post(runnable);
                } else {
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                }*/
                presenter.playStart();
                break;
            case R.id.btn_next:
                presenter.playNext();
                break;
            case R.id.btn_lrc:
                lrcView.setVisibility(lrcView.isShown() ? View.GONE : View.VISIBLE);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        presenter.destoryPlaler();
        super.onDestroy();
    }


    @Override
    public void update(SeekBar seekBar) {
        lrcView.updateTime(seekBar.getProgress());
    }
}
