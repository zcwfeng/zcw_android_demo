package top.zcwfeng.base.customview.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import top.zcwfeng.base.customview.customview.BaseCustomViewModel;
import top.zcwfeng.base.customview.customview.IBaseCustomView;

public
class BaseViewHolder extends RecyclerView.ViewHolder {
    private final IBaseCustomView view;
    public BaseViewHolder(@NonNull IBaseCustomView itemView) {
        super((View) itemView);
        view = itemView;
    }

    public void bind(BaseCustomViewModel viewModel){
        view.setData(viewModel);
    }
}
