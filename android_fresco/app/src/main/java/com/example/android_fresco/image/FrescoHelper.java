package com.example.android_fresco.image;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.animation.drawable.AnimationListener;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ImageDecodeOptionsBuilder;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imageutils.BitmapUtil;

import java.io.File;
import java.util.List;


public class FrescoHelper {
    private Uri mUri;
    private RotationOptions mRotationOptions;
    private Priority mRequestPriority;
    private boolean mAutoPlayingAnimation = true;
    private int resizeWidth;
    private int resizeHeight;
    private float maxBitmapSize = BitmapUtil.MAX_BITMAP_SIZE;
    private int blurRadius = -1;
    private boolean resizeByViewActualSize;
    private DraweeView genericDraweeView;
    private GenericDraweeHierarchy mHierarchy;
    private boolean forceStaticImage = false;
    private boolean printLog;
    private FrescoLoadCallback<ImageInfo> mLoadCallback;
    private BaseControllerListener<ImageInfo> baseControllerListener;
    private AnimationListener mAnimationListener;


    public static FrescoHelper create(DraweeView simpleDraweeView) {
        return new FrescoHelper(simpleDraweeView);
    }

    public static FrescoHelper create(DraweeView simpleDraweeView, ScalingUtils.ScaleType st) {
        FrescoHelper ret = new FrescoHelper(simpleDraweeView);
        ret.actualImageScaleType(st);
        return ret;
    }

    public static FrescoHelper createFitCenter(DraweeView simpleDraweeView) {
        FrescoHelper ret = new FrescoHelper(simpleDraweeView);
        ret.actualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        return ret;
    }

    public static FrescoHelper create() {
        return new FrescoHelper();
    }

    private FrescoHelper(DraweeView draweeView) {
        genericDraweeView = draweeView;
        if (genericDraweeView != null) {
            try {
                if (genericDraweeView instanceof SimpleDraweeView) {
                    SimpleDraweeView simpleDraweeView = (SimpleDraweeView) genericDraweeView;
                    mHierarchy = simpleDraweeView.getHierarchy();
                }else if (genericDraweeView instanceof MeMeDraweeView) {
                    MeMeDraweeView meMeDraweeView = (MeMeDraweeView) genericDraweeView;
                    mHierarchy = meMeDraweeView.getHierarchy();
                }
            }catch (Throwable throwable) {
                Log.e("zcw"," FrescoHelper  getHierarchy error");
            }

        }

        if (mHierarchy == null) {
            GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(null);
            mHierarchy = hierarchyBuilder.build();
        }

        makeDefaultConfig();
    }


    private FrescoHelper() {
        makeDefaultConfig();
    }

    private void makeDefaultConfig() {
        if (mHierarchy != null) {
            //default hierarchy config
            mHierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
            mHierarchy.setActualImageFocusPoint(new PointF(0.5f, 0f));
            mHierarchy.setFadeDuration(300);
        }

        mRotationOptions = RotationOptions.autoRotate();
        resizeByViewActualSize = true;
        mLoadCallback = null;
        baseControllerListener = null;
        resizeWidth = -1;
        resizeHeight = -1;
    }

    /**
     * 替换域名
     * @param path
     * @return
     */
    public static String replaceSrc(String path){
        if (TextUtils.isEmpty(path))return path;
//        SystemConfig config = SettingsHandler.getConfig();
//        if (config!=null && config.configData!=null && config.configData.cdnShift!=null){
//            if (!TextUtils.isEmpty(config.configData.cdnShift.destUrl)){
//                path = path.replaceAll(config.configData.cdnShift.srcUrl,config.configData.cdnShift.destUrl);
//            }
//        }
        return path;
    }

    public void load(String path) {
        path = replaceSrc(path);
        makeUri(path);
        if(TextUtils.isEmpty(path)) return;
        if (genericDraweeView != null) {
            display();
        }else {
            throw new NullPointerException("SimpleDraweeView should not be null");
        }
    }

    public void load(Uri uri) {
        String path = replaceSrc(uri.toString());
        mUri = Uri.parse(path);
        if (genericDraweeView != null) {
            display();
        }else {
            throw new NullPointerException("SimpleDraweeView should not be null");
        }
    }

    /**
     * 设置高斯模糊半径 越大越模糊
     * @param blurRadius 必须大于0
     * @return
     */
    public FrescoHelper setBlurRadius(int blurRadius){
        this.blurRadius = blurRadius;
        return this;
    }

    private void makeUri(String path) {
        if (path == null) return;

        Uri uri = UriUtil.parseUriOrNull(path);

        if (UriUtil.isNetworkUri(uri)) {
            //make scaled url from scale server

            mUri = uri;
        } else /*if (UriUtil.isLocalFileUri(uri))*/ {
            mUri = UriUtil.getUriForFile(new File(path));
        }
    }

