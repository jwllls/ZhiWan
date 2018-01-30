package com.zhiwan.hamitao.base_module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class BaseActivity  extends AppCompatActivity {


    public static final String TAG = BaseActivity.class.getSimpleName();


    /**
     * 屏幕的宽度、高度、密度
     */
    public static int mScreenWidth;
    public static int mScreenHeight;
    public static float mDensity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        mDensity = metric.density;
    }




   /* /*//**************************************信鸽注册**************************************//*/
    public void registerPush() {
        //  信鸽注册
        if (UserUtil.user() != null) {
            XGPushManager.registerPush(this, UserUtil.user().getUserId() + "",
                    new XGIOperateCallback() {
                        @Override
                        public void onSuccess(Object data, int fla) {
                            LogUtil.d(TAG,UserUtil.user().getUserId() + "信鸽注册成功");
                        }

                        @Override
                        public void onFail(Object data, int errCode, String msg) {
                            LogUtil.d(TAG,"信鸽注册失败---" + errCode + "---" + msg);
                        }
                    });
            Intent service = new Intent(getApplicationContext(), XGPushServiceV3.class);
            getApplicationContext().startService(service);
        }
    }
    /*//**************************************信鸽注册**************************************//*/*/

}
