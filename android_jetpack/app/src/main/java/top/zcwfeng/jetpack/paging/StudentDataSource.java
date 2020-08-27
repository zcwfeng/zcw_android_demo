package top.zcwfeng.jetpack.paging;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.ArrayList;
import java.util.List;

import top.zcwfeng.common.constants.Config;
import top.zcwfeng.jetpack.paging.bean.MyStudent;

public class StudentDataSource extends PositionalDataSource<MyStudent> {

    /**
     * 初始化第一个页面数据
     *
     * @param params
     * @param callback
     */
    @Override
    public void loadInitial(@NonNull LoadInitialParams params,
                            @NonNull LoadInitialCallback<MyStudent> callback) {
        callback.onResult(getStudents(0, Config.PAGESIZE)
                , 0, 1000);
    }

    /**
     * 滑动的时候加载数据
     *
     * @param params
     * @param callback
     */
    @Override
    public void loadRange(@NonNull LoadRangeParams params,
                          @NonNull LoadRangeCallback<MyStudent> callback) {
        // 从哪里开始加载，加载多少，内部动态计算
        callback.onResult(getStudents(params.startPosition,params.loadSize));

    }

    /**
     * 模拟数据源
     *
     * @param startPosition
     * @param pageSize
     * @return
     */
    private List getStudents(int startPosition, int pageSize) {
        ArrayList list = new ArrayList();
        MyStudent student;
        for (int i = startPosition; i < startPosition + pageSize; i++) {
            student = new MyStudent();
            student.setId("ID 号：" + i);
            student.setSex("Sex:" + i);
            student.setName("userName:" + i);
            list.add(student);
        }
        return list;
    }


}
