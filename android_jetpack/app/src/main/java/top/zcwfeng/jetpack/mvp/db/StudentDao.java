package top.zcwfeng.jetpack.mvp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insert(Student... students);
    @Delete
    void delete(Student student);
    @Update
    void update(Student student);
    @Query("select * from Student")
    List<Student> getAll();


    @Query("select * from Student order by uid")
    LiveData<List<Student>> getAllLiveDataStudent();
}
