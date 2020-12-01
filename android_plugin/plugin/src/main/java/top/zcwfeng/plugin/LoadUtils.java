package top.zcwfeng.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

public
class LoadUtils {

    private final static String apkPath = "/sdcard/plugin-debug.apk";

    private static Resources mResource;

    public static Resources getResources(Context context) {
        if (mResource == null) {
            mResource = loadResource(context);
        }
        return mResource;
    }

    public static Resources loadResource(Context context) {
        // assets.addAssetPath(key.mResDir) 源码
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            // 让assetManager 对象加载的资源是插件
            Method addAssetPathMethod = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, apkPath);

            Resources resources = context.getResources();
            // 加载插件资源Resource
            return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
