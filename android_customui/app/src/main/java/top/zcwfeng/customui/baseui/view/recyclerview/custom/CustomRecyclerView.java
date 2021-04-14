package top.zcwfeng.customui.baseui.view.recyclerview.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public
class CustomRecyclerView extends RecyclerView {
    public CustomRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected float getTopFadingEdgeStrength() {
//        return super.getTopFadingEdgeStrength();
        return 0;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    //    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
////2021/3/10 hjz 反射解决 不希望显示顶部的fadingEdge .不过一开始mTopGlow null ，需要滑动几下才有。 不是最好方案，但是目前找不到最优方案
//        EdgeEffect mTopGlow = null;
//        try {
//            Field topGlow = devRecycler.getClass().getDeclaredField("mTopGlow");
//            if (topGlow != null) {
//                topGlow.setAccessible(true);
//                mTopGlow = (EdgeEffect) topGlow.get(devRecycler);
//            }
//            if (mTopGlow != null) {
//                mTopGlow.setSize(0, 0);
//                mTopGlow.finish();
//                Log.d("zcw","EdgeEffect=>mTopGlow");
//            }else {
//                Log.d("zcw","EdgeEffect===>null ");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("zcw","EdgeEffect printStackTrace ===>"+e.getMessage());
//        }
}
