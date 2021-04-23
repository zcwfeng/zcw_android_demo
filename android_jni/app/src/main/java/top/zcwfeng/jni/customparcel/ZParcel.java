package top.zcwfeng.jni.customparcel;

public
class ZParcel {
//    static  {
//        System.loadLibrary("native-lib");
//    }
    private long mNativePtr = 0; // used by native code

    public ZParcel(long nativePtr) {
//        mNativePtr = nativePtr;
        init(nativePtr);
    }

    public static ZParcel obtain() {
        return MyObject.ZPARCEL;
    }

    private void init(long nativePtr) {
        if (nativePtr != 0) {
            mNativePtr = nativePtr;
        } else {
            mNativePtr = nativeCreate();
        }
    }

    private static class MyObject{
        private static ZParcel ZPARCEL = new ZParcel(0);
    }

    public final int readInt() {
        return nativeReadInt(mNativePtr);
    }
    public final void setDataPosition(int pos) {
        nativeSetDataPosition(mNativePtr, pos);
    }

    public final void writeInt(int val) {
        nativeWriteInt(mNativePtr, val);
    }
    private static native long nativeCreate();
    private static native int nativeReadInt(long nativePtr);
    private static native void nativeSetDataPosition(long nativePtr, int pos);
    private static native void nativeWriteInt(long nativePtr, int val);

}
