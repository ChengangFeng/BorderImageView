package com.github.chengang.library;

import android.graphics.Color;

/**
 * Created by 陈岗不姓陈 on 2017/11/23.
 * <p>
 */

public class ColorUtil {

    /**
     * 取两个颜色间的渐变区间 中的某一点的颜色
     *
     * @param startColor 开始颜色
     * @param endColor   结束的颜色
     * @param radio      百分比浮点数
     * @return
     */
    public static int getMiddleColor(int startColor, int endColor, float radio) {
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);

        int red = (int) (redStart + ((redEnd - redStart) * radio + 0.5));
        int greed = (int) (greenStart + ((greenEnd - greenStart) * radio + 0.5));
        int blue = (int) (blueStart + ((blueEnd - blueStart) * radio + 0.5));
        return Color.argb(255, red, greed, blue);
    }

}
