package com.zhiwan.hamitao.base_module.util;

import com.zhiwan.hamitao.base_module.model.UserModel;


/**
 * Created by linjianwen on 2018/1/5.
 */

public class UserUtil {

    private static final String MY_INFO = "MY_INFO";
    private static UserModel user;
    public static long vipEndTime = 0;//vip结束时间戳

    private UserUtil() {
    }

    /**
     * 保存自己的用户资料
     *
     * @param user
     */
    public static void saveUserMine(UserModel user) {
        if (user != null) {
            UserUtil.user = user;
            PropertiesUtil.getInstance().setString(MY_INFO, GsonUtil.GsonToString(user));
        }
    }

    /**
     * 获取自己的用户资料
     *
     * @return
     */
    public static UserModel user() {
        if (user == null) {
            String userJson = PropertiesUtil.getInstance().getString(MY_INFO, "");
            if (StringUtil.isBlank(userJson)) return null;
            user = GsonUtil.GsonToBean(userJson, UserModel.class);
        }
        return user;
    }

    /**
     * 判断用户资料是否不为null
     *
     * @return true不为null, 反之则为null
     */
    public static boolean isUserNotNull() {
        String userJson = PropertiesUtil.getInstance().getString(MY_INFO, "");
        if (StringUtil.isBlank(userJson)) return false;
        return GsonUtil.GsonToBean(userJson, UserModel.class) != null;
    }

    /**
     * 清除本地用户信息
     */
    public static void clear() {
        PropertiesUtil.getInstance().setString(MY_INFO, "");//用户资料
        user = null;
    }


    /**
     * 本地缓存用户资料
     *
     * @param user 用户资料
     */
    public static void saveUserByUserId(UserModel user) {
        if (user != null)
            PropertiesUtil.getInstance().setString(String.valueOf(user.getUserId()), GsonUtil.GsonToString(user));
    }

    /**
     * 获取本地用户资料
     *
     * @param userId 用户ID
     * @return
     */
    public static UserModel user(long userId) {
        String userJson = PropertiesUtil.getInstance().getString(String.valueOf(userId), "");
        if (StringUtil.isBlank(userJson)) return null;
        UserModel user = GsonUtil.GsonToBean(userJson, UserModel.class);
        if (user != null) return user;
        else return null;
    }

    /**
     * 本地缓存用户资料
     *
     * @param user 用户资料
     */
    public static void saveUserByImId(UserModel user) {
        if (user != null)
            PropertiesUtil.getInstance().setString(user.getImId(), GsonUtil.GsonToString(user));
    }

    /**
     * 获取本地用户资料
     *
     * @param imId 用户 IM ID
     */
    public static UserModel user(String imId) {
        String userJson = PropertiesUtil.getInstance().getString(imId, "");
        if (StringUtil.isBlank(userJson)) return null;
        UserModel user = GsonUtil.GsonToBean(userJson, UserModel.class);
        if (user != null) return user;
        else return null;
    }
}
