package com.zhiwan.hamitao.im_module.chat.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GlideRoundTransform extends BitmapTransformation {
	private static float radius = 5f;

	private static GlideRoundTransform glideRoundTransform;

	public static GlideRoundTransform getInstance(Activity activity) {
		if (glideRoundTransform == null) {
			glideRoundTransform = new GlideRoundTransform(activity);
		}
		return glideRoundTransform;
	}

	public static GlideRoundTransform getInstance(Activity activity, int dp) {
		if (glideRoundTransform == null) {
			glideRoundTransform = new GlideRoundTransform(activity);
		}
		setRadius(dp);
		return glideRoundTransform;
	}

	public GlideRoundTransform(Context context) {
		this(context, 5);
	}

	public GlideRoundTransform(Context context, int dp) {
		super(context);
		radius = Resources.getSystem().getDisplayMetrics().density * dp;
	}

	private static void setRadius(int dp) {
		radius = Resources.getSystem().getDisplayMetrics().density * dp;
	}

	@Override
	protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
                               int outWidth, int outHeight) {
		return roundCrop(pool, toTransform);
	}

	private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
		if (source == null)
			return null;
		Bitmap result = pool.get(source.getWidth(), source.getHeight(),
				Bitmap.Config.ARGB_8888);
		if (result == null) {
			result = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
					Bitmap.Config.ARGB_8888);
		}
		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP,
				BitmapShader.TileMode.CLAMP));
		paint.setAntiAlias(true);
		RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
		canvas.drawRoundRect(rectF, radius, radius, paint);
		return result;
	}



	@Override
	public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

	}
}