    public static Uri toUri(String path) {
        if (path == null) return null;

        Uri uri = UriUtil.parseUriOrNull(path);

        if (UriUtil.isNetworkUri(uri)) {
            //make scaled url from scale server

            return uri;
        } else /*if (UriUtil.isLocalFileUri(uri))*/ {
            return UriUtil.getUriForFile(new File(path));
        }
    }

    public void load(@DrawableRes int resId) {
        mUri = UriUtil.getUriForResourceId(resId);
        if (genericDraweeView != null) {
            display();
        } else {
            throw new NullPointerException("SimpleDraweeView should not be null");
        }
    }


    public FrescoHelper fadeDuration(int duration) {
        if (mHierarchy != null) {
            mHierarchy.setFadeDuration(duration);
        }
        return this;
    }


    public FrescoHelper resizeImage(int dstWidth, int dstHeight) {
        resizeWidth = dstWidth;
        resizeHeight = dstHeight;
        resizeByViewActualSize = false;
        return this;
    }

    public FrescoHelper resizeImage(int dst) {
        resizeWidth = resizeHeight = dst;
        resizeByViewActualSize = false;
        return this;
    }

    public FrescoHelper resizeImage(int dstWidth, int dstHeight,float maxBitmapSize) {
        resizeWidth = dstWidth;
        resizeHeight = dstHeight;
        this.maxBitmapSize = maxBitmapSize;
        resizeByViewActualSize = false;
        return this;
    }

    public FrescoHelper forceStaticImage(boolean forceStaticImage){
        this.forceStaticImage = forceStaticImage;
        return this;
    }

    /**
     * 请使用 {@link #resizeImageByViewActualSize(boolean)}
     */
    @Deprecated
    public FrescoHelper resizeByViewActualSize() {
        resizeByViewActualSize = true;
        return this;
    }

    public FrescoHelper resizeImageByViewActualSize(boolean value) {
        resizeByViewActualSize = value;
        return this;
    }

    public FrescoHelper rotateOptions(RotationOptions rotationOptions) {
        mRotationOptions = rotationOptions;
        return this;
    }

    public FrescoHelper autoPlayAnimations(boolean autoPlayingAnimation) {
        mAutoPlayingAnimation = autoPlayingAnimation;
        return this;
    }

    public FrescoHelper requestPriority(Priority priority) {
        mRequestPriority = priority;
        return this;
    }

    public FrescoHelper placeHolderImage(Drawable drawable) {
        if (mHierarchy != null) {
            mHierarchy.setPlaceholderImage(drawable);
        }
        return this;
    }

    public FrescoHelper actualImageFocusPoint(PointF pointF) {
        if (mHierarchy != null) {
            mHierarchy.setActualImageFocusPoint(pointF);
        }
        return this;
    }

    public FrescoHelper actualImageScaleType(ScalingUtils.ScaleType scaleType) {
        if (mHierarchy != null) {
            mHierarchy.setActualImageScaleType(scaleType);
        }
        return this;
    }

    public FrescoHelper placeHolderImage(Drawable drawable, ScalingUtils.ScaleType scaleType) {
        if (mHierarchy != null) {
            mHierarchy.setPlaceholderImage(drawable, scaleType);
        }
        return this;
    }

    public FrescoHelper placeHolderImage(@DrawableRes int resourceId) {
        if (mHierarchy != null) {
            mHierarchy.setPlaceholderImage(resourceId);
        }
        return this;
    }

    public FrescoHelper placeHolderImage(@DrawableRes int resourceId, ScalingUtils.ScaleType scaleType) {
        if (mHierarchy != null) {
            mHierarchy.setPlaceholderImage(resourceId, scaleType);
        }
        return this;
    }

    public FrescoHelper failureImage(Drawable drawable) {
        if (mHierarchy != null) {
            mHierarchy.setFailureImage(drawable);
        }
        return this;
    }

    public FrescoHelper failureImage(Drawable drawable, ScalingUtils.ScaleType scaleType) {
        if (mHierarchy != null) {
            mHierarchy.setFailureImage(drawable, scaleType);
        }
        return this;
    }

    public FrescoHelper failureImage(@DrawableRes int resourceId) {
        if (mHierarchy != null) {
            mHierarchy.setFailureImage(resourceId);
        }
        return this;
    }

    public FrescoHelper failureImage(@DrawableRes int resourceId, ScalingUtils.ScaleType scaleType) {
        if (mHierarchy != null) {
            try {
                mHierarchy.setFailureImage(resourceId, scaleType);
            } catch (Exception e) {
            }
        }
        return this;
    }

