package com.hamitao.zhiwan.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
 * 播放器页面
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

    private MediaPlayer mediaPlayer;
    private MusicPresenter presenter;

    private Handler handler = new Handler();


    String musicUrl = "http://res.webftp.bbs.hnol.net/zhangyu/music/cd63/03.mp3";
    String url = "http://up.xzdown.com/s/2018-01-21/1516504844.mp3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        presenter = new MusicPresenter(this, this);
        mediaPlayer = new MediaPlayer();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                long time = mediaPlayer.getCurrentPosition();
                lrcView.updateTime(time);
                seekBar.setProgress((int) time);
            }
            handler.postDelayed(this, 300);
        }
    };


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


    //设置播放路径
    public void setMusicData(String music) {
        try {
            mediaPlayer.setDataSource(music);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        more.setVisibility(View.VISIBLE);

        more.setText("歌单");

        lrcView.loadLrc(getLrcText("chengdu.lrc"));

        setMusicData(musicUrl);

        lrcView.setOnPlayClickListener(new LrcView.OnPlayClickListener() {
            @Override
            public boolean onPlayClick(long time) {
                mediaPlayer.seekTo((int) time);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    handler.post(runnable);
                }
                return true;
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mp.getDuration());
                seekBar.setProgress(0);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                lrcView.updateTime(0);
                seekBar.setProgress(0);
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                lrcView.updateTime(seekBar.getProgress());
            }
        });

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
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    handler.post(runnable);
                } else {
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                }
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
        handler.removeCallbacks(runnable);
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }


    @Override
    public void update(SeekBar seekBar) {
        lrcView.updateTime(seekBar.getProgress());
    }
}
