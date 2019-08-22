package top.zcwfeng.flowlayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.zcwfeng.flowlayout.R;

public class FlextBoxLayoutManagerAdapter extends RecyclerView.Adapter<FlextBoxLayoutManagerAdapter.CustomViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public FlextBoxLayoutManagerAdapter(Context context,List<String> datas) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView view = (TextView) mInflater.inflate(R.layout.item_textview,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tag.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        public TextView tag;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tv);
        }
    }
}
