package com.zhiwan.hamitao.xg.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;
import com.zhiwan.hamitao.base_module.Constant;
import com.zhiwan.hamitao.base_module.IM.Foreground;
import com.zhiwan.hamitao.base_module.util.ActivityCollector;
import com.zhiwan.hamitao.base_module.util.GsonUtil;
import com.zhiwan.hamitao.base_module.util.PropertiesUtil;
import com.zhiwan.hamitao.base_module.util.SystemUtil;
import com.zhiwan.hamitao.xg.AiNotificationTypeEnum;
import com.zhiwan.hamitao.xg.push.NotificationMsgModel;
import com.zhiwan.hamitao.xg.push.TransPush;
import com.zhiwan.hamitao.xg.push.VideoNotificationModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.POWER_SERVICE;

public class MessageReceiver extends XGPushBaseReceiver {
    private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
    public static final String LogTag = "TPushReceiver";

    private void show(Context context, String text) {
//        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    private final int pushId = 1000;

    // 通知展示
    @Override
    public void onNotifactionShowedResult(Context context,
                                          XGPushShowedResult notifiShowedRlt) {
        if (context == null || notifiShowedRlt == null || Foreground.get().isForeground() //应用在前台
                ) {
            return;
        }


        try {
            if (ActivityCollector.getActivity(Class.forName("com.langu.yuefan.activity.StartActivity")) != null) {//如果应用没有被销毁，会在im那边推过来
                return;
            }
            pushNotify(context, Class.forName("com.langu.yuefan.activity.StartActivity"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public <T> void pushNotify(Context context, Class<T> clazz) {
        CharSequence senderStr;

        senderStr = context.getResources().getString(R.string.app_name);
        //电源管理器
        PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");


        mWakelock.acquire();//点亮

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        Intent notificationIntent = new Intent(context, clazz);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentTitle(senderStr)//设置通知栏标题
                .setContentText("您有一条新消息")
                .setContentIntent(intent) //设置通知栏点击意图
//                .setNumber(++pushNum) //设置通知集合的数量
                .setTicker("您有一条新消息") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setDefaults(default_notification())//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(android.R.drawable.sym_def_app_icon);//设置通知小ICON
        Notification notify = mBuilder.build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(pushId, notify);

        mWakelock.release();
    }


    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null) {
            return;
        }
        String text;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);

    }

    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);

    }

    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);

    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            text = "通知被打开 :" + message;
        } else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // 通知被清除啦。。。。
            // APP自己处理通知被清除后的相关动作
            text = "通知被清除 :" + message;
        }
//		Toast.makeText(context, "广播接收到通知被点击:" + message.toString(),
//				Toast.LENGTH_SHORT).show();
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理的过程。。。
        Log.d(LogTag, text);
        show(context, text);
    }

    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {
        if (context == null || message == null) {
            return;
        }
        String text;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = message + "注册成功";
            // 在这里拿token
            String token = message.getToken();
        } else {
            text = message + "注册失败，错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    // 消息透传
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("extra")) {
                    String value = obj.getString("extra");
                    Log.d(LogTag, "get custom value:" + value);
                    TransPush push = GsonUtil.GsonToBean(value, TransPush.class);
                    if (push == null) return;
                    AiNotificationTypeEnum anEnum = AiNotificationTypeEnum.getType(push.getTransType());
                    if (anEnum == null) return;
                    switch (anEnum) {
                        case VIDEO_APPLY:
                            VideoNotificationModel model = GsonUtil.GsonToBean(push.getContent(), VideoNotificationModel.class);
                            if (SystemUtil.isAppAlive(context, Constant.PACKAGE)) {
                     /*           //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
                                //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
                                //DetailActivity,再按Back键就不会返回MainActivity了。所以在启动
                                //DetailActivity前，要先启动MainActivity。
                                Log.i("NotificationReceiver", "the app process is alive");
//                                Intent mainIntent = Router.build("start").getIntent(context);
                                //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
                                //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
                                //如果Task栈不存在MainActivity实例，则在栈顶创建
                                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Intent detailIntent = Router.build("callwoman").getIntent(context);
                                detailIntent.putExtra(Constant.EXTRA_BUNDLE, model);
                                Intent[] intents = {mainIntent, detailIntent};
                                context.startActivities(intents);*/
                            } else {
                                //如果app进程已经被杀死，先重新启动app，将DetailActivity的启动参数传入Intent中，参数经过
                                //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入
                                // 参数跳转到指定的Activity中去了
                                Log.i("NotificationReceiver", "the app process is dead");
                                Intent launchIntent = context.getPackageManager().
                                        getLaunchIntentForPackage(Constant.PACKAGE);
                                if (launchIntent != null) {
                                    launchIntent.setFlags(
                                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                    launchIntent.putExtra(Constant.EXTRA_BUNDLE, model);
                                    context.startActivity(launchIntent);
                                }
                            }
                            break;
                        case NOTIFICATION_ALL:
                            NotificationMsgModel msgModel = GsonUtil.GsonToBean(push.getContent(), NotificationMsgModel.class);
                            context.sendBroadcast(new Intent(Constant.NOTIFICATION).putExtra("notification", msgModel));
                            break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据用户设置是否开启响铃和震动
     *
     * @return
     */
    public int default_notification() {
        int defaults = Notification.DEFAULT_LIGHTS;//使用默认闪光提示
        if (PropertiesUtil.getInstance().getBoolean(SystemUtil.RING_ON_OFF,
                true)) {
            defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        }
        if (PropertiesUtil.getInstance().getBoolean(SystemUtil.VIBRATE_ON_OFF,
                true)) {
            defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
        }
        if (PropertiesUtil.getInstance().getBoolean(SystemUtil.RING_ON_OFF,
                true) && PropertiesUtil.getInstance().getBoolean(SystemUtil.VIBRATE_ON_OFF,
                true)) {
            return Notification.DEFAULT_ALL;
        }

        return defaults;
    }
}
