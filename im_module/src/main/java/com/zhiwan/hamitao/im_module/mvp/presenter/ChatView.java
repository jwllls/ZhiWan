package com.zhiwan.hamitao.im_module.mvp.presenter;


import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.ext.message.TIMMessageDraft;

import java.util.List;

/**
 * 聊天界面的接口
 */
public interface ChatView {

    /**
     * 显示消息
     */
    void showMessage(TIMMessage message);

    /**
     * 显示消息
     */
    void showMessage(List<TIMMessage> messages, boolean isLoadMore);

    /**
     * 清除所有消息(离线恢复),并等待刷新
     */
    void clearAllMessage();

    /**
     * 发送消息成功
     *
     * @param message 返回的消息
     */
    void onSendMessageSuccess(TIMMessage message);

    /**
     * 发送消息失败
     *
     * @param code    返回码
     * @param desc    返回描述
     * @param message 发送的消息
     */
    void onSendMessageFail(int code, String desc, TIMMessage message);

    /**
     * 显示草稿
     */
    void showDraft(TIMMessageDraft draft);

    void onBegin();

    void onFinish();

}
