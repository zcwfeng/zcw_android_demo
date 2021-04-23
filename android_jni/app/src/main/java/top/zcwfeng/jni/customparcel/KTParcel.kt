package top.zcwfeng.jni.customparcel

import top.zcwfeng.jni.customparcel.KTParcel.MyObject.KTPARCEL as _

class KTParcel private constructor(nativePtr: Long) {
    private var mNativePtr: Long = 0

    object MyObject {
        val KTPARCEL = KTParcel(0)
    }

    companion object {
        //        init {
//            System.loadLibrary("native-lib");
//        }
        fun obtain(): KTParcel = _
    }

    init {
        mNativePtr = nativePtr
        mNativePtr = nativeCreate()
    }

    private external fun nativeCreate(): Long
    private external fun nativeReadInt(nativePtr: Long): Int
    private external fun nativeSetDataPosition(nativePtr: Long, pos: Int)
    private external fun nativeWriteInt(nativePtr: Long, `val`: Int)

    fun readInt() = nativeReadInt(mNativePtr)
    fun setDataPosition(pos: Int) = nativeSetDataPosition(mNativePtr, pos)
    fun writeInt(value: Int) = nativeWriteInt(mNativePtr, value)

}