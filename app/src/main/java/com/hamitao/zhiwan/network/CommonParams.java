package com.hamitao.zhiwan.network;


import com.hamitao.zhiwan.Constant;
import com.hamitao.zhiwan.base.BaseApplication;
import com.hamitao.zhiwan.enums.OsEnum;
import com.hamitao.zhiwan.model.UserModel;
import com.hamitao.zhiwan.util.StringUtil;
import com.hamitao.zhiwan.util.SystemUtil;
import com.hamitao.zhiwan.util.UserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linjianwen on 2018/1/5.
 *
 * 公共参数类
 */

public class CommonParams {


    /**
     * 所有接口的共有参数
     */
    public static Map<String, String> commonParam() {
        Map<String, String> map = new HashMap<>();
        UserModel user = UserUtil.user();
        if (user != null) {
            map.put("userId", String.valueOf(user.getUserId()));
            String token = user.getToken();
            if (StringUtil.isNotBlank(token))
                map.put("token", token);
        }
        map.putAll(commonPhoneInfo());
        return map;
    }

    /**
     * 用户未登录的状态下,接口请求用这个参数
     * 手机信息相关参数
     */
    public static Map<String, String> commonPhoneInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("deviceId", SystemUtil.getUDID(BaseApplication.getInstance()));//手机UDID
        map.put("mac", SystemUtil.getMacAddress(BaseApplication.getInstance()));//手机wifi的Mac地址
        map.put("os", OsEnum.ANDROID.getType() + "");//用户手机类型:android
        String appChannel = SystemUtil.getAppMetaData(BaseApplication.getInstance());
        if (!StringUtil.isBlank(appChannel)) {
            map.put("channel", appChannel);//渠道名
        }
        map.put("deviceName", SystemUtil.getDevice());//手机型号
        map.put("osVersion", SystemUtil.getOSVersion());//手机系统版本
        map.put("version", Constant.versionCode + "");//版本控制
        map.put("packId", Constant.PACKAGE_ID);
//        if (BaseApplication.mCurrentLat != default_mCurrent && BaseApplication.mCurrentLong != 0) {
//            map.put("lon", BaseApplication.mCurrentLong + "");//用户经度
//            map.put("lat", BaseApplication.mCurrentLat + "");//用户纬度
//        }
        map.put("serverIndex", "1");//区分腾讯IM和环信IM
        map.put("serveVersion", "2");//区分重构新包和旧包
        return map;
    }

}
