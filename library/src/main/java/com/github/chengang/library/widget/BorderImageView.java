package com.github.chengang.library.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.StateSet;

import com.github.chengang.library.drawable.BorderDrawable;

/**
 * Created by 陈岗不姓陈 on 2017/11/22.
 * <p>
 */

public class BorderImageView extends AppCompatImageView {

    private BorderDrawable mBorderDrawable;

    private static final ColorStateList DEFAULT_TINT = new ColorStateList(
            new int[][]{{-android.R.attr.state_enabled}, StateSet.WILD_CARD},
            new int[]{0x80FFFFFF, 0xFFFFFFFF}
    );

    public BorderImageView(Context context) {
        this(context, null);
    }

    public BorderImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        int[] colors = new int[]{Color.BLUE, Color.GREEN};
        int[][] states = new int[][]{{android.R.attr.state_pressed}, {}};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        mBorderDrawable = new BorderDrawable(colorStateList, 15);
        mBorderDrawable.setCallback(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBorderDrawable.setBounds(0, 0, w, h);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        mBorderDrawable.setState(getDrawableState());
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        mBorderDrawable.jumpToCurrentState();
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable dr) {
        return super.verifyDrawable(dr) || dr == mBorderDrawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBorderDrawable.draw(canvas);
    }
}
