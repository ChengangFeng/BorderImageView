package com.github.chengang.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by 陈岗不姓陈 on 2017/11/22.
 * <p>
 */

public class BorderImageView extends AppCompatImageView {

    private BorderDrawable mBorderDrawable;

    public BorderImageView(Context context) {
        this(context, null);
    }

    public BorderImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BorderImageView);
        int startColor = typedArray.getColor(R.styleable.BorderImageView_start_color, Color.parseColor("#E91E63"));
        int endColor = typedArray.getColor(R.styleable.BorderImageView_end_color, Color.parseColor("#2196F3"));
        int borderWidth = typedArray.getDimensionPixelOffset(R.styleable.BorderImageView_border_width, 15);
        typedArray.recycle();
        int[] colors = new int[]{endColor, startColor};
        int[][] states = new int[][]{{android.R.attr.state_pressed}, {}};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        mBorderDrawable = new BorderDrawable(colorStateList, borderWidth);
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
