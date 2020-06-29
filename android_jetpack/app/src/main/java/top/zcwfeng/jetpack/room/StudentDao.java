package top.zcwfeng.jetpack.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insert(Student ... students);
    @Delete
    void delete(Student student);
    @Update
    void update(Student student);
    @Query("select * from Student")
    List<Student> getAll();
    @Query("select * from Student where name like :name")
    Student findByName(String name);
    @Query("select * from Student where uid in (:uids)")
    List<Student> getAllId(int[] uids);
    @Query("select name,pwd from Student")
    List<StudentTuple> getRecord();


}
