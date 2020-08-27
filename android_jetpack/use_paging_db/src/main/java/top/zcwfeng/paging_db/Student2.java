package top.zcwfeng.paging_db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_paging_table")
public class Student2 {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "student_number")
    private int studentNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }
}
