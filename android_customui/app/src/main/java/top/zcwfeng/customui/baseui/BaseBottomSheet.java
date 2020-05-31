package top.zcwfeng.customui.baseui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import top.zcwfeng.customui.BuildConfig;
import top.zcwfeng.customui.R;

/**
 * 底部滑出fragment,再次显示时不重建.且弹出的容器可装载不同的fragment,同时支持viewpager(原生的不支持viewpager中第一个列表以外的view滚动)
 */
public abstract class BaseBottomSheet extends BottomSheetDialogFragment {
    private static final String TAG = "BaseBottomSheet";
    private boolean mIsViewDestroyed;
    private boolean mIsDismissed;
    private boolean mIsShownByMe;
    private BottomSheetBehavior<View> mBehavior;

    public BaseBottomSheet() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), getTheme()) {
            @Override
            protected void onStart() {
                super.onStart();
                Window window = getWindow();
                if (window != null) {
                    if (fullScreen()) {
                        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }
                    window.setDimAmount(0);
                    if (getActivity() != null) {
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    }
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_bottom_sheet, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Fragment fragment = getFragment();
            if (fragment != null)
                getChildFragmentManager().beginTransaction().replace(R.id.bottom_sheet_place_holder, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果想把业务拆分到另一个 Fragment 里面，可以实现这个方法
     *
     * @return
     */
    public Fragment getFragment() {
        return null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            try {
                parent.setBackgroundColor(Color.TRANSPARENT);
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
                BottomSheetBehavior<View> customBehavior = getCustomBehavior();
                layoutParams.setBehavior(customBehavior);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mBehavior = BottomSheetBehavior.from(parent);
            mBehavior.setBottomSheetCallback(mBottomSheetCallback);
            mBehavior.setHideable(isCancelable());
            onBehavior(mBehavior);
        }
    }

    /**
     * 自定义behavior
     *
     * @return 默认返回 ViewPagerBottomSheetBehavior
     */
    @Nullable
    protected BottomSheetBehavior<View> getCustomBehavior() {
        try {
            return new ViewPagerBottomSheetBehavior<>(getContext(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * BottomSheetBehavior 创建时回调
     *
     * @param behavior
     */
    protected void onBehavior(BottomSheetBehavior<View> behavior) {

    }

    /**
     * dialog 取消时回调
     */
    protected void onDialogCancel() {
    }

    /**
     * 获取BottomSheetBehavior
     *
     * @return
     */
    protected BottomSheetBehavior<View> getBehavior() {
        return mBehavior;
    }

    /**
     * 是否需要全屏,全屏状态不显示通知栏
     *
     * @return true:全屏;false:不全屏
     */
    protected boolean fullScreen() {
        return false;
    }

    /**
     * remove current fragment from dialog.
     * default is false,
     *
     * @return if true,this fragment will remove from dialog,
     * else fragment will hide.
     */
    protected boolean removeSelfFromDialog() {
        return false;
    }

    @Override
    public void onStart() {
        //Fragment 如果是被hide,页面切换回来 这个方法还是会回调
        super.onStart();
        if (mBehavior != null) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        Dialog dialog = getDialog();
        if (dialog != null) {
            if (isAdded() && isHidden()) {
                dialog.dismiss();
            } else {
                dialog.setCanceledOnTouchOutside(true);
                Window window = dialog.getWindow();
                if (window != null) {
                    if (fullScreen()) {
                        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }
                    window.setDimAmount(windowDimAmount());
                    if (getActivity() != null) {
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    }
                }
            }
        }
    }

    /**
     * @return window dim amount
     */
    protected float windowDimAmount() {
        return 0;
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        if (mBehavior != null) {
            mBehavior.setHideable(cancelable);
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (getDialog() != null && !getDialog().isShowing()) {
            getDialog().show();
        }
        mIsDismissed = false;
        mIsShownByMe = true;
        FragmentTransaction ft = manager.beginTransaction();
        if (isAdded()) {
            ft.show(this);
            if (mBehavior != null && mBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {//如果是滑动隐藏的,由于view不会重新初始化,因此再次显示的时候需要手动将view再滚出来.STATE_COLLAPSED和EXPANDED都可以
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        } else {
            ft.add(this, tag);
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, "Please use #show() method!");
        }
        return -1;//
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!mIsShownByMe) {
            // If not explicitly shown through our API, take this as an
            // indication that the dialog is no longer dismissed.
            mIsDismissed = false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!mIsShownByMe && !mIsDismissed) {
            // The fragment was not shown by a direct call here, it is not
            // dismissed, and now it is being detached...  well, okay, thou
            // art now dismissed.  Have fun.
            mIsDismissed = true;
        }
    }

    /**
     * Remove dialog.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (!mIsViewDestroyed) {
            dismissInternal(true);
        }
    }

    @Override
    public void dismiss() {
        dismissInternal(false);
    }

    @Override
    public void dismissAllowingStateLoss() {
        dismissInternal(true);
    }

    void dismissInternal(boolean allowStateLoss) {
        if (mIsDismissed) {
            return;
        }
        mIsDismissed = true;
        mIsShownByMe = false;
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.dismiss();
        }

        if (removeSelfFromDialog()) {
            removeFragment(allowStateLoss);
        } else {
            hideFragment(allowStateLoss);
        }
    }

    private void hideFragment(boolean allowStateLoss) {
        try {
            if (getContext() == null) {
                return;//如果context为null,说明mHost为null,mhost为null,则以下操作均无法正确执行
            }
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager == null)
                fragmentManager = getChildFragmentManager();//maybe throw exception
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.hide(this);
            if (allowStateLoss) {
                ft.commitAllowingStateLoss();
            } else {
                ft.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void removeFragment(boolean allowStateLoss) {
        try {
            mIsViewDestroyed = true;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(this);
            if (allowStateLoss) {
                ft.commitAllowingStateLoss();
            } else {
                ft.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback
            = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet,
                                   @BottomSheetBehavior.State int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
}