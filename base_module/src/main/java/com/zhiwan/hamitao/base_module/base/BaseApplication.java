package com.zhiwan.hamitao.base_module.base;

import android.app.Application;


import com.squareup.leakcanary.LeakCanary;
import com.zhiwan.hamitao.base_module.Constant;
import com.zhiwan.hamitao.base_module.util.AppVersionUtil;
import com.zhiwan.hamitao.base_module.util.PropertiesUtil;
import com.zhiwan.hamitao.base_module.util.RecordUtil;


/**
 * Created by linjianwen on 2018/1/4.
 */

public class BaseApplication extends Application {


    private static final String tag = BaseApplication.class.getSimpleName();


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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        //初始化录音存放位置：
        RecordUtil.getInstance().setSavePath(Constant.USER_RECORD_LOCAL);

        //应用版本号
        Constant.versionCode = AppVersionUtil.getVersionCode(this);

        //初始化Sharepreference
        PropertiesUtil.getInstance().init(this);





    }


}
