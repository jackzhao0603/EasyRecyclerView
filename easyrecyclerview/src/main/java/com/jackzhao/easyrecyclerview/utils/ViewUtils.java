package com.jackzhao.easyrecyclerview.utils;

import android.content.Context;

public class ViewUtils {

    public static int sp2px(Context context, float spValue) {
        if (spValue == 0)
            return 0;
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        if (dipValue == 0)
            return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static float px2dp(Context context, int pxValue) {
        if (pxValue == 0)
            return 0;

        final float scale = context.getResources().getDisplayMetrics().density;
        return (float) ((pxValue) / scale);
    }
}
