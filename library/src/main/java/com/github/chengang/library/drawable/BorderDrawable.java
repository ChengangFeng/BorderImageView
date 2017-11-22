package com.github.chengang.library.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by 陈岗不姓陈 on 2017/11/22.
 * <p>
 */

public class BorderDrawable extends Drawable implements Animatable{

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect mRect = new Rect();
    private int mBorderWidth;
    private int mDefaultColor;
    private ColorStateList mColorStateList;

    public BorderDrawable(ColorStateList colorStateList, int borderWidth) {
        this.mBorderWidth = borderWidth;
        this.mColorStateList = colorStateList;
        mDefaultColor = mColorStateList.getDefaultColor();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mDefaultColor);
        mPaint.setStrokeWidth(borderWidth);
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    protected boolean onStateChange(int[] state) {
        int color = mColorStateList.getColorForState(state, mDefaultColor);
        if(color != mDefaultColor){
            mDefaultColor = color;
            mPaint.setColor(mDefaultColor);
            invalidateSelf();
            return true;
        }
        return false;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        final int halfBorderWith = mBorderWidth / 2;
        mRect.set(bounds.left + halfBorderWith, bounds.top + halfBorderWith, bounds.right - halfBorderWith, bounds.bottom - halfBorderWith);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRect(mRect, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
