package com.zhiwan.hamitao.base_module.model;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class UserModel extends UserInfoModel{

    /**
     * IM账号
     */
    private String imId;
    /**
     * IM登陆签名
     */
    private String imSign;
    /**
     * 登陆token
     */
    private String token;

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public String getImSign() {
        return imSign;
    }

    public void setImSign(String imSign) {
        this.imSign = imSign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