    public FrescoHelper backgroundImage(Drawable drawable) {
        if (mHierarchy != null) {
            mHierarchy.setBackgroundImage(drawable);
        }
        return this;
    }

    public FrescoHelper overlayImage(Drawable drawable) {
        if (mHierarchy != null) {
            mHierarchy.setOverlayImage(drawable);
        }
        return this;
    }

    public FrescoHelper roundingParams(RoundingParams roundingParams) {
        if (mHierarchy != null) {
            mHierarchy.setRoundingParams(roundingParams);
        }
        return this;
    }

    public FrescoHelper aspectRatio(float aspectRatio) {
        if (genericDraweeView != null) {
            genericDraweeView.setAspectRatio(aspectRatio);
        }
        return this;
    }

    public FrescoHelper printLog() {
        this.printLog = true;
        return this;
    }

    public FrescoHelper loadCallback(FrescoLoadCallback<ImageInfo> loadCallback) {
        mLoadCallback = loadCallback;
        return this;
    }

    public FrescoHelper controllerListener(BaseControllerListener<ImageInfo> controllerListener) {
        baseControllerListener = controllerListener;
        return this;
    }

    public FrescoHelper animationListener(AnimationListener mAnimationListener) {
        this.mAnimationListener = mAnimationListener;
        return this;
    }

