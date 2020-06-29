package top.zcwfeng.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

/**
 * 数据工厂
 */
public class StudentDataSourceFactory extends DataSource.Factory<Integer,Student> {
    @NonNull
    @Override
    public DataSource<Integer, Student> create() {
        StudentDataSource studentDataSource = new StudentDataSource();
        return studentDataSource;
    }
}
