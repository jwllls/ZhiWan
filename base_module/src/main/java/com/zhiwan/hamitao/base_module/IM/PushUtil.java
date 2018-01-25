package com.zhiwan.hamitao.base_module.IM;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMMessage;
import com.zhiwan.hamitao.base_module.R;
import com.zhiwan.hamitao.base_module.base.BaseApplication;

import static android.content.Context.POWER_SERVICE;
import static com.tencent.qalsdk.service.QalService.context;


/**
 * 在线消息通知展示
 */
public class PushUtil {


    private final int pushId = 1000;

    private static PushUtil instance = new PushUtil();

    private PowerManager.WakeLock mWakelock;

    public void init(Context context) {
        //电源管理器
        PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");
    }

    private PushUtil() {

    }

    public static PushUtil getInstance() {
        return instance;
    }


    public <T> void PushNotify(TIMMessage msg, Class<T> clazz) {
        //系统消息，自己发的消息，程序在前台的时候不通知
        if (msg == null //消息为空
                || Foreground.get().isForeground() //应用在前台
                || msg.isSelf() //自己的消息
                || (msg.getConversation().getType() != TIMConversationType.Group
                && msg.getConversation().getType() != TIMConversationType.C2C) //非群消息,非私聊消息
                || msg.getRecvFlag() == TIMGroupReceiveMessageOpt.ReceiveNotNotify
                )//群消息设置为不需通知
            return;


        CharSequence senderStr, contentStr;

        senderStr = BaseApplication.getInstance().getResources().getString(R.string.app_name);

        if (mWakelock != null) mWakelock.acquire();//点亮

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        Intent notificationIntent = new Intent(context, clazz);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        mBuilder.setContentTitle(BaseApplication.getInstance().getResources().getString(R.string.app_name))//设置通知栏标题
                .setContentText("您有一条新消息")
                .setContentIntent(intent) //设置通知栏点击意图
//                .setNumber(++pushNum) //设置通知集合的数量
                .setTicker(senderStr + ":" + "您有一条新消息") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(R.drawable.app_logo);//设置通知小ICON
        Notification notify = mBuilder.build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(pushId, notify);

        mWakelock.release();
    }


}
