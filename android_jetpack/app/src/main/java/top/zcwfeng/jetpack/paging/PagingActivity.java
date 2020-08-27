package top.zcwfeng.jetpack.paging;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import top.zcwfeng.jetpack.R;
import top.zcwfeng.jetpack.paging.adapter.RecyclerViewPageingAdapter;
import top.zcwfeng.jetpack.paging.bean.MyStudent;

public class PagingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerViewPageingAdapter recyclerViewPageingAdapter;
    MyViewModel studentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging);
        studentViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory())
                .get(MyViewModel.class);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerViewPageingAdapter = new RecyclerViewPageingAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewPageingAdapter);

        studentViewModel.getListLiveData().observe(this, new Observer<PagedList<MyStudent>>() {
            @Override
            public void onChanged(PagedList<MyStudent> myStudents) {
                recyclerViewPageingAdapter.submitList(myStudents);
            }
        });

    }
}