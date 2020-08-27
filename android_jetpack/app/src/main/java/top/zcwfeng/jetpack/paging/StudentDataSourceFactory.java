package top.zcwfeng.jetpack.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import top.zcwfeng.jetpack.paging.bean.MyStudent;

/**
 * 数据工厂
 * <Integer,MyStudent>
 * PositionalDataSource----MyStudent
 */
public
class StudentDataSourceFactory extends DataSource.Factory<Integer, MyStudent> {
    @NonNull
    @Override
    public DataSource<Integer, MyStudent> create() {
        StudentDataSource studentDataSource = new StudentDataSource();
        return studentDataSource;
    }
}
