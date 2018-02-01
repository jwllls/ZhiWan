package com.zhiwan.hamitao.base_module;

import android.os.Environment;

/**
 * Created by linjianwen on 2018/1/25.
 */

public class Constant {

    public static String PROXY_SERVER_URL = "";

    public static final String PACKAGE = "com.zhiwan.hamitao.zhiwan";


    public static final int ACCOUNT_TYPE = 792;
    //sdk appid 由腾讯分配
    public static final int SDK_APPID = 1400059977;


    //图灵SDK
    public static final String TURING_KEY = "8822cd8417ea49e29f78d1f79888093b";
    public static final String TURING_SECRET = "av3lI07mJ2FdR7AJ";

    //龙域
    //String key = "03a192c8e18c4c3fafea62d0368d9fc7";
    //String secret = "3SO5x54GiO2IFdnE";

    //声网Appkey
    public static final String OPENVOICE_APPID = "699d5cc22dab468bae888cca23c18459";


    /**
     * 小于5M的视频无需压缩
     */
    public static final long max_video_size = 1024 * 1024 * 9;

    public static final String PACKAGE_ID = "0";//分包ID

    public static int versionCode = 1;//版本号

    public static boolean DEBUG = true;//调试模式
    /**
     * 请求方式
     */
    public static final int REQUEST_REFRESH = 1;//刷新列表
    public static final int REQUEST_MORE = 2;//加载更多

    public static final String FINAL_STATE = "";// 最终用户协议

    public static  String INVITE_CASH = "";// 邀请好友

    /**
     * 本地保存地址
     */
    //图片保存路径
    public final static String USER_PIC_LOCAL = Environment
            .getExternalStorageDirectory() + "/ZhiWan/Downloader/pic/";
    //apk保存路径
    public final static String USER_APK_LOCAL = Environment
            .getExternalStorageDirectory() + "/ZhiWan/Downloader/apk/";
    //音频保存路径
    public final static String USER_VOICE_LOCAL = Environment
            .getExternalStorageDirectory() + "/ZhiWan/Downloader/voice/";
    //视频保存路径
    public final static String USER_VIDEO_LOCAL = Environment
            .getExternalStorageDirectory() + "/ZhiWan/Downloader/video/";

    //录音保存路径
    public final static String USER_RECORD_LOCAL = Environment
            .getExternalStorageDirectory() + "/ZhiWan/Downloader/record/";

    public static final String CONNECT_FAIL = PACKAGE + ".CONNECT_FAIL";//重新连接失败
    public static final String CONNECT_SUCC = PACKAGE + ".CONNECT_SUCC";//重新连接成功

    public static final String RECEIVE_PRIVATE_MESSAGE = PACKAGE + ".RECEIVE_PRIVATE_MESSAGE";//收到私聊消息
    public static final String RECEIVE_GROUP_MESSAGE = PACKAGE + ".RECEIVE_GROUP_MESSAGE";//收到群聊消息
    public static final String RECEIVE_GROUP_MESSAGE_KICK = PACKAGE + ".RECEIVE_GROUP_MESSAGE_KICK";//收到被踢出群的系统消息
    public static final String NEW_MESSAGE = PACKAGE + ".NEW_MESSAGE";//有新消息
    public static final String EXTRA_BUNDLE = PACKAGE + ".EXTRA_BUNDLE";//透传数据
    public static final String NOTIFICATION = PACKAGE + ".NOTIFICATION";//全站广播
    public static final String LOGIN_SUCCESS = PACKAGE + ".LOGIN_SUCCESS";//登录成功
    public static final String RELOGIN = PACKAGE + ".RELOGIN";//重新登录


    /*请求码*/
    public static final int RECORD_CODE = 100;  //跳转到录音界面


}
