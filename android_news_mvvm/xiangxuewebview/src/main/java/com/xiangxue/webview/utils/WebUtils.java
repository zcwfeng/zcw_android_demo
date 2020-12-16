package com.xiangxue.webview.utils;

import android.content.Context;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebUtils {

    public static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static boolean isNotNull(List list) {
        return list != null && list.size() > 0;
    }

    public static boolean isNotNull(Set set) {
        return set != null && set.size() > 0;
    }

    public static boolean isNotNull(Map map) {
        return map != null && map.size() > 0;
    }

}
