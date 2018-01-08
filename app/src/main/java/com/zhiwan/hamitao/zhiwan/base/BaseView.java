package com.zhiwan.hamitao.zhiwan.base;

/**
 * Created by linjianwen on 2018/1/5.
 */

public interface BaseView {

    void onBegin();

    void onFinish();

    //错误信息
    void onMessageShow(String msg);
}
