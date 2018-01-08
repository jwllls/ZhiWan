package com.zhiwan.hamitao.zhiwan.base;

import android.app.Application;

import com.zhiwan.hamitao.zhiwan.Constant;
import com.zhiwan.hamitao.zhiwan.util.AppVersionUtil;

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
        Constant.versionCode = AppVersionUtil.getVersionCode(this);
    }

}
