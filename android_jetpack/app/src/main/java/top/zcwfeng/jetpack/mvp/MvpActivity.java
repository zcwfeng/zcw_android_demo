package top.zcwfeng.jetpack.mvp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import top.zcwfeng.jetpack.R;
import top.zcwfeng.jetpack.mvp.db.Student;
import top.zcwfeng.jetpack.mvp.model.StudentViewModel;
import top.zcwfeng.jetpack.mvp.presenter.impl.GoodsPresenter;
import top.zcwfeng.jetpack.mvp.view.BaseActivity;
import top.zcwfeng.jetpack.mvp.view.impl.IGoodsView;

public class MvpActivity extends BaseActivity<GoodsPresenter, IGoodsView> implements IGoodsView {
    ListView listView;
    StudentViewModel studentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        listView=findViewById(R.id.listView);

        studentViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(StudentViewModel.class);

        studentViewModel.getAllLiveDataStudent().observe(this, new Observer<List<top.zcwfeng.jetpack.mvp.db.Student>>() {
            @Override
            public void onChanged(List<top.zcwfeng.jetpack.mvp.db.Student> students) {
                Log.i("zcw:::", "onChanged: " + students.size());
                listView.setAdapter(new GoodsAdapter(MvpActivity.this,students));
            }
        });

//        for (int i = 0; i < 50; i++) {
//            studentViewModel.insert(new Student("jack"+i,"2222",i));
//        }

//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                for (int i = 0; i < 50; i++) {
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    studentViewModel.update(new Student(6,"David" + i,"123",1));
//                    Log.d("zcw:::", "run: "+Thread.currentThread().getName());
//                }
//            }
//        }.start();

    }


    @Nullable
    @Override
    public Object getLastNonConfigurationInstance() {
        Log.d("zcw:::", "getLastNonConfigurationInstance: ");
        return super.getLastNonConfigurationInstance();
    }

    @Nullable
    @Override
    public Object getLastCustomNonConfigurationInstance() {
        Log.d("zcw", "getLastCustomNonConfigurationInstance: ");
        return super.getLastCustomNonConfigurationInstance();
    }

    @Override
    protected GoodsPresenter createPresenter() {
        return new GoodsPresenter();
    }
    @Override
    public void showGoodsView(List<Student> goods) {
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