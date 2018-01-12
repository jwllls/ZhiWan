package com.hamitao.zhiwan.base;

import android.app.Application;

import com.hamitao.zhiwan.Constant;
import com.hamitao.zhiwan.util.AppVersionUtil;


/**
 * Created by linjianwen on 2018/1/4.
 */

public class BaseApplication extends Application{


    private static final String TAG = "jianwen";


    public static BaseApplication instance;

    private BaseApplication() {
    }

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

        //应用版本号
        Constant.versionCode = AppVersionUtil.getVersionCode(this);

        //图灵机器人识别
    }

}
