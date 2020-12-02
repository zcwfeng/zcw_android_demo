package top.zcwfeng.zcwplugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;

public
class LoadUtil {
    private final static String apkPath = "/sdcard/plugin-debug.apk";

    private final static String apkFixPath = "/sdcard/patch.jar";

    /*
        制作补丁包流程:
        1、把Bug修复掉后，先生成类的class文件。
        2、执行命令：EnjoyFix\app\build\intermediates\classes\debug>dx --dex --output=patch.jar com/enjoy/enjoyfix/Utils.class

        应用补丁包： patchElment（补丁包生成的） + oldElement(APK原有的) 赋值给oldElement
        1、获取程序的PathClassLoader对象
        2、反射获得PathClassLoader父类BaseDexClassLoader的pathList对象
        3、反射获取pathList的dexElements对象 （oldElement）
        4、把补丁包变成Element数组：patchElement（反射执行makePathElements）
        5、合并patchElement+oldElement = newElement （Array.newInstance）
        6、反射把oldElement赋值成newElement

        makePathElements参数：
        1、补丁包：List[new File("/sdcard/patch.jar")]
        2、optimizedDirectory 传一个私有目录就行比如：context.getCacheDir()
        3、ArrayList<IOException> suppressedExceptions = new ArrayList<IOException>();
     */
    @SuppressLint("DiscouragedPrivateApi")
    public static void loadFix(Context context) {
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

            Method makePathElements = dexPathListClass.getDeclaredMethod("makePathElements", List.class, File.class, List.class);
            makePathElements.setAccessible(true);

            List<File> dexFile = new ArrayList<>();
            File file = new File(apkFixPath);
            dexFile.add(file);
            List<IOException> suppressException = new ArrayList<>();
            Object[] patchElements = (Object[]) makePathElements.invoke(hostPathList, dexFile, context.getCacheDir(), suppressException);

            //将新的dexElements 赋值到 宿主dexElements，不能直接Object[] obj = new Object[] 因为我们要把obj放到反射的elements里面去，所以不行
            Object[] newDexElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(),
                    hostDexElements.length + patchElements.length);

            System.arraycopy(patchElements, 0, newDexElements, 0,
                    patchElements.length);
            System.arraycopy(hostDexElements, 0, newDexElements, patchElements.length,
                    hostDexElements.length);
            //赋值
            dexElementsField.set(hostPathList, newDexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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

    /*
    源码
     protected @Nullable AssetManager createAssetManager(@NonNull final ResourcesKey key) {
        AssetManager assets = new AssetManager();

        // resDir can be null if the 'android' package is creating a new Resources object.
        // This is fine, since each AssetManager automatically loads the 'android' package
        // already.
        if (key.mResDir != null) {
            if (assets.addAssetPath(key.mResDir) == 0) {
                Log.e(TAG, "failed to add asset path " + key.mResDir);
                return null;
            }
        }
     */
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

    // TODO: 2020/12/1 热修复

    // so修复 nativeLibraryPathElement
    // 普通代码 dexElements
    // 资源修复 参考换肤


}
