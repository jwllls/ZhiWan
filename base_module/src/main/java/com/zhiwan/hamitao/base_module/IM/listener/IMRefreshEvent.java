package com.zhiwan.hamitao.base_module.IM.listener;

import com.tencent.TIMConversation;
import com.tencent.TIMManager;
import com.tencent.TIMRefreshListener;

import java.util.List;
import java.util.Observable;

/**
 * IMSDK提供的刷新和被动更新的通知,登录前注册
 */
public class IMRefreshEvent extends Observable implements TIMRefreshListener {


    private volatile static IMRefreshEvent instance;

    private IMRefreshEvent(){
        //注册监听器
        TIMManager.getInstance().setRefreshListener(this);
    }

    public static IMRefreshEvent getInstance(){
        if (instance == null) {
            synchronized (IMRefreshEvent.class) {
                if (instance == null) {
                    instance = new IMRefreshEvent();
                }
            }
        }
        return instance;
    }

    /**
     * 数据刷新通知，如未读技术、会话列表等
     */
    @Override
    public void onRefresh() {
        setChanged();
        notifyObservers();
    }


    /**
     * 部分会话刷新，多终端数据同步
     */
    @Override
    public void onRefreshConversation(List<TIMConversation> list) {
        setChanged();
        notifyObservers();
    }
}
