package com.hamitao.zhiwan.network;


import com.hamitao.zhiwan.enums.ErrorCode;
import com.hamitao.zhiwan.util.StringUtil;

/**
 * Created by linjianwen on 2018/1/5.
 *
 * 网络请求回调
 */

public class NetWorkCallBack {

    private BaseCallBack callBack = null;

    public NetWorkCallBack(BaseCallBack callBack) {
        this.callBack = callBack;
    }

    public NetworkObserver getNetWorkSubscriber() {
        return networkObserver;
    }
    private NetworkObserver networkObserver = new NetworkObserver() {

        @Override
        public void onError(Throwable e) {
            try {
                if (callBack != null) {
                    callBack.onFail(null, e.getMessage());
                    callBack.onEnd();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        @Override
        public void onNext(NetWordResult result) {
            try {
                if (result.isOk()) {
                    //请求成功
                    if (callBack != null)
                        callBack.onSuccess(result);

                } else {
                    //请求失败
                    String message = "未知错误";
                    if (!StringUtil.isBlank(result.getError().getMessage())) {
                        message = result.getError().getMessage();
                    }
                    if (result.getError().getCode() == ErrorCode.LOGIN_TOKEN_INVALID.getType()) {
                        //token过期
                        //身份异常,重新登录
                       // ReloginUtil.relogin();
                    }
                    if (callBack != null)
                        callBack.onFail(result, message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



    public interface BaseCallBack {

        void onSuccess(NetWordResult result);

        void onFail(NetWordResult result, String msg);

        void onBegin();

        void onEnd();
    }


}
