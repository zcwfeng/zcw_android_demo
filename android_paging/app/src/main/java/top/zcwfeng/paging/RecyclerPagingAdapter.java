package top.zcwfeng.paging;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public
class RecyclerPagingAdapter extends PagedListAdapter<Student, RecyclerPagingAdapter.MyRecyclerViewHolder> {
    // TODO: 2020/6/28 new 比较器
    private static DiffUtil.ItemCallback<Student> DIFF_STUDENT =
            new DiffUtil.ItemCallback<Student>() {
                // 一般不计较唯一性内容，id
                @Override
                public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                // 对象本身的比较
                @Override
                public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public RecyclerPagingAdapter() {
        super(DIFF_STUDENT);
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position) {
        Student student = getItem(position);
        if (null == student) {
            holder.tvId.setText("Id加载中");
            holder.tvName.setText("Name加载中");
            holder.tvSex.setText("Sex加载中");
        } else {
            holder.tvId.setText(student.getId());
            holder.tvName.setText(student.getName());
            holder.tvSex.setText(student.getSex());
        }
    }


    // item 优化
    public static class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

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
