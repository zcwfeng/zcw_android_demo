package top.zcwfeng.use_paging_pagekey;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class PersonViewModel extends ViewModel {

    private final LiveData<PagedList<Person>> pagedListLiveData;

    public PersonViewModel() {
        // @1 Factory 的创建
        DataSource.Factory<Integer, Person> factory = new PersonDataSourceFactory();

        /*PagedList.Config pConfig = new PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .build();*/

        // @1 Factory
        pagedListLiveData = new LivePagedListBuilder<Integer, Person>(factory, 2).build();
    }

    public LiveData<PagedList<Person>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}
