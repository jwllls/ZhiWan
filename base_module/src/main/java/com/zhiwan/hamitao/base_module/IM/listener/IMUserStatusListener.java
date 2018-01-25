package com.zhiwan.hamitao.base_module.IM.listener;


import android.widget.Toast;

import com.langu.frame.base.BaseApplication;
import com.tencent.TIMManager;
import com.tencent.TIMUserStatusListener;

import static com.langu.frame.utils.ReloginUtil.relogin;

/**
 * Created by Administrator on 2016/10/19.
 */
public class IMUserStatusListener implements TIMUserStatusListener {

    private volatile static IMUserStatusListener instance;

    private IMUserStatusListener() {
        //注册消息监听器
        TIMManager.getInstance().setUserStatusListener(this);
    }

    public static IMUserStatusListener getInstance() {
        if (instance == null) {
            synchronized (IMUserStatusListener.class) {
                if (instance == null) {
                    instance = new IMUserStatusListener();
                }
            }
        }
        return instance;
    }

    @Override
    public void onForceOffline() {
        //被踢下线
        Toast.makeText(BaseApplication.getInstance(), "你的帐号在其他地方登录,如非被人操作,请及时修改密码并重新登录!", Toast.LENGTH_LONG).show();
        relogin();
    }

    @Override
    public void onUserSigExpired() {
        //票据过期，需要换票后重新登录
        Toast.makeText(BaseApplication.getInstance(), "你的帐号token过期,需重新登陆!", Toast.LENGTH_LONG).show();
        relogin();
    }

}
