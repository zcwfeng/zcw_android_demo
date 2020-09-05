package top.zcwfeng.jni;

public
class BsPatchUtils {
//    static {
//        System.loadLibrary("bspatch_utlis");
//    }

    public static native int path(String oldApk,String newApk,String path);
}
