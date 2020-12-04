package top.zcwfeng.opengl.filter;

import android.content.Context;

import top.zcwfeng.opengl.R;

public
class RecordFilter extends AbstractFilter{
    public RecordFilter(Context context) {
        super(context, R.raw.base_vert, R.raw.base_frag);
    }
}
