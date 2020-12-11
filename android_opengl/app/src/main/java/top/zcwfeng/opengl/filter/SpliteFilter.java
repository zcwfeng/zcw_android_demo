package top.zcwfeng.opengl.filter;

import android.content.Context;

import top.zcwfeng.opengl.R;

public
class SpliteFilter extends AbstractFrameFilter{
    public SpliteFilter(Context context) {
//        super(context, R.raw.base_vert, R.raw.split2_screen);
//        super(context, R.raw.base_vert, R.raw.split3_screen);
        super(context, R.raw.base_vert, R.raw.split4_screen);

    }

    @Override
    public int onDraw(int texture, FilterChain filterChain) {
        return super.onDraw(texture, filterChain);
    }
}
