package com.zhiwan.hamitao.zhiwan.network;



import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.zhiwan.hamitao.zhiwan.network.CommonParams.commonParam;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class NetworkRequest {

    private static <T> void addObservable(Observable<T> observable, Subscriber<T> subscriber) {
        RxUtils.getInstance().addSubscription(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber));
    }

    /**
     * 停止所有请求
     */
    public static void stop() {
        RxUtils.getInstance().unSubscription();
    }


    /**
     * 更新用户资料
     * 首页banner
     *
     * @param netWorkCallBack 回调
     */
    public static void udapteApk(NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().udapteApk(commonParam()), netWorkCallBack.getNetWorkSubscriber());
    }




}
