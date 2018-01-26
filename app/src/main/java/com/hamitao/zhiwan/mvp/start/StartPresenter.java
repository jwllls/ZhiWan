package com.hamitao.zhiwan.mvp.start;

import com.zhiwan.hamitao.base_module.base.BasePresenter;

/**
 * Created by linjianwen on 2018/1/26.
 */

public class StartPresenter implements BasePresenter {

    private StartView view;

    public StartPresenter(StartView view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    public void jumpActivity(){
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                context.startActivity(new Intent(context, MainActivity));
//            }
//        },1000);
        view.naviMain();
    }


}
