package top.zcwfeng.android_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SimplerAdatper extends RecyclerView.Adapter<SimplerAdatper.MyViewHolder> {
    private LayoutInflater mInflater;
    protected List<String> mDatas;
    private Context mContext;

    public interface OnItemClickListener{
         void onItemClick(View view,int pos);
         void onItemLongClick(View view,int pos);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickLsn(OnItemClickListener clickLsn){
        onItemClickListener = clickLsn;
    }

    public SimplerAdatper(Context context, List<String> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recyclerview, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        setUpOnItemEvent(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.mTv.setText(mDatas.get(position));
        setUpOnItemEvent(holder);
    }

    protected void setUpOnItemEvent(@NonNull final MyViewHolder holder) {
        if(onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();

                    onItemClickListener.onItemClick(v,pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();

                    onItemClickListener.onItemLongClick(v,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv);

        }
    }

    public void add(int pos,String item){
        mDatas.add(pos,item);
        notifyItemInserted(pos);
    }

    public void del(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }
}
