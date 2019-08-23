package top.zcwfeng.android_recyclerview;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class StaggerGraidLayoutActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private StaggerSimplerAdatper mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stagger_graid_layout);



        initDatas();

        initViews();

        mAdapter = new StaggerSimplerAdatper(this,mDatas);
        mAdapter.setOnItemClickLsn(new SimplerAdatper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
            }

            @Override
            public void onItemLongClick(View view, int pos) {
                mAdapter.del(pos);

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }


    private void initViews() {
        mRecyclerView = findViewById(R.id.recyclerview);
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for(char i = 'A';i<'z';i++) {
            mDatas.add(i+"");
        }
    }

}


