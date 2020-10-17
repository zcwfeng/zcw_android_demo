package top.zcwfeng.giflib.gif;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

import java.io.InputStream;

public class GifFrame {
    static {
        System.loadLibrary("native-lib");
    }

    private int width;
    private int height;

    private int frameCount;
    //C++ 一帧的对象 指针
    long nativeHandle;

    private GifFrame(int width, int height, int frameCount, long nativeHandle) {
        this.width = width;
        this.height = height;
        this.frameCount = frameCount;
        this.nativeHandle = nativeHandle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFrameCount() {
        return frameCount;
    }

    private native long nativeGetFrame(long nativeHandle, Bitmap bitmap, int frameIndex);

    private static native GifFrame nativeDecodeStream(InputStream stream, byte[] buffer);

    private static native GifFrame nativeDecodeStreamJNI(AssetManager assertManager, String gifPath);

    /**
     * 获取每一帧时间
     * @param bitmap
     * @param frameIndex
     * @return frame 时长
     */
    public long getFrame(Bitmap bitmap, int frameIndex) {
        return nativeGetFrame(nativeHandle, bitmap, frameIndex);
    }

    public static GifFrame decodeStream(final Context context, final String fileName) {
        return nativeDecodeStreamJNI((context == null) ? null : context.getAssets(), fileName);
    }

    public static GifFrame decodeStream(InputStream stream) {
        byte[] buffer = new byte[16 * 1024];
        return nativeDecodeStream(stream, buffer);
    }
}
