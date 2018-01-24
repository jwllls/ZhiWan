package com.zhiwan.hamitao.im_module.presentation.presenter;

import android.os.Handler;

import com.zhiwan.hamitao.im_module.presentation.viewfeatures.SplashView;


/**
 * 闪屏界面逻辑
 */
public class SplashPresenter {
    SplashView view;
    private static final String TAG = SplashPresenter.class.getSimpleName();

    public SplashPresenter(SplashView view) {
        this.view = view;
    }


    /**
     * 加载页面逻辑
     */
    public void jumpActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view.isUserLogin()) {
                    view.navToHome();
                } else {
                    view.navToLogin();
                }
            }
        }, 1000);
    }


}
