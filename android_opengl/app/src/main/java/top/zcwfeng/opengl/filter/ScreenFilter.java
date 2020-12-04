package top.zcwfeng.opengl.filter;

import android.content.Context;

import top.zcwfeng.opengl.R;

public
class ScreenFilter extends AbstractFilter{
    public ScreenFilter(Context context) {
        super(context,R.raw.base_vert,R.raw.base_frag);
    }

}
