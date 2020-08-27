package top.zcwfeng.jetpack.paging.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import top.zcwfeng.jetpack.R;
import top.zcwfeng.jetpack.paging.bean.MyStudent;

public
class RecyclerViewPageingAdapter extends PagedListAdapter<MyStudent,RecyclerViewPageingAdapter.MyRecyclerViewHolder> {

    private static DiffUtil.ItemCallback<MyStudent> DIFF_STUDENT =
            new DiffUtil.ItemCallback<MyStudent>() {
                @Override
                public boolean areItemsTheSame(@NonNull MyStudent oldItem, @NonNull MyStudent newItem) {
                    return (oldItem.getId()).equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull MyStudent oldItem, @NonNull MyStudent newItem) {
                    return oldItem.equals(newItem);
                }
            };
    public RecyclerViewPageingAdapter() {
        super(DIFF_STUDENT);
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,null);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position) {
        MyStudent student =  getItem(position);
        if(student == null){
            holder.tvId.setText("记载中.getId()");
            holder.tvName.setText("记载中.getName()");
            holder.tvSex.setText("记载中.getSex()");
        }else{
            holder.tvId.setText(student.getId());
            holder.tvName.setText(student.getName());
            holder.tvSex.setText(student.getSex());

        }

    }

    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        TextView tvName;
        TextView tvSex;
        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);


            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSex = itemView.findViewById(R.id.tv_sex);
        }
    }
}
