package com.zhiwan.hamitao.base_module.IM.listener;

import android.content.Intent;

import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMGroupSystemElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMProfileSystemElem;
import com.zhiwan.hamitao.base_module.Constant;
import com.zhiwan.hamitao.base_module.IM.Foreground;
import com.zhiwan.hamitao.base_module.IM.PushUtil;
import com.zhiwan.hamitao.base_module.base.BaseApplication;
import com.zhiwan.hamitao.base_module.util.LogUtil;
import com.zhiwan.hamitao.base_module.util.StringUtil;

import java.util.List;
import java.util.Observable;

/**
 * Created by Zenfer on 2016/10/19.
 * 腾讯IM消息监听
 */
public class IMMessageListener extends Observable implements TIMMessageListener {

    private String tag = IMMessageListener.class.getSimpleName();

    private volatile static IMMessageListener instance;

    private IMMessageListener() {
        //注册消息监听器
        TIMManager.getInstance().addMessageListener(this);
    }

    public static IMMessageListener getInstance() {
        if (instance == null) {
            synchronized (IMMessageListener.class) {
                if (instance == null) {
                    instance = new IMMessageListener();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        for (TIMMessage msg : list) {
            if (!Foreground.get().isForeground()) {
                try {
                    PushUtil pushUtil = PushUtil.getInstance();
                    pushUtil.init(BaseApplication.getInstance());
                    pushUtil.PushNotify(msg, Class.forName("com.langu.aiai.activity.StartActivity"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            switch (msg.getConversation().getType()) {
                case C2C:
                    if (!msg.getConversation().getPeer().equals("admin"))
                        BaseApplication.getInstance().sendBroadcast(new Intent(Constant.NEW_MESSAGE));
                    //测试数据
//                    if (msg.getConversation().getPeer().equals("100014") || msg.getConversation().getPeer().equals("100027"))
//                        for (int i = 0; i < msg.getElementCount(); i++) {
//                            TIMElem elem = msg.getElement(i);
//                            if (elem.getType() == TIMElemType.Custom) {
//                                TIMCustomElem text = (TIMCustomElem) elem;
//                                String data = getMessageContent(text);
//                                Log.d("onNewMessages", "收到新消息 -- " + msg.getConversation().getPeer() + "---" + data);
//                            }
//                        }
                    //测试数据
                    setChanged();
                    notifyObservers(msg);
                    break;
                case Group:
                    BaseApplication.getInstance().sendBroadcast(new Intent(Constant.RECEIVE_GROUP_MESSAGE));
                    setChanged();
                    notifyObservers(msg);
                    break;
                case System:
                    LogUtil.d(tag,"系统消息");
                    for (int i = 0; i <= msg.getElementCount(); i++) {
                        TIMElem elem = msg.getElement(i);
                        switch (elem.getType()) {
                            case ProfileTips:
                                TIMProfileSystemElem timProfileSystemElem = (TIMProfileSystemElem) elem;
                                switch (timProfileSystemElem.getSubType()) {
                                    case TIM_PROFILE_SYSTEM_FRIEND_PROFILE_CHANGE:
                                        //好友简介更改通知
                                        break;
                                }
                                break;
                            case GroupSystem:
                                TIMGroupSystemElem groupSystemElem = (TIMGroupSystemElem) elem;
                                switch (groupSystemElem.getSubtype()) {
                                    case TIM_GROUP_SYSTEM_KICK_OFF_FROM_GROUP_TYPE:
                                        //被踢出群组的群系统信息
                                        if (!StringUtil.isBlank(groupSystemElem.getGroupId()))
                                            BaseApplication.getInstance().sendBroadcast(new Intent(Constant.RECEIVE_GROUP_MESSAGE_KICK)
                                                    .putExtra("groupId", Long.parseLong(groupSystemElem.getGroupId())));
                                        break;
                                }
                                break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return true; //返回true将终止回调链，不再调用下一个新消息监听器
    }

    /**
     * 主动通知新消息
     */
    public void onNewMessage(TIMMessage message) {
        setChanged();
        notifyObservers(message);
    }

    /**
     * 清理消息监听
     */
    public void clear() {
        instance = null;
    }

}
