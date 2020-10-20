# 流程步骤

1.
2.
3.

java 端

`GifFrame.java`

```Java
    private native long nativeGetFrame(long nativeHandle, Bitmap bitmap, int frameIndex);

    private static native GifFrame nativeDecodeStream(InputStream stream, byte[] buffer);

    private static native GifFrame nativeDecodeStreamJNI(AssetManager assertManager, String gifPath);

```

## jingraphics
JNI ： https://developer.android.google.cn/ndk/guides/stable_apis
文档： https://developer.android.google.cn/ndk/guides/stable_apis#jingraphics

```
libjnigraphics 库会公开允许访问 Java Bitmap 对象的像素缓冲区的 API。工作流如下：

调用 AndroidBitmap_getInfo() 以检索信息，例如指定位图句柄的宽度和高度。

调用 AndroidBitmap_lockPixels() 以锁定像素缓冲区并检索指向它的指针。这样做可确保像素在应用调用 AndroidBitmap_unlockPixels() 之前不会移动。

对像素缓冲区进行相应修改，以使其符合相应像素格式、宽度和其他特性。

调用 AndroidBitmap_unlockPixels() 以解锁缓冲区。

从 API 级别 8 开始提供。
```

## 读取Asset

https://developer.android.google.cn/ndk/reference/group/asset

## 打Log

```C++


```

## 解码

