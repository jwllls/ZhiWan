package com.zhiwan.hamitao.im_module.chat.adapter.message;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.imsdk.TIMMessage;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.model.UserModel;
import com.zhiwan.hamitao.base_module.model.chat.ChatSo;
import com.zhiwan.hamitao.base_module.model.chat.ChatSoundDo;
import com.zhiwan.hamitao.im_module.R;
import com.zhiwan.hamitao.base_module.util.GsonUtil;
import com.zhiwan.hamitao.base_module.util.MusicPlayer;


class VoiceMessageItem extends BaseMessageItem implements OnClickListener {

    private LinearLayout chat_voice_content_layout;
    private TextView duration;
    private TextView mduration;
    private ImageView voice_photo;

    private MusicPlayer mp = null;
    private AnimationDrawable frameAnimatio;

    VoiceMessageItem(TIMMessage msg, ChatSo chat, BaseActivity activity, UserModel friend, MusicPlayer mp) {
        super(msg, chat, activity, friend);
        this.mp = mp;
    }

    @Override
    protected void onInitViews() {
        View view = mInflater.inflate(R.layout.chat_message_voice, null);
        chat_voice_content_layout = (LinearLayout) view
                .findViewById(R.id.chat_voice_content_layout);
        duration = (TextView) view.findViewById(R.id.duration);
        mduration = (TextView) view.findViewById(R.id.mduration);
        voice_photo = (ImageView) view.findViewById(R.id.voice_photo);
        layout_content.addView(view);

    }

    @Override
    protected void onFillMessage() {
        ChatSoundDo sound = GsonUtil.GsonToBean(chat.getContent(), ChatSoundDo.class);
        if (sound == null) {
            return;
        }
        long durationTime = sound.getDuration();
        if (!msg.isSelf()) {
            voice_photo.setBackgroundResource(R.drawable.chat_anim_left_voice);
            mduration.setVisibility(View.VISIBLE);
            mduration.setText(durationTime / 1000 + "'");
            duration.setVisibility(View.GONE);
        } else {
            voice_photo.setBackgroundResource(R.drawable.chat_anim_right_voice);
            mduration.setVisibility(View.GONE);
            duration.setVisibility(View.VISIBLE);
            duration.setText(durationTime / 1000 + "'");
        }
        frameAnimatio = (AnimationDrawable) voice_photo.getBackground();
        if (mp.isPlaying() && mp.getUrl().equals(sound.getUrl())) {
            frameAnimatio.start();
        }
        chat_voice_content_layout.setTag(R.id.image_tag, sound);
        chat_voice_content_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
                //经测试正常，如果以后异常，在Musicerplayer搞个frameAnimation的get，set方法
                frameAnimatio.stop();
                frameAnimatio.selectDrawable(0);
            }
            // TODO 播放语音
            Log.d("TAG", "点击了语音=================");
            ChatSoundDo sound = (ChatSoundDo) v.getTag(R.id.image_tag);
            Log.d("TAG", "sound.getPath===" + sound.getUrl());
            playAudio(frameAnimatio, sound.getUrl());
        }


    }

    //播放语音
    private void playAudio(final AnimationDrawable frameAnimatio, String soundUrl) {
        mp.playUrl(soundUrl, frameAnimatio);
        mp.setOnMusicListener(new MusicPlayer.OnMusicListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }

            @Override
            public void onCompletion(MediaPlayer mp) {

            }

            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }

            @Override
            public void stoped() {

            }
        });
    }


}
