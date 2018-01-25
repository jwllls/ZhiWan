package com.hamitao.zhiwan.mvp.userinfo;

import android.content.Context;

import com.zhiwan.hamitao.base_module.base.BasePresenter;

/**
 * Created by linjianwen on 2018/1/17.
 */

public class UserInfoPresenter implements BasePresenter {


    private UserInfoView presenter;

    private Context context;

    public UserInfoPresenter(UserInfoView presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    /**
     * 获取用户资料
     */
    public void getUserData(){

    }


    /**
     * 更新用户信息
     */
    public void updateUserData(){

    }


    /**
     * 更新用户头像
     */
    public void updateUserFace(String url){

    }
}
