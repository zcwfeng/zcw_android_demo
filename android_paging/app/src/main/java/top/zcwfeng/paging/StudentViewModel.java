package top.zcwfeng.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * 数据元获取，也就是网上的PagedList
 * 每请求一页旧货去一个PageList对象
 */
public class StudentViewModel extends ViewModel {

    private final LiveData<PagedList<Student>> listLiveData;


    public StudentViewModel() {
        StudentDataSourceFactory factory = new StudentDataSourceFactory();
        this.listLiveData = new LivePagedListBuilder<Integer,Student>(factory,Flag.SIZE).build();
    }

    public LiveData<PagedList<Student>> getListLiveData() {
        return listLiveData;
    }
}
