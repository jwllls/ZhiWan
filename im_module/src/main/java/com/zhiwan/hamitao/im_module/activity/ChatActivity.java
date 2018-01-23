package com.zhiwan.hamitao.im_module.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.ext.message.TIMMessageDraft;
import com.zhiwan.hamitao.im_module.mvp.presenter.ChatView;

import java.util.List;

/**
 * Created by linjianwen on 2018/1/23.
 */

public  class ChatActivity extends AppCompatActivity implements ChatView {





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void showMessage(TIMMessage message) {

    }

    @Override
    public void showMessage(List<TIMMessage> messages, boolean isLoadMore) {

    }

    @Override
    public void clearAllMessage() {

    }

    @Override
    public void onSendMessageSuccess(TIMMessage message) {

    }

    @Override
    public void onSendMessageFail(int code, String desc, TIMMessage message) {

    }

    @Override
    public void showDraft(TIMMessageDraft draft) {

    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onFinish() {

    }
}
