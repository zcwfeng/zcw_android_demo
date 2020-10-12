package com.enjoy.enjoymmkv;

import android.content.Context;


public class MMKV {


    static {
        System.loadLibrary("mmkv");
    }

    private static String rootDir;
    private final long nativeHandle;

    public MMKV(long handle) {
        nativeHandle = handle;
    }

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
        long handle = getDefaultMMKV();
        return new MMKV(handle);
    }




    public int getInt(String key, int defaultValue) {
        return getInt(nativeHandle, key, defaultValue);
    }

    public void putInt(String key, int value) {
        putInt(nativeHandle, key, value);
    }

    private static native void jniInitialize(String rootDir);

    private static native long getDefaultMMKV();

    private static native int getInt(long nativeHandle, String key, int defaultValue);

    private static native void putInt(long nativeHandle, String key, int value);

}
