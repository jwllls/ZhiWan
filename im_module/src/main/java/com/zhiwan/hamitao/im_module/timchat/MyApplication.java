package com.zhiwan.hamitao.im_module.timchat;

import android.app.Application;
import android.content.Context;

import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushListener;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.zhiwan.hamitao.im_module.R;
import com.zhiwan.hamitao.im_module.timchat.utils.Foreground;


/**
 * 全局Application
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Foreground.init(this);
        context = getApplicationContext();
        if(MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify){
                        //消息被设置为需要提醒
                        notification.doNotify(getApplicationContext(), R.drawable.icon_share);
                    }
                }
            });
        }
    }

    public static Context getContext() {
        return context;
    }



}
