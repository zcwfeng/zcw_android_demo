package top.zcwfeng.customui.baseui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * An interaction behavior plugin for a child view of {@link CoordinatorLayout} to make it work as
 * a bottom sheet.
 * 用来让BottomSheetDialog支持Viewpager
 *
 * @author zcw
 */
public class ViewPagerBottomSheetBehavior<V extends View> extends BottomSheetBehavior<V> {
    public ViewPagerBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        super.onLayoutChild(parent, child, layoutDirection);
        //TODO 密切关注源码变化
        try {
            updateNestedScrollingChild(child);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void updateNestedScrollingChild(V child) throws Exception {
        Field ref = BottomSheetBehavior.class.getDeclaredField("mNestedScrollingChildRef");
        ref.setAccessible(true);
        ref.set(this, new WeakReference<>(findScrollingChild(child)));
    }

    /**
     * 重置可滚动的child,如果根view中方的是viewpager,page切换时必须调用此方法
     */
    public void invalidateScrollingChild() {
        try {
            Field ref = BottomSheetBehavior.class.getDeclaredField("mViewRef");
            ref.setAccessible(true);
            WeakReference<View> viewRef = (WeakReference<View>) ref.get(this);
            final View scrollingChild = findScrollingChild(viewRef.get());
            updateNestedScrollingChild((V) scrollingChild);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewPager) {
            ViewPager viewPager = (ViewPager) view;
            View currentViewPagerChild = viewPager.getChildAt(viewPager.getCurrentItem());
            if (currentViewPagerChild == null) {
                return null;
            }

            View scrollingChild = findScrollingChild(currentViewPagerChild);
            if (scrollingChild != null) {
                return scrollingChild;
            }
        } else if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                View scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }
}