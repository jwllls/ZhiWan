package com.hamitao.zhiwan.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hamitao.zhiwan.R;

/**
 * Created by linjianwen on 2018/1/18.
 */

public class ItemView extends RelativeLayout {


    private TextView rightTextView;

    private TextView leftTextView;

    private void initView(Context context) {
        View.inflate(context, R.layout.view_item, ItemView.this);
        rightTextView = (TextView) this.findViewById(R.id.tv_item);
        leftTextView = (TextView) this.findViewById(R.id.view_item);
        init();
    }


    public ItemView(Context context) {
        super(context);
        initView(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }



    /**
     * 初始化操作
     */
    private void init() {

        ((TextView) leftTextView).setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.icon_arraow_right), null);

    }


    public void setRightText(String str) {
        rightTextView.setText(str);
    }

    public void setRightBackground(Drawable drawable) {
        rightTextView.setBackground(drawable);
    }

    public void setRigthDrawableTop(Drawable drawable) {
        rightTextView.setCompoundDrawables(null, drawable, null, null);
    }

    public void setRightDrawableButtom(Drawable drawable) {
        rightTextView.setCompoundDrawables(null, null, null, drawable);
    }

    public void setRightDrawableRight(Drawable drawable) {
        rightTextView.setCompoundDrawables(null, null, drawable, null);
    }

    public void setRightDrawableLeft(Drawable drawable) {
        rightTextView.setCompoundDrawables(drawable, null, null, null);
    }


    public void setLeftBackground(Drawable drawable) {
        leftTextView.setBackground(drawable);
    }

    public void setLeftText(String str) {
        if (leftTextView instanceof TextView) {
            ((TextView) leftTextView).setText(str);
        }

    }

    public void setLeftBackGround(Drawable drawable) {
        ((TextView) leftTextView).setCompoundDrawables(drawable, null, null, null);
    }

    public void setLeftDrawableTop(Drawable drawable) {
        ((TextView) leftTextView).setCompoundDrawables(null, drawable, null, null);
    }

    public void setLeftDrawableButtom(Drawable drawable) {
        ((TextView) leftTextView).setCompoundDrawables(null, null, null, drawable);
    }

    public void setLeftDrawableLeft(Drawable drawable) {
        ((TextView) leftTextView).setCompoundDrawables(drawable, null, null, null);
    }

    public void setLeftDrawableRight(Drawable drawable) {
        ((TextView) leftTextView).setCompoundDrawables(null, null, drawable, null);
    }

    public void setRightClickListener(OnClickListener listener) {
        rightTextView.setOnClickListener(listener);
    }

    public void setLeftClickListener(OnClickListener listener) {
        leftTextView.setOnClickListener(listener);
    }


}
