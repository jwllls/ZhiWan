package com.hamitao.zhiwan.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class MusicService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * Service第一次启动的时候调用这个方法，可以做一些变量的初始化。
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Service每一次启动的时候调用这个方法，可以在此方法写一些业务逻辑。
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * Service销毁的时候调用，用于释放资源。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

