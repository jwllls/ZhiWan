package com.hamitao.zhiwan.activity;

import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.hamitao.zhiwan.R;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.zhiwan.hamitao.im_module.presentation.business.InitBusiness;
import com.zhiwan.hamitao.im_module.presentation.business.LoginBusiness;
import com.zhiwan.hamitao.im_module.presentation.event.FriendshipEvent;
import com.zhiwan.hamitao.im_module.presentation.event.GroupEvent;
import com.zhiwan.hamitao.im_module.presentation.event.MessageEvent;
import com.zhiwan.hamitao.im_module.presentation.event.RefreshEvent;
import com.zhiwan.hamitao.im_module.presentation.presenter.SplashPresenter;
import com.zhiwan.hamitao.im_module.presentation.viewfeatures.SplashView;
import com.zhiwan.hamitao.im_module.timchat.model.UserInfo;
import com.zhiwan.hamitao.im_module.timchat.ui.HomeActivity;
import com.zhiwan.hamitao.im_module.timchat.ui.customview.DialogActivity;
import com.zhiwan.hamitao.im_module.timchat.utils.PushUtil;
import com.zhiwan.hamitao.im_module.tlslibrary.service.TlsBusiness;
import com.zhiwan.hamitao.im_module.ui.NotifyDialog;


/**
 * Created by linjianwen on 2018/1/4.
 */

public class StartActivity extends AppCompatActivity implements SplashView, TIMCallBack {

    private String TAG = StartActivity.class.getSimpleName();

    private SplashPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();


    }

    private void init() {

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);

        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());
        //初始化IMSDK
        InitBusiness.start(getApplicationContext(), loglvl);
        //初始化TLS
        TlsBusiness.init(getApplicationContext());

        presenter = new SplashPresenter(this);
        presenter.jumpActivity();
    }


    @Override
    public void navToHome() {
        //登录之前要初始化群和好友关系链缓存
        TIMUserConfig userConfig = new TIMUserConfig();
        userConfig.setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                Log.d(TAG, "receive force offline message");
                Intent intent = new Intent(StartActivity.this, DialogActivity.class);
                startActivity(intent);
            }

            @Override
            public void onUserSigExpired() {
                //token票据过期，需要重新登录
                new NotifyDialog().show(getString(R.string.tls_expire), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                            logout();
                    }
                });
            }
        })
                //网络连接监听
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.i(TAG, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i(TAG, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i(TAG, "onWifiNeedAuth");
                    }
                });

        //设置刷新监听
        RefreshEvent.getInstance().init(userConfig);
        //初始化好友链
        userConfig = FriendshipEvent.getInstance().init(userConfig);
        //初始化群组链
        userConfig = GroupEvent.getInstance().init(userConfig);
        //初始化消息事件
        userConfig = MessageEvent.getInstance().init(userConfig);
        TIMManager.getInstance().setUserConfig(userConfig);
        //Imd登陆
        LoginBusiness.loginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), this);
    }

    @Override
    public void navToLogin() {

    }

    @Override
    public boolean isUserLogin() {
        return false;
    }


    /**
     * 清楚所有通知栏通知
     */
    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        // MiPushClient.clearNotification(getApplicationContext());
    }


    /**
     * 登陆失败回调
     *
     * @param i
     * @param s
     */
    @Override
    public void onError(int i, String s) {
        Log.e(TAG, "login error : code " + i + " " + s);
        switch (i) {
            case 6208:
                //离线状态下被其他终端踢下线
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(com.zhiwan.hamitao.im_module.R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        navToHome();
                    }
                });
                break;
            case 6200:
                Toast.makeText(this, getString(com.zhiwan.hamitao.im_module.R.string.login_error_timeout), Toast.LENGTH_SHORT).show();
                navToLogin();
                break;
            default:
                Toast.makeText(this, getString(com.zhiwan.hamitao.im_module.R.string.login_error), Toast.LENGTH_SHORT).show();
                navToLogin();
                break;
        }
    }

    @Override
    public void onSuccess() {

        //初始化程序后台后消息推送
        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
        String deviceMan = Build.MANUFACTURER;
        //注册小米和华为推送
//        if (deviceMan.equals("Xiaomi") && shouldMiInit()){
//            MiPushClient.registerPush(getApplicationContext(), "2882303761517480335", "5411748055335");
//        }else if (deviceMan.equals("HUAWEI")){
//            PushManager.requestToken(this);
//        }


//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "refreshed token: " + refreshedToken);
//
//        if(!TextUtils.isEmpty(refreshedToken)) {
//            TIMOfflinePushToken param = new TIMOfflinePushToken(169, refreshedToken);
//            TIMManager.getInstance().setOfflinePushToken(param, null);
//        }
//        MiPushClient.clearNotification(getApplicationContext());
        Log.d(TAG, "imsdk env " + TIMManager.getInstance().getEnv());
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
