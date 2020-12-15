package top.zcwfeng.base.customview.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract
class BaseCustomView<VIEW extends ViewDataBinding,DATA extends BaseCustomViewModel> extends LinearLayout implements IBaseCustomView<DATA>{
    protected VIEW mBinding;
    protected DATA mViewModel;

    public BaseCustomView(Context context) {
        super(context);
        init();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    public void init(){
        setPadding(16, 16, 16, 16);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // TODO: 4 布局优化 PictureView
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(),this,true);
        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRootClicked(v);
            }
        });

//        addView(mBinding.getRoot());
    }

    public void setData(DATA data){
        mViewModel = data;
        setDataToView(data);
        mBinding.executePendingBindings();

    }

    public abstract void setDataToView(DATA data);

    public abstract int getLayoutId();

    public abstract void onRootClicked(View view);
}
