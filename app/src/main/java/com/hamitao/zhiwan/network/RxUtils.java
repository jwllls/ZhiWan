package com.hamitao.zhiwan.network;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class RxUtils {


    public static RxUtils rxUtils;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();


    private RxUtils(){}

    public static RxUtils getInstance(){
        if (rxUtils == null ){
            rxUtils = new RxUtils();
        }
        return  rxUtils;
    }

    public void clearSubscription() {
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.clear();
        }
    }

    public void unSubscription() {
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Subscription subscription) {
        if (compositeSubscription != null) {
            compositeSubscription.add(subscription);
        }
    }

}
