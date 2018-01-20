package com.hamitao.zhiwan.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

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
    SeekBar seekbar;  //进度条
    @BindView(R.id.btn_lrc)
    Button btnLrc;  //显示歌词按钮


    private MusicPresenter presenter;

    private MediaPlayer mediaPlayer;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        presenter = new MusicPresenter(this, this);
        initView();
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                long time = mediaPlayer.getCurrentPosition();
                lrcView.updateTime(time);
                seekbar.setProgress((int) time);
            }

            handler.postDelayed(this, 300);
        }
    };

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
        lrcView.loadLrc(getLrcText("chengdu.lrc"));


        lrcView.setOnPlayClickListener(new LrcView.OnPlayClickListener() {
            @Override
            public boolean onPlayClick(long time) {
                mediaPlayer.seekTo((int) time);
                if (!mediaPlayer.isPlaying()) {
                    presenter.play();
                    handler.post(runnable);
                }
                return true;
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekbar.setMax(mp.getDuration());
                seekbar.setProgress(0);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                lrcView.updateTime(0);
                seekbar.setProgress(0);
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekbar.getProgress());
                lrcView.updateTime(seekbar.getProgress());
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


    @OnClick({R.id.btn_before, R.id.btn_play, R.id.btn_next, R.id.btn_lrc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_before:
                break;
            case R.id.btn_play:
                if (!mediaPlayer.isPlaying()) {
                    presenter.play();
                    handler.post(runnable);
                } else {
                    presenter.pause();
                    handler.removeCallbacks(runnable);
                }
                break;
            case R.id.btn_next:
                break;
            case R.id.btn_lrc:
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
    public void getMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

}
