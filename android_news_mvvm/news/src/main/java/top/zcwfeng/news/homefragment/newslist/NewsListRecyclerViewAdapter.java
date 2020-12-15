package top.zcwfeng.news.homefragment.newslist;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.zcwfeng.base.customview.customview.BaseCustomViewModel;
import top.zcwfeng.base.customview.recyclerview.BaseViewHolder;
import top.zcwfeng.common.views.pictureview.PictureTitleView;
import top.zcwfeng.common.views.pictureview.PictureTitleViewModel;
import top.zcwfeng.common.views.titleview.TitleView;


/**
 
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_TYPE_PICTURE_TITLE = 1;
    private final int VIEW_TYPE_TITLE = 2;
    private List<BaseCustomViewModel> mItems;

    void setData(List<BaseCustomViewModel> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems != null && mItems.get(position) instanceof PictureTitleViewModel) {
            return VIEW_TYPE_PICTURE_TITLE;
        }
        return VIEW_TYPE_TITLE;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PICTURE_TITLE) {
            return new BaseViewHolder(new PictureTitleView(parent.getContext()));
        } else if (viewType == VIEW_TYPE_TITLE) {
            return new BaseViewHolder(new TitleView(parent.getContext()));
        }
        return null;
    }



    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }
}
