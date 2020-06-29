package top.zcwfeng.paging;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据的来源（room，网络等）
 *
 * PositionalDataSource<Student> 适用于目标数据总数的固定，通过特别的位置加载数据（0-10）
 */
public class StudentDataSource extends PositionalDataSource<Student> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Student> callback) {
        //数据源，位置，总大小
        callback.onResult(getStudents(0,Flag.SIZE),0,1000);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Student> callback) {
        //从哪里开始加载，内部算      大小，size也是内部算
        callback.onResult(getStudents(params.startPosition,params.loadSize));
    }

    /**
     * 模拟数据来源（数据库，文件，网络服务器等）
     * @param startPosition
     * @param pageSize
     * @return
     */
    private List<Student> getStudents(int startPosition,int pageSize){
        List<Student> students = new ArrayList<>();
        for (int i= startPosition;i<startPosition + pageSize;i++) {
            Student student = new Student();
            student.setId("ID号是：" + i);
            student.setName("我的名称："+i);
            student.setSex("我的性别" +i);
            students.add(student);
        }
        return students;
    }
}
