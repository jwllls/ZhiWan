package com.zhiwan.hamitao.base_module.IM.listener;

import com.tencent.TIMConversation;
import com.tencent.TIMManager;
import com.tencent.TIMRefreshListener;

import java.util.List;

/**
 * Created by Zenfer on 2016/10/19.
 */
public class IMRefreshListener implements TIMRefreshListener {

    private volatile static IMRefreshListener instance;

    private IMRefreshListener() {
        //注册消息监听器
        TIMManager.getInstance().setRefreshListener(this);
    }

    public static IMRefreshListener getInstance() {
        if (instance == null) {
            synchronized (IMRefreshListener.class) {
                if (instance == null) {
                    instance = new IMRefreshListener();
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

    }

    /**
     * 部分会话刷新，多终端数据同步
     */
    @Override
    public void onRefreshConversation(List<TIMConversation> list) {

    }
}
