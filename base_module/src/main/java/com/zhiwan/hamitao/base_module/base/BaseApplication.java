package com.zhiwan.hamitao.base_module.base;

import android.app.Application;

import com.chenenyu.router.BuildConfig;
import com.chenenyu.router.Configuration;
import com.chenenyu.router.Router;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.turing.authority.authentication.AuthenticationListener;
import com.turing.authority.authentication.SdkInitializer;
import com.zhiwan.hamitao.base_module.Constant;
import com.zhiwan.hamitao.base_module.util.AppVersionUtil;
import com.zhiwan.hamitao.base_module.util.LogUtil;
import com.zhiwan.hamitao.base_module.util.PropertiesUtil;
import com.zhiwan.hamitao.base_module.util.RecordUtil;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by linjianwen on 2018/1/4.
 */

public class BaseApplication extends Application {


    private static final String TAG = BaseApplication.class.getSimpleName();

    private RefWatcher refWatcher; //用于检测内存泄漏

    public static BaseApplication instance;

    public static BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initApp();

    }

    private void initApp() {
        instance = this;

        //内存泄漏
        refWatcher = setupLeakCanary();

        //初始化录音存放位置：
        RecordUtil.getInstance().setSavePath(Constant.USER_RECORD_LOCAL);

        //应用版本号
        Constant.versionCode = AppVersionUtil.getVersionCode(this);

        //初始化Sharepreference
        PropertiesUtil.getInstance().init(this);

        //初始化图灵SDK
        SdkInitializer.init(this, Constant.TURING_KEY, Constant.TURING_SECRET, new AuthenticationListener() {
            @Override
            public void onSuccess() {
                LogUtil.d(TAG, "图灵SDK初始化成功");
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, s + "错误码:" + i);
            }
        });

        //初始化极光推送
        JPushInterface.setDebugMode(true);//设置开启日志，发布时关闭日志
        JPushInterface.init(instance);//初始化极光推送


        //初始化路由框架
        Router.initialize(new Configuration.Builder()
                // 调试模式，开启后会打印log
                .setDebuggable(BuildConfig.DEBUG)
                // 模块名，每个使用Router的module都要在这里注册
                .registerModules("im_module", "base_module", "app")
                .build());


    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher() {
        return instance.refWatcher;
    }

}
