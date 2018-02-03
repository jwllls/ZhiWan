package com.hamitao.zhiwan.network;


import com.zhiwan.hamitao.base_module.network.NetWorkCallBack;
import com.zhiwan.hamitao.base_module.network.RxUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.zhiwan.hamitao.base_module.network.CommonParams.commonParam;


/**
 * Created by linjianwen on 2018/1/5.
 */

public class NetworkRequest {

    //observable  被观察者
    //observer    观察者

    private static <T> void addObservable(Observable<T> observable, Observer<T> observer) {

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


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


    public static void getRecommendList(NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().getRecommendList(), netWorkCallBack.getNetWorkSubscriber());
    }

    public static void getSquareList(NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().getSquareList(), netWorkCallBack.getNetWorkSubscriber());
    }

    public static void getMusic(NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().getRecommendList(), netWorkCallBack.getNetWorkSubscriber());
    }
}
