package top.zcwfeng.use_paging_pagekey;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

/**
 * PageKeyedDataSource<Key, Value>：适用于目标数据根据页信息请求数据的场景，
 * 即Key字段是页相关的信息。比如请求的数据的参数中包含类似next / pervious页数的信息。
 */
public class CustomPageDataSource extends PageKeyedDataSource<Integer, Person> {

    private DataRepository dataRepository;

    CustomPageDataSource(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    // loadInitial 初始加载数据
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Person> callback) {
        List<Person> dataList = dataRepository.initData(params.requestedLoadSize);
        callback.onResult(dataList, null, 2);
    }

    // loadBefore 向前分页加载数据
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Person> callback) {
        List<Person> dataList = dataRepository.loadPageData(params.key, params.requestedLoadSize);
        if (dataList != null) {
            callback.onResult(dataList, params.key - 1);
        }
    }

    // loadAfter 向后分页加载数据
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Person> callback) {
        List<Person> dataList = dataRepository.loadPageData(params.key, params.requestedLoadSize);
        if (dataList != null) {
            callback.onResult(dataList, params.key + 1);
        }
    }
}
