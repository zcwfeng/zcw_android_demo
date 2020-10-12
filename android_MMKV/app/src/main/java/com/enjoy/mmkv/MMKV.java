package com.enjoy.mmkv;

import android.content.Context;


/**
 * @author Lance
 * @date 2019/1/23
 */
public class MMKV {

    static {
        System.loadLibrary("native-lib");
    }

    static public final int SINGLE_PROCESS_MODE = 0x1;

    static public final int MULTI_PROCESS_MODE = 0x2;

    static private String rootDir = null;

    private long nativeHandle;

    private MMKV(long handle) {
        nativeHandle = handle;
    }

    /**
     * 初始化方法 创建mmkv文件目录
     *
     * @param context
     * @return
     */
    public static String initialize(Context context) {
        String rootDir = context.getFilesDir().getAbsolutePath() + "/mmkv";
        return initialize(rootDir);
    }

    public static String initialize(String rootDir) {
        MMKV.rootDir = rootDir;
        jniInitialize(MMKV.rootDir);
        return rootDir;
    }

    public static MMKV defaultMMKV() {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        }
        long handle = getDefaultMMKV(SINGLE_PROCESS_MODE);
        return new MMKV(handle);
    }

    public static MMKV defaultMMKV(int mode) {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        }
        long handle = getDefaultMMKV(mode);
        return new MMKV(handle);
    }

    public static MMKV mmkvWithID(String mmapID) {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        }

        long handle = getMMKVWithID(mmapID, SINGLE_PROCESS_MODE);
        return new MMKV(handle);
    }

    public static MMKV mmkvWithID(String mmapID, int mode) {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        }

        long handle = getMMKVWithID(mmapID, mode);
        return new MMKV(handle);
    }


    public void putInt(String key, int value) {
        putInt(nativeHandle, key, value);
    }


    public int getInt(String key, int defaultValue) {
        return getInt(nativeHandle, key, defaultValue);
    }

    public void putLong(String key, long value) {
        putLong(nativeHandle, key, value);
    }


    public long getLong(String key, long defaultValue) {
        return getLong(nativeHandle, key, defaultValue);
    }

    public void putFloat(String key, float value) {
        putFloat(nativeHandle, key, value);
    }


    public float getFloat(String key, float defaultValue) {
        return getFloat(nativeHandle, key, defaultValue);
    }


    private static native void jniInitialize(String rootDir);

    private static native long getDefaultMMKV(int mode);

    private static native long getMMKVWithID(String mmapID, int mode);

    private native void putInt(long handle, String key, int value);

    private native int getInt(long handle, String key, int defaultValue);


    private native void putLong(long handle, String key, long value);

    private native long getLong(long handle, String key, long defaultValue);

    private native void putFloat(long handle, String key, float value);

    private native float getFloat(long handle, String key, float defaultValue);

}
