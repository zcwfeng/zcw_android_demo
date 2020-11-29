package top.zcwfeng.zcwplugin;

import android.content.Context;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

public
class LoadUtil {
    private final static String apkPath = "/sdcard/plugin-debug.apk";

    public static void load(Context context) {
        /**
         * 宿主dexElements = 宿主dexElements + 插件dexElements
         *
         * 1.获取宿主dexElements
         * 2.获取插件dexElements
         * 3.合并两个dexElements
         * 4.将新的dexElements 赋值到 宿主dexElements
         *
         * 目标：dexElements  -- DexPathList类的对象 -- BaseDexClassLoader的对象，类加载器
         *
         * 获取的是宿主的类加载器  --- 反射 dexElements  宿主
         *
         * 获取的是插件的类加载器  --- 反射 dexElements  插件
         */

        try {
            Class<?> clazz = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = clazz.getDeclaredField("pathList");// 只和类有关和对象无关
            pathListField.setAccessible(true);

            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            // 宿主的类加载器
            ClassLoader pathClassLoader = context.getClassLoader();
            // DexPathList 类对象
            Object hostPathList = pathListField.get(pathClassLoader);
            // 宿主的dexElements
            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);


            // plugin的类加载器
            ClassLoader dexClassLoader = new DexClassLoader(apkPath,
                    context.getCacheDir().getAbsolutePath(),
                    null
                    , pathClassLoader);//parent 考虑适配问题，不要传null

            // DexPathList 类对象
            Object pluginPathList = pathListField.get(dexClassLoader);
            // plugin的dexElements
            Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);


            //将新的dexElements 赋值到 宿主dexElements
            // 不能直接Object[] obj = new Object[] 因为我们要把obj放到反射的elements里面去，所以不行
            Object[] newDexElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(),
                    hostDexElements.length + pluginDexElements.length);

            System.arraycopy(hostDexElements, 0, newDexElements, 0,
                    hostDexElements.length);
            System.arraycopy(pluginDexElements, 0, newDexElements, hostDexElements.length,
                    pluginDexElements.length);

            //赋值
            dexElementsField.set(hostPathList, newDexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
