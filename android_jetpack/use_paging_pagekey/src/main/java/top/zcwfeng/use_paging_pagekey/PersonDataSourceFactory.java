package top.zcwfeng.use_paging_pagekey;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class PersonDataSourceFactory extends DataSource.Factory<Integer, Person> {

    @NonNull
    @Override
    public DataSource<Integer, Person> create() {
        return new CustomPageDataSource(new DataRepository());
    }
}
