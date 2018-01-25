package com.zhiwan.hamitao.im_module.chat.mvp.conversation;

import com.tencent.TIMConversation;

import java.util.List;

/**
 * 会话列表界面的接口
 */
public interface ConversationView {

    /**
     * 初始化界面或刷新界面
     */
    void initView(List<TIMConversation> conversationList);


    /**
     * 删除会话
     */
    void removeConversation(String identify);

    /**
     * 刷新
     */
    void refresh();

    /**
     * 刷新本地资料
     */
    void refreshInfo();

}
