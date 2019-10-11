package com.example.android_lifecycle.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DimenUtils {
    private static DisplayMetrics mMetrics = null;
    private static Resources mResource = null;

    private static final int DP_TO_PX = TypedValue.COMPLEX_UNIT_DIP;
    private static final int SP_TO_PX = TypedValue.COMPLEX_UNIT_SP;
    private static final int PX_TO_DP = TypedValue.COMPLEX_UNIT_MM + 1;
    private static final int PX_TO_SP = TypedValue.COMPLEX_UNIT_MM + 2;
    private static final int DP_TO_PX_SCALE_H = TypedValue.COMPLEX_UNIT_MM + 3;
    private static final int DP_SCALE_H = TypedValue.COMPLEX_UNIT_MM + 4;
    private static final int DP_TO_PX_SCALE_W = TypedValue.COMPLEX_UNIT_MM + 5;

    public static final int DENSITY_OTHER = -1;
    public static final int DENSITY_NULL = 0;
    public static final int DENSITY_LOW = 120;
    public static final int DENSITY_MEDIUM = 160;
    public static final int DENSITY_HIGH = 240;
    public static final int DENSITY_XHIGH = 320;
    public static final int DENSITY_XXHIGH = 480;
    public static final int DENSITY_XXXHIGH = 640;



    // -- dimens convert
    private static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case DP_TO_PX:
            case SP_TO_PX:
                return TypedValue.applyDimension(unit, value, metrics);
            case PX_TO_DP:
                return value / metrics.density;
            case PX_TO_SP:
                return value / metrics.scaledDensity;
            case DP_TO_PX_SCALE_H:
                return TypedValue.applyDimension(DP_TO_PX, value * getScaleFactorH(), metrics);
            case DP_SCALE_H:
                return value * getScaleFactorH();
            case DP_TO_PX_SCALE_W:
                return TypedValue.applyDimension(DP_TO_PX, value * getScaleFactorW(), metrics);
        }
        return 0;
    }

    public static int dp2px(float value) {
        if(!isInited()) {
            return (int)value;
        }
        return (int) applyDimension(DP_TO_PX, value, mMetrics);
    }

    public static float px2dp(float value) {
        if(!isInited()) {
            return (int)value;
        }
        return (int) applyDimension(PX_TO_DP, value, mMetrics);
    }

    public final static float BASE_SCREEN_WIDH = 720f;
    public final static float BASE_SCREEN_HEIGHT = 1280f;
    public final static float BASE_SCREEN_DENSITY = 2f;
    public static Float sScaleW, sScaleH;

    /**
     * 如果要计算的值已经经过dip计算，则使用此结果，如果没有请使用getScaleFactorWithoutDip
     */
    public static float getScaleFactorW() {
        if (sScaleW == null) {
            sScaleW = (getScreenWidth() * BASE_SCREEN_DENSITY) / (getDensity() * BASE_SCREEN_WIDH);
        }
        return sScaleW;
    }

    public static float getScaleFactorH() {
        if (sScaleH == null) {
            sScaleH = (getScreenHeight() * BASE_SCREEN_DENSITY)
                    / (getDensity() * BASE_SCREEN_HEIGHT);
        }
        return sScaleH;
    }

    public static int getScreenWidth() {
        if(!isInited()) {
            return 720;
        }
        return mMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        if(!isInited()) {
            return 1280;
        }
        return mMetrics.heightPixels;
    }

    public static float getScreenDensity() {
        if(!isInited()) {
            return 1.0f;
        }
        return mMetrics.density;
    }


    public static float getDensity() {
        if(!isInited()) {
            return 2;
        }
        return mMetrics.density;
    }

    public static int getScreenType() {
        if(!isInited()) {
            return DENSITY_NULL;
        }
        if (mMetrics.densityDpi > 240 && mMetrics.densityDpi <= 320) {
            return DENSITY_XHIGH;

        } else if (mMetrics.densityDpi > 320 && mMetrics.densityDpi <= 480) {
            return DENSITY_XXHIGH;

        } else if (mMetrics.densityDpi > 480 && mMetrics.densityDpi <= 640) {
            return DENSITY_XXXHIGH;

        } else if (mMetrics.densityDpi > 160 && mMetrics.densityDpi <= 240) {
            return DENSITY_HIGH;

        } else if (mMetrics.densityDpi > 120 && mMetrics.densityDpi <= 160) {
            return DENSITY_MEDIUM;

        } else if (mMetrics.densityDpi <= 120 && mMetrics.densityDpi > 0) {
            return DENSITY_LOW;

        } else if (mMetrics.densityDpi > 640) {
            return DENSITY_XXXHIGH;

        } else if (mMetrics.densityDpi <= 0) {
            return DENSITY_OTHER;
        }

        return DENSITY_NULL;

    }

    public static int getScreenLayoutSize(Context context) {
        final int screenSize = context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenSize;
    }

    public static void updateLayout(View view, int w, int h) {
        if (view == null)
            return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null)
            return;
        if (w != -3)
            params.width = w;
        if (h != -3)
            params.height = h;
        view.setLayoutParams(params);
    }

    public static void updateLayoutMargin(View view, int l, int t, int r, int b) {
        if (view == null)
            return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null)
            return;
        if (params instanceof RelativeLayout.LayoutParams) {
            updateMargin(view, (RelativeLayout.LayoutParams) params, l, t, r, b);
        } else if (params instanceof LinearLayout.LayoutParams) {
            updateMargin(view, (LinearLayout.LayoutParams) params, l, t, r, b);
        } else if (params instanceof FrameLayout.LayoutParams) {
            updateMargin(view, (FrameLayout.LayoutParams) params, l, t, r, b);
        }
    }

    private static void updateMargin(View view, ViewGroup.MarginLayoutParams params, int l, int t,
                                     int r, int b) {
        if (view == null)
            return;
        if (l != -3)
            params.leftMargin = l;
        if (t != -3)
            params.topMargin = t;
        if (r != -3)
            params.rightMargin = r;
        if (b != -3)
            params.bottomMargin = b;
        view.setLayoutParams(params);
    }

    public static int getWindowWidth() {
        if(!isInited()){
            return 1280;
        }
        return mMetrics.widthPixels;
    }

    public static int getWindowHeight() {
        if(!isInited()){
            return 720;
        }
        return mMetrics.heightPixels;
    }

    /**
     * 获取屏幕高度(px)
     */
    public static int getScanScreenHeight(Context context) {
        int screenHeight = -1;
        if (screenHeight == -1) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            screenHeight = dm.heightPixels;
        }
        return screenHeight;
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScanScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static int getStatusBarHeight(final Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int sp2px(float value) {
        if(null == mMetrics) {
            return (int)value;
        }
        return (int) applyDimension(SP_TO_PX, value, mMetrics);
    }

    public static int px2sp(float value) {
        if(null == mMetrics) {
            return (int)value;
        }
        return (int) applyDimension(PX_TO_SP, value, mMetrics);
    }

    public static int getContentHeight2(Context context) {
        int height = mMetrics.heightPixels - getStatusBarHeight(context);
        return height;
    }

    public static boolean isInited() {
        return (null != mMetrics);
    }

    @SuppressWarnings("infer")
    public synchronized static boolean initMetrics(Context context) {
        if(null != mMetrics) {
            return true;
        }
        if(null == context) {
            return false;
        }
        mResource = context.getResources();
        if(null == mResource) {
            return  false;
        }
        mMetrics = mResource.getDisplayMetrics();
        if(null != mMetrics) {
            return true;
        }
        return false;
    }

    public static int getIconHWByDensity(int density) {
        int hw = 96;
        //根据像素密度区间来
        if (density < DENSITY_MEDIUM) {
            hw = 32;
        } else if (density >= DENSITY_MEDIUM && density < DENSITY_HIGH) {
            hw = 48;
        } else if (density >= DENSITY_HIGH && density < DENSITY_XHIGH) {
            hw = 72;
        } else if (density >= DENSITY_XHIGH && density < DENSITY_XXHIGH) {
            hw = 96;
        } else if (density >= DENSITY_XXHIGH && density < DENSITY_XXXHIGH) {
            hw = 144;
        } else if (density >= DENSITY_XXXHIGH) {
            hw = 192;
        }
        return hw;
    }

    public static Rect getScreenSizeRect(Context context) {
        DisplayMetrics mMetrics = context.getResources()
                .getDisplayMetrics();
        Rect rect = new Rect(0, 0, mMetrics.widthPixels, mMetrics.heightPixels);
        return rect;
    }

    public static final Rect getTextBound(String text, float textSize) {
        Rect textBound = new Rect();
        Paint textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.SANS_SERIF);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.getTextBounds(text, 0, text.length(), textBound);
        return textBound;
    }

    /**
     * 获取屏幕英寸数
     * @return 屏幕英寸数
     */
    public static double getDiagonalInch(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;  // 屏幕宽度（像素）
        int height = context.getResources().getDisplayMetrics().heightPixels;  // 屏幕高度（像素）
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        double screenSize = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / densityDpi;
        return screenSize;
    }
}