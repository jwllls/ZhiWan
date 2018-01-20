package com.hamitao.zhiwan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamitao.zhiwan.R;

/**
 * Created by linjianwen on 2018/1/18.
 */

public class itemView extends ViewGroup {


    private TextView rightTextView;

    private TextView leftTextView;


    public itemView(Context context) {
        super(context);
    }

    public itemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public itemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 初始化操作
     */
    private void init() {
        leftTextView.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.icon_arraow_right), null);
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
        leftTextView.setText(str);
    }

    public void setLeftBackGround(Drawable drawable) {
        leftTextView.setCompoundDrawables(drawable, null, null, null);
    }

    public void setLeftDrawableTop(Drawable drawable) {
        leftTextView.setCompoundDrawables(null, drawable, null, null);
    }

    public void setLeftDrawableButtom(Drawable drawable) {
        leftTextView.setCompoundDrawables(null, null, null, drawable);
    }

    public void setLeftDrawableLeft(Drawable drawable) {
        leftTextView.setCompoundDrawables(drawable, null, null, null);
    }

    public void setLeftDrawableRight(Drawable drawable) {
        leftTextView.setCompoundDrawables(null, null, drawable, null);
    }

    public void setRightClickListener(OnClickListener listener) {
        rightTextView.setOnClickListener(listener);
    }

    public void setLeftClickListener(OnClickListener listener) {
        leftTextView.setOnClickListener(listener);
    }


}
