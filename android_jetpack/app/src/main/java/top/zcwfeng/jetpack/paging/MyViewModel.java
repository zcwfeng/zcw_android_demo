package top.zcwfeng.jetpack.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import top.zcwfeng.common.constants.Config;
import top.zcwfeng.jetpack.paging.bean.MyStudent;

public
class MyViewModel extends ViewModel {
    private final LiveData<PagedList<MyStudent>> listLiveData;

    public MyViewModel() {
        StudentDataSourceFactory factory = new StudentDataSourceFactory();
        this.listLiveData = new LivePagedListBuilder<Integer, MyStudent>
                (factory, Config.PAGESIZE)
                .build();
    }


    // TODO 暴露数据出去
    public LiveData<PagedList<MyStudent>> getListLiveData() {
        return listLiveData;
    }
}
