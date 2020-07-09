package top.zcwfeng.lib;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class SkinResources {

    private String mSkinPkgName;
    private boolean isDefaultSkin = true;

    // app原始的resource
    private Resources mAppResources;
    // 皮肤包的resource
    private Resources mSkinResources;

    private SkinResources(Context context) {
        mAppResources = context.getResources();
    }

    public void applySkin(Resources resources, String pkgName) {
        mSkinResources = resources;
        mSkinPkgName = pkgName;
        //是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }


    //单例
    private volatile static SkinResources instance;
    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }
    public void reset() {
        mSkinResources = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }


    /**
     * 1.通过原始app中的resId(R.color.XX)获取到自己的 名字
     * 2.根据名字和类型获取皮肤包中的ID
     */
    public int getIdentifier(int resId){
        if(isDefaultSkin){
            return resId;
        }
        String resName=mAppResources.getResourceEntryName(resId);
        String resType=mAppResources.getResourceTypeName(resId);
        int skinId=mSkinResources.getIdentifier(resName,resType,mSkinPkgName);
        return skinId;
    }

    /**
     * 输入主APP的ID，到皮肤APK文件中去找到对应ID的颜色值
     * @param resId
     * @return
     */
    public int getColor(int resId){
        if(isDefaultSkin){
            return mAppResources.getColor(resId);
        }
        int skinId=getIdentifier(resId);
        if(skinId==0){
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(skinId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    public Drawable getDrawable(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        //通过 app的resource 获取id 对应的 资源名 与 资源类型
        //找到 皮肤包 匹配 的 资源名资源类型 的 皮肤包的 资源 ID
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }


    /**
     * 可能是Color 也可能是drawable
     *
     * @return
     */
    public Object getBackground(int resId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resId);

        if ("color".equals(resourceTypeName)) {
            return getColor(resId);
        } else {
            // drawable
            return getDrawable(resId);
        }
    }
}
