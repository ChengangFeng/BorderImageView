package com.github.chengang.library;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by 陈岗不姓陈 on 2017/11/22.
 * <p>
 */

public class BorderDrawable extends Drawable implements Animatable {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect mRect = new Rect();
    private int mBorderWidth;
    private int mStartColor;
    private int mMiddleColor;
    private int mEndColor;
    private int mDuration = 700;
    private boolean isAnimateRunning = false;
    private long mAnimateStartTime;
    private ColorStateList mColorStateList;

    public BorderDrawable(ColorStateList colorStateList, int borderWidth) {
        this.mBorderWidth = borderWidth;
        this.mColorStateList = colorStateList;
        mStartColor = mColorStateList.getDefaultColor();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mStartColor);
        mPaint.setStrokeWidth(borderWidth);
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    protected boolean onStateChange(int[] state) {
        int color = mColorStateList.getColorForState(state, mStartColor);
        if (mEndColor != color) {
            if (mDuration > 0) {
                mStartColor = mMiddleColor;
                mEndColor = color;
                start();
            } else {
                mStartColor = color;
                mEndColor = color;
                invalidateSelf();
            }
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
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        //直接跳转到当前状态，所以取消动画执行
        stop();
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

    private final Runnable mUpdater = new Runnable() {
        @Override
        public void run() {
            update();
        }
    };

    private void update() {
        long curDuration = SystemClock.uptimeMillis() - mAnimateStartTime;
        float animateExecuteProgress = Math.min(1f, (float) curDuration / mDuration);
        mMiddleColor = ColorUtil.getMiddleColor(mStartColor, mEndColor, animateExecuteProgress);
        mPaint.setColor(mMiddleColor);
        if (1f != animateExecuteProgress) {
            scheduleSelf(mUpdater, SystemClock.uptimeMillis());
            invalidateSelf();
        } else {
            isAnimateRunning = false;
        }
    }

    @Override
    public void start() {
        //动画执行
        mAnimateStartTime = SystemClock.uptimeMillis();
        unscheduleSelf(mUpdater);
        scheduleSelf(mUpdater, SystemClock.uptimeMillis());
    }

    @Override
    public void stop() {
        //动画停止
        unscheduleSelf(mUpdater);
        invalidateSelf();
    }

    @Override
    public boolean isRunning() {
        return isAnimateRunning;
    }

    @Override
    public void scheduleSelf(@NonNull Runnable what, long when) {
        super.scheduleSelf(what, when);
        isAnimateRunning = true;
    }

    @Override
    public void unscheduleSelf(@NonNull Runnable what) {
        super.unscheduleSelf(what);
        isAnimateRunning = false;
    }

}
