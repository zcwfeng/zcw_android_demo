package top.zcwfeng.zcwffmpeg;

public class FFMpegUtils {

    static {
        System.loadLibrary("ffmpegutils-lib");
    }

    public static native void logMetada(String filePath);
}
