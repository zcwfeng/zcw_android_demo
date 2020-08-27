package top.zcwfeng.paging_db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MyPagerAdapter extends PagedListAdapter<Student2,MyPagerAdapter.MyViewHolder> {
    protected MyPagerAdapter() {
        super(new DiffUtil.ItemCallback<Student2>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student2 oldItem, @NonNull Student2 newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student2 oldItem, @NonNull Student2 newItem) {
                return oldItem.getStudentNumber() == newItem.getStudentNumber();
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell, parent, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student2 student = getItem(position);
        if (student == null) {
            holder.textView.setText("loading");
        } else {
            holder.textView.setText(String.valueOf(student.getStudentNumber()));

        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}



