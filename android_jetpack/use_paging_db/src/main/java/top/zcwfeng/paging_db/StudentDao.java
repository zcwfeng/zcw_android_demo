package top.zcwfeng.paging_db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface StudentDao {
    @Insert
    void insertStudents(Student2 ... students);
    @Query("DELETE FROM student_paging_table")
    void deleteAllStudents();

    @Query("SELECT * FROM student_paging_table ORDER BY id")
    DataSource.Factory<Integer, Student2> getAllStudents();
}