    private void setImageURI(Uri uri) {
        if (genericDraweeView instanceof SimpleDraweeView) {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) genericDraweeView;
            simpleDraweeView.setImageURI(uri);
        }else if (genericDraweeView instanceof MeMeDraweeView) {
            MeMeDraweeView meMeDraweeView = (MeMeDraweeView) genericDraweeView;
            meMeDraweeView.setImageError();
        }
    }

    private DraweeController getController() {
        if (genericDraweeView instanceof SimpleDraweeView) {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) genericDraweeView;
            return simpleDraweeView.getController();
        }else if (genericDraweeView instanceof MeMeDraweeView) {
            MeMeDraweeView meMeDraweeView = (MeMeDraweeView) genericDraweeView;
            return meMeDraweeView.getController();
        }

        return null;
    }

    public void setController(DraweeController controller) {
        if (genericDraweeView instanceof SimpleDraweeView) {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) genericDraweeView;
            simpleDraweeView.setController(controller);
        }else if (genericDraweeView instanceof MeMeDraweeView) {
            MeMeDraweeView meMeDraweeView = (MeMeDraweeView) genericDraweeView;
            meMeDraweeView.setController(controller);
        }
    }

    private void display() {
        if (genericDraweeView == null) {
            return;
        }
        if (mUri == null) {
            setImageURI(mUri);
            return;
        }

        printLog("display image, url " + mUri.toString());

        final ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(mUri);
        if (mRequestPriority != null) {
            requestBuilder.setRequestPriority(mRequestPriority);
        }
        if (mRotationOptions != null) {
            requestBuilder.setRotationOptions(mRotationOptions);
        }
        if (blurRadius>0){
            requestBuilder.setPostprocessor(new IterativeBoxBlurPostProcessor(3, blurRadius));
        }
        requestBuilder.setLocalThumbnailPreviewsEnabled(true);
        if (forceStaticImage){
            ImageDecodeOptions imageDecodeOptions = new ImageDecodeOptionsBuilder().setForceStaticImage(true).build();
            requestBuilder.setImageDecodeOptions(imageDecodeOptions);
        }
        try{
            if (resizeByViewActualSize) {
                if (getWidth() <= 0 || getHeight() <= 0) {
                    genericDraweeView.post(new Runnable() {
                        @Override
                        public void run() {

                            printLog("resizeByViewActualSize w: " + genericDraweeView.getMeasuredWidth() + " h: " + genericDraweeView.getMeasuredHeight());
                            ResizeOptions resizeOptions = getWidth() > 0 && getHeight() > 0 ? new ResizeOptions(getWidth(), getHeight()) : null;
                            requestBuilder.setResizeOptions(resizeOptions);
                            setupDraweeView(requestBuilder.build());
                        }
                    });
                } else {

                    printLog("resizeByViewActualSize ,But no need wait for getting size, w: " + getWidth() + " h: " + getWidth());
                    ResizeOptions resizeOptions = getWidth() > 0 && getHeight() > 0 ? new ResizeOptions(getWidth(), getHeight()) : null;
                    requestBuilder.setResizeOptions(resizeOptions);
                    setupDraweeView(requestBuilder.build());
                }

            } else {
                printLog(" w: " + resizeWidth + " h: " + resizeHeight);
                ResizeOptions resizeOptions = resizeWidth > 0 && resizeHeight > 0 ? new ResizeOptions(resizeWidth, resizeHeight,maxBitmapSize) : null;
                requestBuilder.setResizeOptions(resizeOptions);
                setupDraweeView(requestBuilder.build());
            }
        }catch (Throwable e){
//            Timber.e(e,FrescoConstants.TAG_FORMAT," FrescoHelper  display error");
        }

    }

    private void setupDraweeView(ImageRequest request){
        try {
            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setOldController(getController())
                    .setImageRequest(request)
                    .setAutoPlayAnimations(mAutoPlayingAnimation)
                    .setControllerListener(getControllerListener())
                    .build();
            setController(controller);
        }catch (Throwable throwable) {
//            Timber.e(throwable,FrescoConstants.TAG_FORMAT," FrescoHelper  setup DraweeView error");
        }
    }

    @Nullable
    private ControllerListener<? super ImageInfo> getControllerListener() {
        if (baseControllerListener != null) {
            return baseControllerListener;
        }
        return mLoadCallback == null ? null : new DraweeControllerListener(mLoadCallback);
    }

    private int getWidth() {
        if (genericDraweeView == null) {
            return -1;
        }

        ViewGroup.LayoutParams layoutParams = genericDraweeView.getLayoutParams();
        if (layoutParams != null && layoutParams.width > 0) {
            return layoutParams.width;
        }
        return genericDraweeView.getWidth();
    }

    private int getHeight() {
        if (genericDraweeView == null) {
            return -1;
        }
        ViewGroup.LayoutParams layoutParams = genericDraweeView.getLayoutParams();
        if (layoutParams != null && layoutParams.height > 0) {
            return layoutParams.height;
        }

        return genericDraweeView.getHeight();
    }

    public void fetch(String path,final FrescoImageFetcher<Bitmap> imageFetcher) {
        path = replaceSrc(path);
        makeUri(path);
        fetch(imageFetcher);
    }

    public void fetch(@DrawableRes int resId,final FrescoImageFetcher<Bitmap> imageFetcher) {
        mUri = UriUtil.getUriForResourceId(resId);
        fetch(imageFetcher);
    }

    public void fetch(final FrescoImageFetcher<Bitmap> imageFetcher) {

        if (imageFetcher == null) {
            return;
        }

        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(mUri);
        if (mRequestPriority != null) {
            requestBuilder.setRequestPriority(mRequestPriority);
        }
        if (mRotationOptions != null) {
            requestBuilder.setRotationOptions(mRotationOptions);
        }
        if (resizeWidth > 0 && resizeHeight > 0) {
            requestBuilder.setResizeOptions(new ResizeOptions(resizeWidth, resizeHeight));
        }
        try {
            ImageRequest imageRequest = requestBuilder.build();
            DataSource<CloseableReference<CloseableImage>>
                    dataSource = ImagePipelineFactory.getInstance().getImagePipeline().fetchDecodedImage(imageRequest, null);
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                                     @Override
                                     public void onNewResultImpl(@Nullable final Bitmap bitmap) {
                                         if (bitmap != null && !bitmap.isRecycled()) {
                                             Bitmap newBitmap = bitmap.copy(bitmap.getConfig(),false);
                                             imageFetcher.onSuccess(newBitmap);
                                             bitmap.recycle();
                                         }
                                     }

                                     @Override
                                     public void onCancellation(DataSource<CloseableReference<CloseableImage>> dataSource) {
                                         super.onCancellation(dataSource);
                                         imageFetcher.onCancel();
                                     }

                                     @Override
                                     public void onFailureImpl(DataSource dataSource) {
                                         Throwable throwable = null;
                                         if (dataSource != null) {
                                             throwable = dataSource.getFailureCause();
                                         }
                                         imageFetcher.onFailure(throwable);
                                     }
                                 },
                    UiThreadImmediateExecutorService.getInstance());
        }catch (Throwable throwable){
//            Timber.e(throwable,FrescoConstants.TAG_FORMAT," FrescoHelper  fetch error");
        }

    }

    private void printLog(String log) {
            Log.d("FrescoHelper", "    " + log);
    }


    public static void processTrimMemory(int level) {
        List<MemoryTrimmable> memoryTrimmableList = ImagePipelineConfigFactory.getMemoryTrimmableList();

        if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            processLowMemory();
        } else if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            if (memoryTrimmableList != null) {
                for (MemoryTrimmable trimmable : memoryTrimmableList) {
                    trimmable.trim(MemoryTrimType.OnSystemLowMemoryWhileAppInBackground);
                }
            }
        }

    }

    public static void processLowMemory() {
        try {
            Fresco.getImagePipeline().clearMemoryCaches();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
