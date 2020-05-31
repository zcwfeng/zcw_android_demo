package com.example.android_fresco.image;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.DefaultBitmapMemoryCacheParamsSupplier;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * 配置Fresco的 PipelineConfig
 */
public class ImagePipelineConfigFactory {


    private static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";

    private static ImagePipelineConfig sOkHttpImagePipelineConfig;


    private static List<MemoryTrimmable> sMemoryTrimmable;

    public static List<MemoryTrimmable> getMemoryTrimmableList() {
        return sMemoryTrimmable;
    }

    /**
     * Creates config using OkHttp as network backed.
     */
    public static ImagePipelineConfig getOkHttpImagePipelineConfig(Context context) {
        if (sOkHttpImagePipelineConfig == null) {
            OkHttpClient okHttpClient = OkHttp.newBuilder().build();
            ImagePipelineConfig.Builder configBuilder =
                    OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient);

            sMemoryTrimmable = new ArrayList<>();

            configureCaches(configBuilder, context);
            sOkHttpImagePipelineConfig = configBuilder.build();
        }
        return sOkHttpImagePipelineConfig;
    }


    /**
     * 根据不同机型，配置内存，磁盘缓存
     */
    private static void configureCaches(ImagePipelineConfig.Builder configBuilder, Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final BitmapMemoryCacheParamsSupplier memoryCacheParamsSupplier = new BitmapMemoryCacheParamsSupplier(activityManager);

        configBuilder
                .setDownsampleEnabled(true)
                .setResizeAndRotateEnabledForNetwork(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setBitmapMemoryCacheParamsSupplier(memoryCacheParamsSupplier)
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(context)
                                .setBaseDirectoryPath(context.getApplicationContext().getCacheDir())
                                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                                .setMaxCacheSize(ConfigConstants.MAX_DISK_CACHE_SIZE)
                                .build())
                .setMemoryTrimmableRegistry(new MemoryTrimmableRegistry() {
                    @Override
                    public void registerMemoryTrimmable(MemoryTrimmable trimmable) {
                        sMemoryTrimmable.add(trimmable);
                    }

                    @Override
                    public void unregisterMemoryTrimmable(MemoryTrimmable trimmable) {
                        sMemoryTrimmable.remove(trimmable);
                    }
                });
    }


    /**
     * MemoryCacheParams(
     * )
     *
     * p1: 内存缓存中总图片的最大大小，以字节为单位
     * p2: 内存缓存中图片的最大数量
     * p3: 内存缓存中准备清除，但是尚未被删除的总图片最大大小，以字节为单位
     * p4: 内存缓存中准备清除的总图片最大数量
     * p5: 内存缓存中单个图片的最大大小
     *
     */

    public static class BitmapMemoryCacheParamsSupplier extends DefaultBitmapMemoryCacheParamsSupplier {
        private static final int MAX_CACHE_ENTRIES = 56;
       // private static final int MAX_CACHE_ASHM_ENTRIES = 128;
        private static final int MAX_CACHE_EVICTION_SIZE = 5 * ByteConstants.MB;
        private static final int MAX_CACHE_EVICTION_ENTRIES = 5;
        private static final int MAX_CACHE_ENTRY_SIZE = 10 * ByteConstants.MB;
        private final ActivityManager mActivityManager;

        public BitmapMemoryCacheParamsSupplier(ActivityManager activityManager) {
            super(activityManager);
            mActivityManager = activityManager;
        }

        @Override
        public MemoryCacheParams get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return new MemoryCacheParams(
                        getMaxCacheSize(),
                        MAX_CACHE_ENTRIES,
                        MAX_CACHE_EVICTION_SIZE,
                        MAX_CACHE_EVICTION_ENTRIES,
                        MAX_CACHE_ENTRY_SIZE);
            } else {
                return super.get();
            }
        }

        private int getMaxCacheSize() {
            final int maxMemory =
                    Math.min(mActivityManager.getMemoryClass() * ByteConstants.MB, Integer.MAX_VALUE);
            if (maxMemory < 32 * ByteConstants.MB) {
                return 4 * ByteConstants.MB;
            } else if (maxMemory < 64 * ByteConstants.MB) {
                return 6 * ByteConstants.MB;
            } else {
                return maxMemory / 4;
            }
        }


    }


    public static class ConfigConstants {

        //最大堆内存
        public static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

        //最大磁盘缓存
        public static final int MAX_DISK_CACHE_SIZE = 40 * ByteConstants.MB;

        //最大内存缓存
        public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 15;

        //最小内存缓存
        public static final int MIN_MEMORY_CACHE_SIZE = 5 * ByteConstants.MB;
    }

    public class ByteConstants {

        public static final int KB = 1024;
        public static final int MB = 1024 * KB;
        public static final int GB = 1024 * MB;

        private ByteConstants() {

        }
    }


}
