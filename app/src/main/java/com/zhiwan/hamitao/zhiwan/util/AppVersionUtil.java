package com.zhiwan.hamitao.zhiwan.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class AppVersionUtil {


    public static AppVersionUtil appVersionUtil = null;

    private AppVersionUtil(){}

    public static AppVersionUtil getInstance(){
        if (appVersionUtil == null){
            appVersionUtil = new AppVersionUtil();
        }
        return appVersionUtil;
    }

    /**
     * 获取版本号
     * String
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String versionName = info.versionName;
            LogUtil.e("info", "versionName" + versionName);
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取版本号
     * int
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
            LogUtil.e("info", "versionCode" + versionCode);
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

}
