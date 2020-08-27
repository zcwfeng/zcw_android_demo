package top.zcwfeng.use_paging_itemkey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerPagingAdapter extends PagedListAdapter<Person, RecyclerPagingAdapter.MyRecyclerViewHolder> {

    //需要oldPerson与新 newPerson 比较才能得出变化的数据
    private static DiffUtil.ItemCallback<Person> DIFF_STUDENT =
            new DiffUtil.ItemCallback<Person>() {
                // 判断Item是否已经存在
                @Override
                public boolean areItemsTheSame(Person oldPerson, Person newPerson) {
                    return oldPerson.getId().equals(newPerson.getId());
                }

                // 如果Item已经存在则会调用此方法，判断Item的内容是否一致
                @Override
                public boolean areContentsTheSame(Person oldPerson, Person newPerson) {
                    return oldPerson.equals(newPerson);
                }
            };

    protected RecyclerPagingAdapter() {
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
        Person person = getItem(position);
        if (null == person) {
            holder.tvId.setText("Id加载中");
            holder.tvName.setText("Name加载中");
            holder.tvSex.setText("Sex加载中");
        } else if (null != person) {
            holder.tvId.setText(person.getId());
            holder.tvName.setText(person.getName());
            holder.tvSex.setText(person.getSex());

        }
    }

    public static class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvId;
        TextView tvName;
        TextView tvSex;

        public MyRecyclerViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSex = itemView.findViewById(R.id.tv_sex);
        }
    }

}
