package com.hamitao.zhiwan.mvp.userinfo;

import com.zhiwan.hamitao.base_module.base.BaseView;
import com.zhiwan.hamitao.base_module.model.UserModel;

/**
 * Created by linjianwen on 2018/1/17.
 */

public interface UserInfoView extends BaseView {


    /**
     * 获取用户信息
     * @param userModel
     */
    void getUserInfo(UserModel userModel);


    /**
     * 更新成功
     * @param user
     */
    void updateSuccess(UserModel user);


    /**
     * 更新失败
     */
    void updateFail();

//
//    /**
//     * 上传图片成功
//     * @param imgUrl
//     */
//    void updateImgSuccess(String imgUrl);
//
//
//    /**
//     * 上传图片失败
//     * @param msg
//     */
//    void updateImgFail(String msg);


}
