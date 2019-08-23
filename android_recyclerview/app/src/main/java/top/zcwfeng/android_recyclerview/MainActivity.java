package top.zcwfeng.android_recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private SimplerAdatper mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();

        initViews();

        mAdapter = new SimplerAdatper(this,mDatas);

        mAdapter.setOnItemClickLsn(new SimplerAdatper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Toast.makeText(MainActivity.this,"click" + pos,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int pos) {
                Toast.makeText(MainActivity.this,"click long" + pos,Toast.LENGTH_SHORT).show();

            }
        });

        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
//        mRecyclerView.addItemDecoration(itemDecoration);

        // 第三方库animation
        SlideInOutLeftItemAnimator slideInOutLeftItemAnimator = new SlideInOutLeftItemAnimator(mRecyclerView);
        mRecyclerView.setItemAnimator(slideInOutLeftItemAnimator);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(manager);
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //创建菜单项的点击事件
        switch (item.getItemId()) {

            case R.id.add:

                mAdapter.add(1,"Insert Data");

                break;

            case R.id.del:
                mAdapter.del(1);
                break;
            case R.id.recycler_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

                break;
            case R.id.recycler_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));

                break;
            case R.id.recycler_hor_gridview:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL));

                break;
            case R.id.recycler_staggerview:
                Intent intent = new Intent(this,StaggerGraidLayoutActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
