package com.example.android_fresco.image;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchyInflater;
import com.facebook.drawee.view.DraweeView;

/**
 * DraweeView that uses GenericDraweeHierarchy.
 *
 * The hierarchy can be set either programmatically or inflated from XML.
 * See {@link GenericDraweeHierarchyInflater} for supported XML attributes.
 *
 * 重写 SimpleDraweeView ,Catch 掉极端机型的爆机
 */
public class MeMeGenericDraweeView extends DraweeView<GenericDraweeHierarchy> {

    public MeMeGenericDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context);

        try {
            setHierarchy(hierarchy);
        } catch (Throwable throwable) {
//            Timber.e(throwable,FrescoConstants.TAG_FORMAT ,"MeMeGenericDraweeView setHierarchy error");
        }
    }

    public MeMeGenericDraweeView(Context context) {
        super(context);
        inflateHierarchy(context, null);
    }

    public MeMeGenericDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateHierarchy(context, attrs);
    }

    public MeMeGenericDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflateHierarchy(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeMeGenericDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateHierarchy(context, attrs);
    }

    protected void inflateHierarchy(Context context, @Nullable AttributeSet attrs) {
        try {
            GenericDraweeHierarchyBuilder builder =
                    GenericDraweeHierarchyInflater.inflateBuilder(context, attrs);
            setAspectRatio(builder.getDesiredAspectRatio());
            setHierarchy(builder.build());
        } catch (Throwable throwable) {
//            Timber.e(throwable,FrescoConstants.TAG_FORMAT ,"MeMeGenericDraweeView inflateHierarchy error");
        }
    }
}
