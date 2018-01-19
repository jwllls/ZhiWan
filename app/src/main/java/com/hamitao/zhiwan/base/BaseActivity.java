package com.hamitao.zhiwan.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hamitao.zhiwan.util.LogUtil;
import com.hamitao.zhiwan.util.UserUtil;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushServiceV3;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class BaseActivity extends CheckPermissionsActivity {


    public static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    //**************************************信鸽注册**************************************//
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
    //**************************************信鸽注册**************************************//

}
