package top.zcwfeng.use_paging_itemkey;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import java.util.List;

/**
 * 同学们，要认真看看哦
 * ItemKeyedDataSource<Key, Value>：适用于目标数据的加载依赖特定item的信息，
 * 即Key字段包含的是Item中的信息，比如需要根据第N项的信息加载第N+1项的数据，传参中需要传入第N项的ID时，
 * 该场景多出现于论坛类应用评论信息的请求。
 */
public class CustomItemDataSource extends ItemKeyedDataSource<Integer, Person> {

    private DataRepository dataRepository;

    CustomItemDataSource(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    // loadInitial 初始加载数据
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Person> callback) {
        List<Person> dataList = dataRepository.initData(params.requestedLoadSize);
        callback.onResult(dataList);
    }

    @NonNull
    @Override
    public Integer getKey(@NonNull Person item) {
        return (int) System.currentTimeMillis();
    }

    // loadBefore 向前分页加载数据
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Person> callback) {
        List<Person> dataList = dataRepository.loadPageData(params.key, params.requestedLoadSize);
        if (dataList != null) {
            callback.onResult(dataList);
        }
    }

    // loadAfter 向后分页加载数据
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Person> callback) {
        List<Person> dataList = dataRepository.loadPageData(params.key, params.requestedLoadSize);
        if (dataList != null) {
            callback.onResult(dataList);
        }
    }

}
