package com.zhiwan.hamitao.im_module.chat.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GlideCircleTransform extends BitmapTransformation {

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.parseColor("#ffffff");

    private static int mBorderColor = DEFAULT_BORDER_COLOR;
    private static int mBorderWidth = DEFAULT_BORDER_WIDTH;

    private static GlideCircleTransform circleTransform;

    public static GlideCircleTransform getInstance(Context Context) {
        if (circleTransform == null) {
            circleTransform = new GlideCircleTransform(Context);
        }
        mBorderColor = DEFAULT_BORDER_COLOR;
        mBorderWidth = DEFAULT_BORDER_WIDTH;
        return circleTransform;
    }

    public static GlideCircleTransform getInstance(Context Context,
                                                   int color, int width) {
        if (circleTransform == null) {
            circleTransform = new GlideCircleTransform(Context);
        }
        mBorderColor = color;
        mBorderWidth = width;
        return circleTransform;
    }

    public GlideCircleTransform(Context context) {
        super(context);
    }

    protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
                               int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null)
            return null;
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Paint mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        float borderRadius = r - mBorderWidth / 2;
        canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        return result;
    }


    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}