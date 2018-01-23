package com.zhiwan.hamitao.im_module.mvp.conversation;

import com.langu.frame.IM.listener.IMMessageListener;
import com.langu.frame.model.chat.ChatSo;
import com.langu.frame.utils.GsonUtil;
import com.langu.frame.utils.StringUtil;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 会话界面逻辑
 */
public class ConversationPresenter implements Observer {

    private static final String TAG = "ConversationPresenter";
    private ConversationView view;

    public ConversationPresenter(ConversationView view) {
        //注册刷新监听
        IMMessageListener.getInstance().addObserver(this);
        this.view = view;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof IMMessageListener) {
            TIMMessage timMessage = (TIMMessage) data;
            if (timMessage != null) {
                refreshLocationInfo(timMessage);
            }
            //重新获取会话列表
            getConversation();

        }
    }

    public void getConversation() {
        List<TIMConversation> list = TIMManager.getInstance().getConversionList();
        List<TIMConversation> result = new ArrayList<>();
        for (TIMConversation conversation : list) {
            if (conversation.getType() != TIMConversationType.C2C) continue;
            //需要排序和过滤
            if (conversation.getPeer().equals("admin")) continue;
            result.add(conversation);
        }
        if (result.size() <= 0) {
            result.add(TIMManager.getInstance().getConversation(TIMConversationType.C2C, "10001"));
        }
        view.initView(result);
    }

    /**
     * 删除会话
     *
     * @param type 会话类型
     * @param id   会话对象id
     */
    public boolean delConversation(TIMConversationType type, String id) {
        return TIMManager.getInstance().deleteConversationAndLocalMsgs(type, id);
    }


    /**
     * 刷新本地资料
     *
     * @param msg
     */
    public void refreshLocationInfo(TIMMessage msg) {
        for (int i = 0; i < msg.getElementCount(); i++) {
            TIMElem elem = msg.getElement(i);
            if (elem.getType() == TIMElemType.Custom) {
                TIMCustomElem text = (TIMCustomElem) elem;
                String data = getMessageContent(text);
                if (!StringUtil.isBlank(data)) {
                    ChatSo chatSo = GsonUtil.GsonToBean(data, ChatSo.class);
                    if (chatSo == null) {
                        return;
                    }
                    if (chatSo.isHasUpdateInfo()) {
                        view.refreshInfo();
                    }
                }
            }
        }
    }

    /**
     * 获取自定义参数中的data字符串
     */
    public static String getMessageContent(TIMCustomElem customElem) {
        if (null == customElem) return "";
        String data = new String(customElem.getData());
        return data;
    }
}
