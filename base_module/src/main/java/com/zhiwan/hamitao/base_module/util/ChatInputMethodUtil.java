package com.zhiwan.hamitao.base_module.util;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.langu.frame.base.BaseApplication;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Created by Zenfer on 2016/1/8.
 * <p>
 * 实现微信&QQ聊天界面的输入法和表情的切换方式
 */
public class ChatInputMethodUtil {

    private Activity activity = null;

    private Window window = null;

    private InputMethodManager mInputMethodManager;

    int inputHeight = 0;
    // 输入法监听
    boolean isFirstShow = true;//是否第一次弹出输入法

    public ChatInputMethodUtil(Activity activity) {
        this.window = activity.getWindow();
        this.mInputMethodManager = (InputMethodManager) BaseApplication.getInstance().getSystemService(INPUT_METHOD_SERVICE);
    }

    /**
     * 隐藏软键盘 Zenfer
     */
    public void hidenSoftInput(Activity activity) {
        if (window == null)
            return;
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        if (mInputMethodManager == null)
            return;
        if (mInputMethodManager.isActive()) {
            try {
                mInputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示软键盘,此方法是使用在点击<非编辑框>的情况下,通过代码去弹出输入法
     *
     * @param view 编辑框
     */
    public void showSoftInput(View view) {
        if (window == null)
            return;
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (mInputMethodManager == null)
            return;
        if (mInputMethodManager.isActive()) {
            try {
                mInputMethodManager.showSoftInput(view,
                        InputMethodManager.SHOW_IMPLICIT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示软键盘,此方法是使用在点击<编辑框>的情况下,当你点击编辑框的时候,系统会自动默认弹出输入法
     */
    public void showSoftInput() {
        if (window == null)
            return;
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

}
