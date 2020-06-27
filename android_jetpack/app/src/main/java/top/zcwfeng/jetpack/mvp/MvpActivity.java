package top.zcwfeng.jetpack.mvp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import top.zcwfeng.jetpack.R;
import top.zcwfeng.jetpack.bean.Goods;
import top.zcwfeng.jetpack.mvp.presenter.impl.GoodsPresenter;
import top.zcwfeng.jetpack.mvp.view.BaseActivity;
import top.zcwfeng.jetpack.mvp.view.impl.IGoodsView;
import top.zcwfeng.jetpack.utils.LiveDataBus;

public class MvpActivity extends BaseActivity<GoodsPresenter, IGoodsView> implements IGoodsView {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        listView=findViewById(R.id.listView);

        presenter.fetch();

        LiveDataBus.getInstance().with("list", ArrayList.class)
                .observe(this, new Observer<ArrayList>() {
                    @Override
                    public void onChanged(ArrayList arrayList) {
                        if(arrayList!=null){
                            Log.i("David","收到了数据"+arrayList.toString());
                        }
                    }
                });

    }


    @Override
    protected GoodsPresenter createPresenter() {
        return new GoodsPresenter();
    }
    @Override
    public void showGoodsView(List<Goods> goods) {
        listView.setAdapter(new GoodsAdapter(this,goods));
    }

    @Override
    public void showErrorMessage(String msg) {

    }

    @Override
    protected void init() {
        super.init();
        getLifecycle().addObserver(presenter);
    }
}