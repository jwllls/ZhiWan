package com.zhiwan.hamitao.zhiwan.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by KID on 2016/11/4.
 */
public class ScreenUtil {

    /**
     *获取屏幕宽度
     */
    public static int getSWidth(Context context){

        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int  screenWidth=wm.getDefaultDisplay().getWidth();

        return screenWidth;
    }
    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    /**
     *获取屏幕高度
     */
    public static int getSHeight(Context context){

        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int  screenHeight=wm.getDefaultDisplay().getHeight();

        return screenHeight;
    }
}
