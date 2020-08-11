package top.zcwfeng.base.loadsir;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import top.zcwfeng.base.R;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class PlaceholderCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_placeholder;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
