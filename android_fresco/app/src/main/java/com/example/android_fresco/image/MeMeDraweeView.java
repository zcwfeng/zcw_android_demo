package com.example.android_fresco.image;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * This view takes a uri as input and internally builds and sets a controller.
 *
 * <p>This class must be statically initialized in order to be used. If you are using the Fresco
 * image pipeline, use {@link com.facebook.drawee.backends.pipeline.Fresco#initialize} to do this.
 *
 * 重写 SimpleDraweeView ,Catch 掉极端机型的爆机
 */
public class MeMeDraweeView extends MeMeGenericDraweeView {


    private static Supplier<? extends SimpleDraweeControllerBuilder> sDraweeControllerBuilderSupplier;

    /** Initializes {@link SimpleDraweeView} with supplier of Drawee controller builders. */
    public static void initialize(
            Supplier<? extends SimpleDraweeControllerBuilder> draweeControllerBuilderSupplier) {
        sDraweeControllerBuilderSupplier = draweeControllerBuilderSupplier;
    }

    /** Shuts {@link SimpleDraweeView} down. */
    public static void shutDown() {
        sDraweeControllerBuilderSupplier = null;
    }

    private SimpleDraweeControllerBuilder mSimpleDraweeControllerBuilder;

    public MeMeDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init(context, null);
    }

    public MeMeDraweeView(Context context) {
        super(context);
        init(context, null);
    }

    public MeMeDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MeMeDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeMeDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        try {
            Preconditions.checkNotNull(
                    sDraweeControllerBuilderSupplier,
                    "SimpleDraweeView was not initialized!");
            mSimpleDraweeControllerBuilder = sDraweeControllerBuilderSupplier.get();

            if (attrs != null) {
                TypedArray gdhAttrs = context.obtainStyledAttributes(
                        attrs,
                        com.facebook.drawee.R.styleable.SimpleDraweeView);
                try {
                    if (gdhAttrs.hasValue(com.facebook.drawee.R.styleable.SimpleDraweeView_actualImageUri)) {
                        setImageURI(
                                Uri.parse(gdhAttrs.getString(com.facebook.drawee.R.styleable.SimpleDraweeView_actualImageUri)),
                                null);
                    } else if (gdhAttrs.hasValue((com.facebook.drawee.R.styleable.SimpleDraweeView_actualImageResource))) {
                        int resId = gdhAttrs.getResourceId(
                                com.facebook.drawee.R.styleable.SimpleDraweeView_actualImageResource,
                                NO_ID);
                        if (resId != NO_ID) {
                            setActualImageResource(resId);
                        }
                    }
                } finally {
                    gdhAttrs.recycle();
                }
            }
        }catch (Throwable throwable) {
//            Timber.e(throwable,FrescoConstants.TAG_FORMAT ,"MeMeDraweeView init error");
        }

    }

    protected SimpleDraweeControllerBuilder getControllerBuilder() {
        return mSimpleDraweeControllerBuilder;
    }

    /**
     * Displays an image given by the uri.
     *
     * @param uri uri of the image
     * @undeprecate
     */
    @Override
    public void setImageURI(Uri uri) {
        setImageURI(uri, null);
    }

    /**
     * Displays an image given by the uri string.
     *
     * @param uriString uri string of the image
     */
    public void setImageURI(@Nullable String uriString) {
        setImageURI(uriString, null);
    }

    /**
     * Displays an image given by the uri.
     *
     * @param uri uri of the image
     * @param callerContext caller context
     */
    public void setImageURI(Uri uri, @Nullable Object callerContext) {
        /*try {
            DraweeController controller = mSimpleDraweeControllerBuilder
                    .setCallerContext(callerContext)
                    .setUri(uri)
                    .setOldController(getController())
                    .build();
            setController(controller);
        }catch (Throwable throwable){
            Timber.e(throwable,FrescoConstants.TAG_FORMAT ,"MeMeDraweeView setImageURI error");
        }*/
        FrescoHelper.create(this).resizeByViewActualSize().load(uri);
    }

    public void setImageError(){

        if (mSimpleDraweeControllerBuilder == null) return;

       try {
            DraweeController controller = mSimpleDraweeControllerBuilder
                    .setCallerContext(this)
                    .setUri((String)null)
                    .setOldController(getController())
                    .build();
            setController(controller);
        }catch (Throwable throwable){
//            Timber.e(throwable,FrescoConstants.TAG_FORMAT ,"MeMeDraweeView setImageError error");
        }
    }

    /**
     * Displays an image given by the uri string.
     *
     * @param uriString uri string of the image
     * @param callerContext caller context
     */
    public void setImageURI(@Nullable String uriString, @Nullable Object callerContext) {
        FrescoHelper.create(this).load(uriString);
    }

    /**
     * Sets the actual image resource to the given resource ID.
     *
     * Similar to {@link #setImageResource(int)}, this sets the displayed image to the given resource.
     * However, {@link #setImageResource(int)} bypasses all Drawee functionality and makes the view
     * act as a normal {@link android.widget.ImageView}, whereas this method keeps all of the
     * Drawee functionality, including the {@link com.facebook.drawee.interfaces.DraweeHierarchy}.
     *
     * @param resourceId the resource ID to use.
     */
    public void setActualImageResource(@DrawableRes int resourceId) {
        setActualImageResource(resourceId, null);
    }

    /**
     * Sets the actual image resource to the given resource ID.
     *
     * Similar to {@link #setImageResource(int)}, this sets the displayed image to the given resource.
     * However, {@link #setImageResource(int)} bypasses all Drawee functionality and makes the view
     * act as a normal {@link android.widget.ImageView}, whereas this method keeps all of the
     * Drawee functionality, including the {@link com.facebook.drawee.interfaces.DraweeHierarchy}.
     *
     * @param resourceId the resource ID to use.
     * @param callerContext caller context
     */
    public void setActualImageResource(@DrawableRes int resourceId, @Nullable Object callerContext) {
        setImageURI(UriUtil.getUriForResourceId(resourceId), callerContext);
    }

    /**
     * This method will bypass all Drawee-related functionality.
     * If you want to keep this functionality, take a look at {@link #setActualImageResource(int)}
     * and {@link #setActualImageResource(int, Object)}}.
     *
     * @param resId the resource ID
     */
    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        }catch (Throwable throwable){
//            Timber.e(throwable,FrescoConstants.TAG_FORMAT ,"MeMeDraweeView onDraw error");
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
