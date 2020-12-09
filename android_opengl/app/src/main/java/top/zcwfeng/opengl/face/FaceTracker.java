package top.zcwfeng.opengl.face;

public class FaceTracker {
    static{
        System.loadLibrary("FaceTracker");
    }

    private long mNativeObj = 0;

    public FaceTracker(String faceModel, String landmarkerModel) {
        mNativeObj = nativeCreateObject(faceModel, landmarkerModel);
    }


    public void release() {
        nativeDestroyObject(mNativeObj);
        mNativeObj = 0;
    }

    public void start() {
        nativeStart(mNativeObj);
    }

    public void stop() {
        nativeStop(mNativeObj);
    }

    public Face detect(byte[] inputImage, int width, int height, int rotationDegrees) {
        return nativeDetect(mNativeObj, inputImage, width, height, rotationDegrees);
    }




    private static native long nativeCreateObject(String faceModel, String landmarkerModel);

    private static native void nativeDestroyObject(long thiz);

    private static native void nativeStart(long thiz);

    private static native void nativeStop(long thiz);

    private static native Face nativeDetect(long thiz, byte[] inputImage, int width, int height, int rotationDegrees);
}
