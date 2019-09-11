package top.zcwfeng.mvp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.zcwfeng.mvp.bean.Girl;
import top.zcwfeng.mvp.presenter.GirlPresenter;
import top.zcwfeng.mvp.view.BaseActivity;
import top.zcwfeng.mvp.view.IGirlView;

public class MainActivity extends BaseActivity<IGirlView,GirlPresenter<IGirlView>> implements IGirlView {

    private RecyclerView mRecyclerView;
    private SimplerAdatper mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.girl_recyclerview);
        girlPresenter.fetch();


    }

    @Override
    protected GirlPresenter<IGirlView> createPresenter() {
        return new GirlPresenter<>();
    }

    @Override
    public void showGirls(List<Girl> data) {
        mAdapter = new SimplerAdatper(MainActivity.this,data);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void loading() {
        Toast.makeText(this,"loading...",Toast.LENGTH_LONG).show();
    }


}
